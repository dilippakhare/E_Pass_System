package com.tkit.epasssystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tkit.epasssystem.Service.AsyncUtilities;
import com.tkit.epasssystem.Service.Common;
import com.tkit.epasssystem.Service.DownloadUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

public class RegisterationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DownloadUtility
{
    Button btnRegister,btnDateBrowse,btnPhotoPathBrowse;
    EditText et_passengerName,et_address,et_emailId,et_contactNo1,et_contactNo2,et_username,et_password,et_rePassword;
    TextView tv_birthdate,tv_profilePicPath;
    RadioGroup rg_radioGroup;
    RadioButton rb_selection;



    String passengerName,address,emailId,contactNo1,contactNo2,username,password,rePassword,passengerTypeSelected;
    String gender;
    String bdate;

    private int mYear, mMonth, mDay;
    LinearLayout txtLogin;


    Spinner spinner;
    String serverPath;

    String[] passengerType = {"General","Student","Employee"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterationActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> passengerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, passengerType);
        passengerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(passengerAdapter);

        rg_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int selectedId= rg_radioGroup.getCheckedRadioButtonId();
                rb_selection=findViewById(selectedId);

                switch(selectedId) {
                    case R.id.rg_radioGroupMale:
                        gender="Male";
                        break;
                    case R.id.rg_radioGroupFemale:
                        gender="Female";
                        break;
                    default:
                        break;
                }
                Toast.makeText(RegisterationActivity.this, ""+gender, Toast.LENGTH_SHORT).show();

            }
        });



        btnDateBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterationActivity.this,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                tv_birthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnPhotoPathBrowse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pickPhoto(2);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RegisterData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        passengerTypeSelected=passengerType[position];
        //Toast.makeText(getApplicationContext(),passengerType[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void RegisterData() throws JSONException {

        Common.hideSoftKeyBord(this);
        passengerName= et_passengerName.getText().toString();
        address = et_address.getText().toString();
        emailId=et_emailId.getText().toString();
        contactNo1=et_contactNo1.getText().toString();
        contactNo2= et_contactNo2.getText().toString();
        username =et_username.getText().toString();
        password=et_password.getText().toString();
        rePassword=et_rePassword.getText().toString();
        bdate=tv_birthdate.getText().toString();


        if (passengerName.equals("null")||passengerName.equals(null)||passengerName.equals(""))
        {
            Toast.makeText(this, "Enter Passanger name", Toast.LENGTH_SHORT).show();
        }
        else  if (address.equals("null")||address.equals(null)||address.equals(""))
        {
            Toast.makeText(this, "Enter address ", Toast.LENGTH_SHORT).show();
        }
        else  if (emailId.equals("null")||emailId.equals(null)||emailId.equals(""))
        {
            Toast.makeText(this, "Enter email address ", Toast.LENGTH_SHORT).show();
        }
        else  if (contactNo1.equals("null")||contactNo1.equals(null)||contactNo1.equals(""))
        {
            Toast.makeText(this, "Enter Contact no ", Toast.LENGTH_SHORT).show();
        }
        else  if (username.equals("null")||username.equals(null)||username.equals(""))
        {
            Toast.makeText(this, "Enter username ", Toast.LENGTH_SHORT).show();
        }
        else  if (password.equals("null")||password.equals(null)||password.equals(""))
        {
            Toast.makeText(this, "Enter password ", Toast.LENGTH_SHORT).show();
        }
        else  if (rePassword.equals("null")||rePassword.equals(null)||rePassword.equals(""))
        {
            Toast.makeText(this, "Enter password ", Toast.LENGTH_SHORT).show();
        }
        else if (bdate.equals("null")||bdate.equals(null)||bdate.equals(""))
        {
            Toast.makeText(this, "Enter Birthdate ", Toast.LENGTH_SHORT).show();
        }
        else if (gender==null||gender.equals(null)||gender.equals("null"))
        {
            Toast.makeText(this, "Select gender ", Toast.LENGTH_SHORT).show();
        }
  //      else if (serverPath.equals("null")||serverPath.equals(null)||serverPath.equals(""))
    //    {
  //          Toast.makeText(this, "Upload your photo", Toast.LENGTH_SHORT).show();
  //      }
        else
        {
            if (password.equals(rePassword))
            {
                if (contactNo1.length()==10)
                {
                    InsertDataToDb();
                }
                else
                {
                    Toast.makeText(this, "Contact No should be 10 digit", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Please check password", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void InsertDataToDb() throws JSONException
    {

        String url=Common.url+"/Registration";

        long bInMili= Common.GettingMiliSeconds(tv_birthdate.getText().toString());
        long regInmili = System.currentTimeMillis();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("PassengerName",passengerName);
        jsonObject.put("Address",address);
        jsonObject.put("EmailId",emailId);
        jsonObject.put("ContactNo1",contactNo1);
        jsonObject.put("ContactNo2",contactNo2);
        jsonObject.put("BirthDate",bInMili);
        jsonObject.put("Gender",gender);
        jsonObject.put("PassengerType",passengerTypeSelected);
        jsonObject.put("PhotoPath",serverPath);
        jsonObject.put("UserName",username);
        jsonObject.put("UserPassword",password);
        jsonObject.put("RegistrationDate",regInmili);



        AsyncUtilities utilities=new AsyncUtilities(this,true,url,jsonObject.toString(),1,this);
        utilities.execute();


    }

    private void pickPhoto(int i)
    {
        Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(in, i);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==2 && resultCode==RESULT_OK)
        {
            try
            {

                Uri uri = data.getData();
                String path = "";
                try {
                    path = Common.getPath(this, uri);
                } catch (Exception e) {
                }

                File file = new File(path);
               // file=  CompressImage(file.getAbsolutePath());

                AlertDialog.Builder alerBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                alerBuilder.setTitle(getString(R.string.app_name));
                alerBuilder.setMessage("Do you want to uplaod Photo ");
                final File finalFile = file;
                alerBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       tv_profilePicPath.setText(finalFile.getAbsolutePath());
                       serverPath=finalFile.getAbsolutePath();
                    }
                });
                alerBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alerBuilder.show();

                // img.setImageBitmap(thePic);
            }
            catch (Exception e)
            {
                AlertDialog.Builder alerBuilder = new AlertDialog.Builder(this);
                alerBuilder.setTitle(getString(R.string.app_name));
                alerBuilder.setMessage(""+e.toString()+"\n\n"+e.getMessage());
                alerBuilder.show();

            }
        }

    }


    // For Bing object to view
    private void init()
    {
        txtLogin=findViewById(R.id.txtLogin);
        //Button
        btnRegister = findViewById(R.id.btnRegister);
        btnDateBrowse = findViewById(R.id.btnDateBrowse);
        btnPhotoPathBrowse = findViewById(R.id.btnPhotoPathBrowse);

        // EditText
        et_passengerName=findViewById(R.id.et_passengerName);
        et_address=findViewById(R.id.et_address);
        et_emailId=findViewById(R.id.et_emailId);
        et_contactNo1=findViewById(R.id.et_contactNo1);
        et_contactNo2=findViewById(R.id.et_contactNo2);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        et_rePassword=findViewById(R.id.et_rePassword);

        //TextView
        tv_birthdate =findViewById(R.id.tv_birthdate);
        tv_profilePicPath=findViewById(R.id.tv_profilePicPath);

        //RadioGroup and Button
        rg_radioGroup=findViewById(R.id.rg_radioGroup);

        //Spinner
        spinner= findViewById(R.id.spinner);

    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode)
    {


        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        if (requestCode==1 && responseCode==200)
        {
            Toast.makeText(this, "Registration successfully", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(RegisterationActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Registration not successful, please try again", Toast.LENGTH_SHORT).show();
        }
    }
}