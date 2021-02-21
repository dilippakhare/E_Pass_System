package com.tkit.epasssystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tkit.epasssystem.Service.AsyncUtilities;
import com.tkit.epasssystem.Service.Common;
import com.tkit.epasssystem.Service.DownloadUtility;

import org.json.JSONArray;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements DownloadUtility
{
    EditText et_username,et_password;
    String username,password;
    Button btnLogin;
    LinearLayout txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


            et_username = findViewById(R.id.et_username);
            et_password = findViewById(R.id.et_password);
            btnLogin = findViewById(R.id.btnLogin);
            txtRegister = findViewById(R.id.txtRegister);
            txtRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    username = et_username.getText().toString();
                    password = et_password.getText().toString();
                    String url =  Common.url+ "LoginUser?unm="+ username +"&upass="+ password;

                    // Calling Server Web service
                    AsyncUtilities utilities = new AsyncUtilities(LoginActivity.this, false, url, "", 1, LoginActivity.this);
                    utilities.execute();
                }
            });


    }

    @Override
    public void onComplete(String str, int requestCode, int responseCode)
    {
        Log.d("VK",str);
        Toast.makeText(this, "Inside", Toast.LENGTH_SHORT).show();
        if (requestCode==1 &&responseCode==200)
        {
            try
            {
                JSONArray array=new JSONArray(str);
                if(array.length()>0)
                {
                    Common.saveUserData(this,str);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        String  un= jsonObject.getString("UserName");
                        String pass= jsonObject.getString("UserPassword");
                         if (pass.equals(password))
                         {
                             Common.setUserName(this,un);
                             Common.setUserPassword(this,pass);
                             Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                             intent.putExtra("UserName",un);
                             intent.putExtra("UserPassword",pass);

                             finish();
                             startActivity(intent);
                         }
                         else
                         {
                             Toast.makeText(this, "Failed to login, please try again", Toast.LENGTH_SHORT).show();
                         }

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