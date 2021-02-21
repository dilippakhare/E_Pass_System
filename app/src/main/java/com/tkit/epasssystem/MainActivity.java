package com.tkit.epasssystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkit.epasssystem.Service.AsyncUtilities;
import com.tkit.epasssystem.Service.Common;
import com.tkit.epasssystem.Service.DownloadUtility;
import com.tkit.epasssystem.adapter.PassesAdapter;
import com.tkit.epasssystem.model.ModelPassesRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements DownloadUtility
{


    int PassengerId;

    String Username,Userpassword;
    Button btnNewPass,btnProfile;
    ListView listRecentPass;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("E-Pass System");
        setSupportActionBar(toolbar);



        //----------------------- for auto login
        if(Common.getUserName(MainActivity.this).length() == 0)
        {
            finish();
            Intent intent= new Intent(MainActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        Username=Common.getUserName(this);
        Userpassword = Common.getUserPassword(this);
        init();

        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,NewPassRequestActivity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,UserProfileActivity.class);
                startActivity(intent);
            }
        });


//        logout=findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Common.clearUserName(MainActivity.this);
//                Intent intent= new Intent(MainActivity.this,LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String url = Common.url+"/InItAppData?Userid="+Common.getUserId(this)+"&PassengerId="+Common.getPassengerId(this);
        AsyncUtilities utilities = new AsyncUtilities(MainActivity.this, false, url, "", 2, MainActivity.this);
        utilities.execute();
    }

    private void init()
    {
        btnNewPass= findViewById(R.id.btnNewPass);
        btnProfile= findViewById(R.id.btnProfile);
        listRecentPass= findViewById(R.id.listRecentPass);
    }

    ArrayList<ModelPassesRequest>  reqestList=new ArrayList<>();
    @Override
    public void onComplete(String str, int requestCode, int responseCode)
    {
        Log.d("VK",str);

        if (requestCode==2 &&responseCode==200)
        {
            try
            {
              Common.saveAppData(this,str);
              JSONObject j=new JSONObject(str);
              JSONArray array=j.getJSONArray("PassessRequestList");
              Gson gson=new Gson();
              reqestList.clear();

              for(int i=0;i<array.length();i++)
              {
                 JSONObject  jObj=  array.getJSONObject(i);
                 ModelPassesRequest o=new ModelPassesRequest();
                 o=   gson.fromJson(jObj.toString(),ModelPassesRequest.class);
                 //=   gson.fromJson(array.getJSONObject(i).toString(),ModelPassesRequest.class);
                  reqestList.add(o);
              }
                Collections.reverse(reqestList);

               PassesAdapter adapter=new PassesAdapter(this,1,reqestList);
               listRecentPass.setAdapter(adapter);
            }
            catch (Exception ex)
            {}
        }
        else
        {
          //  Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
}