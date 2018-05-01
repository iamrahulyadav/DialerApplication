package com.example.codemaven3015.dialerapplication;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Call_Details extends AppCompatActivity {
    int status=0;
    TextView number1, callStartTime, callDuration1, date1;
    Button submit,callLater,notInterested,interested,notPicked;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__details);
        number1 = findViewById(R.id.number);
        callStartTime = findViewById(R.id.callStartTime);
        callDuration1 = findViewById(R.id.callDuration);
        String TimeValue = getIntent().getStringExtra("TimeValue").toString();
        callStartTime.setText("Call Start Time:- "+TimeValue);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        date1 = findViewById(R.id.date);
        LastCall();
        setBuutonIds();

    }

    private void setBuutonIds()
    {
        submit=findViewById(R.id.submit);
        callLater=findViewById(R.id.callLater);
        notInterested=findViewById(R.id.notInterested);
        interested=findViewById(R.id.interested);
        notPicked=findViewById(R.id.notPicked);
        notPicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=2;
                changeFocusOnClickButton();
            }
        });

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=3;
                changeFocusOnClickButton();
            }
        });

        notInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=4;
                changeFocusOnClickButton();
            }
        });

        callLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=5;
                changeFocusOnClickButton();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCallforSatusUpdate();
            }
        });
    }

    private void serviceCallforSatusUpdate() {
        String url = "http://192.168.101.179:8888/Dialer_service/REST/webservice/updateStatus";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e("success",response.toString());

                if(response.equals("\"status updated\"")){
                    //showMessage = new ShowMessage("Error","Wrong Username or password",getApplicationContext());
                    //showMessage.showMessage();
                    showMessage("Success","Status Updated");


                }else {
                    //showMessage = new ShowMessage("Error","Wrong Username or password",getApplicationContext());
                    //showMessage.showMessage();
                    showMessage("Error","Status not Updated");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("error",error.toString());
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("contactid", sharedPreferences.getString("id",""));
                params.put("status",status+"");
                return params;
            }

        };
        requestQueue.add(jsonObjectRequest);

    }

    private void changeFocusOnClickButton()
 {
    if(status==2)
    {
        callLater.setBackgroundColor(getResources().getColor(R.color.greenLight));
        notInterested.setBackgroundColor(getResources().getColor(R.color.green));
        interested.setBackgroundColor(getResources().getColor(R.color.green));
       notPicked.setBackgroundColor(getResources().getColor(R.color.green));
    }else if(status==3)
     {
         callLater.setBackgroundColor(getResources().getColor(R.color.green));
         notInterested.setBackgroundColor(getResources().getColor(R.color.greenLight));
         interested.setBackgroundColor(getResources().getColor(R.color.green));
         notPicked.setBackgroundColor(getResources().getColor(R.color.green));
     }else if(status==4)
     {
         callLater.setBackgroundColor(getResources().getColor(R.color.green));
         notInterested.setBackgroundColor(getResources().getColor(R.color.green));
         interested.setBackgroundColor(getResources().getColor(R.color.greenLight));
         notPicked.setBackgroundColor(getResources().getColor(R.color.green));
     }else if(status==5)
     {
         callLater.setBackgroundColor(getResources().getColor(R.color.green));
         notInterested.setBackgroundColor(getResources().getColor(R.color.green));
         interested.setBackgroundColor(getResources().getColor(R.color.green));
         notPicked.setBackgroundColor(getResources().getColor(R.color.greenLight));
     }
 }

    public String LastCall() {
        StringBuffer sb = new StringBuffer();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.READ_CALL_LOG
            }, 8);

        }
        Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
        cur.moveToFirst();
        int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
        int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
        //int start_time=cur.getColumnIndex(CallLog.Calls.);
        int date=cur.getColumnIndex(CallLog.Calls.DATE);
        sb.append("Call Details : \n");


        //while ( cur.moveToNext() ) {
        String phNumber = cur.getString( number );
        String callDuration = cur.getString( duration );
        String datetext=cur.getString(date);

        Date callDayTime = new Date(Long.valueOf(datetext));
        number1.setText(phNumber);
        callDuration1.setText("Call Duration:- "+callDuration+" sec");
        date1.setText("Date:- "+callDayTime.toString());
        //sb.append( "\nPhone Number:"+phNumber);
        // break;
        // }
        cur.close();
        String str=sb.toString();
        return str;
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

