package com.tkit.epasssystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tkit.epasssystem.Service.AsyncUtilities;
import com.tkit.epasssystem.Service.Common;
import com.tkit.epasssystem.Service.DownloadUtility;
import com.tkit.epasssystem.model.ModelPassesType;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class NewPassRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DownloadUtility
{
    Button btnDateBrowse,btnRegister;
    private int mYear, mMonth, mDay;
    TextView txtDate;
    Spinner spinner;
    String[] passengerType = {"General","Student","Employee"};
    String passengerTypeSelected;
    EditText edPrice;
    ArrayList<ModelPassesType> passesTypeArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_request);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Pass Request");
        setSupportActionBar(toolbar);
        init();

        btnDateBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewPassRequestActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        passesTypeArrayList= Common.getPassesType(this);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<ModelPassesType> passengerTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, passesTypeArrayList);
        passengerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(passengerTypeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try
                {
                   float price=    passesTypeArrayList.get(i).getDailyPassPrice();
                   edPrice.setText(((int)price)+"");
                }
                catch (Exception ex)
                {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDate.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(NewPassRequestActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
                       return;
                }
                if(edPrice.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(NewPassRequestActivity.this, "Price should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                maakeRequest();
            }
        });

    }
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        passengerTypeSelected=passengerType[position];
       // Toast.makeText(getApplicationContext(),passengerType[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void init()
    {
        txtDate =       findViewById(R.id.txtDate);
        btnDateBrowse = findViewById(R.id.btnDateBrowse);
        spinner= findViewById(R.id.spinner);
        edPrice=findViewById(R.id.edPrice);
        btnRegister=findViewById(R.id.btnRegister);

    }
    void maakeRequest()
    {
        try
        {
            String url = Common.url+"MakePassRequest";
            JSONObject j=new JSONObject();
            j.put("PassengerId",Common.getPassengerId(this));
            j.put("UserId",Common.getUserId(this));
            j.put("PassTypeId",(int)passesTypeArrayList.get(spinner.getSelectedItemPosition()).getPassTypeId());
            j.put("Price",Integer.parseInt(edPrice.getText().toString()));

            long bInMili= Common.GettingMiliSeconds(txtDate.getText().toString());
           // long regInmili = System.currentTimeMillis();
            j.put("FromDate",bInMili);

            AsyncUtilities utilities = new AsyncUtilities(NewPassRequestActivity.this, true, url, j.toString(), 1, NewPassRequestActivity.this);
            utilities.execute();
        }
        catch (Exception ex)
        {}
    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode)
    {
        if (requestCode==1 &&responseCode==200)
        {
            try
            {
                finish();
                Toast.makeText(this,"Pass Registration Done Successfully",Toast.LENGTH_LONG).show();
            }
            catch (Exception ex)
            {}
        }
        else
        {
           // Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
}