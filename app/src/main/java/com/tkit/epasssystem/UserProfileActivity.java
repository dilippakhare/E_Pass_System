package com.tkit.epasssystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tkit.epasssystem.Service.AsyncUtilities;
import com.tkit.epasssystem.Service.Common;
import com.tkit.epasssystem.Service.DownloadUtility;
import com.tkit.epasssystem.model.PassengerProfile;

import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity
{

    PassengerProfile profile;

    TextView txtPassengerName,txtPassengerType,txtAddress,
            txtEmailId,txtContactNo1,txtContactNo2,txtBirthdate,txtGender;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        profile= Common.getPassengerProfl(this);

        txtPassengerName=findViewById(R.id.txtPassengerName);
        txtPassengerType=findViewById(R.id.txtPassengerType);
        txtAddress=findViewById(R.id.txtAddress);
        txtEmailId=findViewById(R.id.txtEmailId);
        txtContactNo1=findViewById(R.id.txtContactNo1);
        txtContactNo2=findViewById(R.id.txtContactNo2);
        txtBirthdate=findViewById(R.id.txtBirthdate);
        txtGender=findViewById(R.id.txtGender);

        txtPassengerName.setText(profile.getPassengerName());
        txtPassengerType.setText(profile.getPassengerType());
        txtAddress.setText(profile.getAddress());
        txtEmailId.setText(profile.getEmailId());
        txtContactNo1.setText(profile.getContactNo1());
        txtContactNo2.setText(profile.getContactNo2());
        txtBirthdate.setText(profile.getBirthDate()+"");
        txtGender.setText(profile.getGender());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}