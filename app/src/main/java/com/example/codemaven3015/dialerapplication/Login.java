package com.example.codemaven3015.dialerapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button login_btn;
    EditText userPasswordEditText,userNameEditText;
    String userName = "", password = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAPTURE_AUDIO_OUTPUT,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.RECORD_AUDIO

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userNameEditText = findViewById(R.id.userNameEditText);
        userPasswordEditText = findViewById(R.id.userPasswordEditText);

        onClickLoginButton();
        userNameEditText.setText(sharedPreferences.getString("userName",""));
        userPasswordEditText.setText(sharedPreferences.getString("password",""));
        verifyStoragePermissions();
    }

    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    1
            );
        }
    }

    private void onClickLoginButton() {


        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkifFieldsHaveValidValues()) {
                    CheckBox  checkBox = findViewById(R.id.checkbox);
                    if(checkBox.isChecked()){
                        editor.putString("userName",userName);
                        editor.putString("password",password);
                        editor.apply();
                    }
                    loginServiceCall();
                }
                //Intent i = new Intent(Login.this, MainActivity.class);
                //startActivity(i);
            }
        });
    }

    private boolean checkifFieldsHaveValidValues() {
        userName= userNameEditText.getText().toString().trim();
        password = userPasswordEditText.getText().toString().trim();
        if(userName.isEmpty()){
            userNameEditText.setError("Please enter  Username");
            return false;
        }else if (password.isEmpty()){
            userPasswordEditText.setError("Please enter  Password");
            return false;
        }
        return true;
    }


    private void loginServiceCall() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.121:8888/Dialer_service/REST/WebService/login";
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("my app", "123"+response);
                        if(response.equals("\"success\"")){
                            Intent i = new Intent(Login.this, Missed_Call.class);
                            startActivity(i);

                        }else {
                            showMessage("Error","Wrong Username or password");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("my app1","error");

            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password",password);
                return params;
            }

        };
        requestQueue.add(jsonObjRequest);
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        //builder.set
        builder.setMessage(message);
        //builder.show();
        AlertDialog dialog1 = builder.create();
        dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Window view = ((AlertDialog)dialog).getWindow();
                view.setBackgroundDrawableResource(R.color.white);
            }
        });
        dialog1.show();
    }
}
