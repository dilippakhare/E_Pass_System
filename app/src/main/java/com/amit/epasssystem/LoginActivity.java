package com.amit.epasssystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amit.epasssystem.Service.AsyncUtilities;
import com.amit.epasssystem.Service.DownloadUtility;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements DownloadUtility
{
    EditText et_username,et_password;
    String username,password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password= findViewById(R.id.et_password);
        btnLogin =findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                username=et_username.getText().toString();
                password =et_password.getText().toString();
                String url="http://115.124.127.109/epass/api/values/LoginUser?unm="+username+"&upass="+password;

                AsyncUtilities utilities=new AsyncUtilities(LoginActivity.this,false,url,"",1,LoginActivity.this);
                utilities.execute();
            }
        });

    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode)
    {

        if (requestCode==1 &&responseCode==200)
        {
            try
            {
                JSONArray array=new JSONArray(str);
                if(array.length()>0)
                {

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        String  nm= jsonObject.getString("UserName");
                        int id=jsonObject.getInt("UserId");

                        if (nm.equals(username))
                        {
                            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(this, nm+"  "+id, Toast.LENGTH_SHORT).show();

                    }

                }
            }
            catch (Exception ex)
            {}
        }
        else
        {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }

    }
}