package com.tkit.epasssystem.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.tkit.epasssystem.LoginActivity;
import com.tkit.epasssystem.MainActivity;
import com.tkit.epasssystem.model.ModelPassesType;
import com.tkit.epasssystem.model.PassengerProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Common
{
    public static  String url="http://115.124.127.109/epass/api/values/";
    //----For Conversion date to milisecond---------------------------------------------------------------------------
    public static Long GettingMiliSeconds(String Date)
    {
        long timeInMilliseconds = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {

            java.util.Date mDate = sdf.parse(Date);
            timeInMilliseconds = mDate.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  timeInMilliseconds;
    }

    //----for Getting path of image---------------------------------------------------------------------------------------
    public static String getPath(Context context, Uri uri) throws URISyntaxException
    {
        if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try
            {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    //----for autohide keyboard-----------------------------------------------------------------------------------------------
    public static void hideSoftKeyBord(Activity context)
    {
        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = context.getCurrentFocus();
        if (v == null)
            return;


        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //------For AutoLogin---------------------------------------------------------------------------------------------------------------

    static final String PREF_USER_NAME= "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }


    //----------------------for password
    static final String PREF_USER_PASSWORD= "password";

    static SharedPreferences getSharedPreferencesPassword(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserPassword(Context ctx, String userPassword)
    {
        SharedPreferences.Editor editor = getSharedPreferencesPassword(ctx).edit();
        editor.putString(PREF_USER_PASSWORD, userPassword);
        editor.commit();
    }

    public static String getUserPassword(Context ctx)
    {
        return getSharedPreferencesPassword(ctx).getString(PREF_USER_PASSWORD, "");
    }

    public static void clearUserPassword(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferencesPassword(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }


    //----For LogOut-------------------------------------------------------------------------------------------------------------------------------
    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void saveUserData(Context context, String str)
    {

     /*   SharedPreferences s= context.getSharedPreferences("MyFile",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor=s.edit();
        editor.putString("Name","MMMMM");
        editor.putInt("Id",555);
        editor.commit();
      */
        SharedPreferences sh=context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        sh.edit().putString("UserData",str).commit();
    }

    public static  int getUserId(Context context)
    {
        try {
            SharedPreferences sh=context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
            String str=  sh.getString("UserData","");
            JSONArray array=new JSONArray(str);
            JSONObject j= array.getJSONObject(0);
           return  j.getInt("UserId");
        }
        catch (Exception ex){}
        return 0;
    }
    public static int getPassengerId(Context context)
    {
        try
        {
            SharedPreferences sh=context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
            String str=  sh.getString("UserData","");
            JSONArray array=new JSONArray(str);
            return   array.getJSONObject(0).getInt("PassengerId");
        }
        catch (Exception ex){}
        return 0;
    }

    public static void saveAppData(Context context, String str)
    {
        SharedPreferences sh=context.getSharedPreferences("APPDATA",Context.MODE_PRIVATE);
        sh.edit().putString("APPDATA",str).commit();
    }
    public static ArrayList<ModelPassesType>  getPassesType(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("APPDATA",Context.MODE_PRIVATE);
        ArrayList<ModelPassesType>  list=new ArrayList<>();
        try
        {
            JSONObject j=new JSONObject(sh.getString("APPDATA",""));

            Gson gson=new Gson();
            JSONArray array=j.getJSONArray("PassesTypeList");
            for(int i=0;i<array.length();i++)
            {
                ModelPassesType m=gson.fromJson(array.getJSONObject(i).toString(),ModelPassesType.class);
                list.add(m);
            }
        }
        catch (Exception ex)
        {}
        return  list;
    }

    public static PassengerProfile getPassengerProfl(Context context)
    {
        SharedPreferences sh=context.getSharedPreferences("APPDATA",Context.MODE_PRIVATE);

        try {
            String s = sh.getString("APPDATA", "");
            JSONObject j = new JSONObject(s);
            Gson gson = new Gson();
            PassengerProfile p=gson.fromJson(j.getJSONObject("PassengerDetails").toString(),PassengerProfile.class);


            return  p;
        }
        catch (Exception ex)
        {}
        return  null;
    }
    //------------------------------------------------------------------------
}


