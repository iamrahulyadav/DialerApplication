package com.example.codemaven3015.dialerapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Missed_Call extends AppCompatActivity  {

    String phone_number = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed__call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setLogoutClick();
        callMissCallAPI();

    }

    @Override
    public void onResume(){
        super.onResume();

        //will be executed onResume
    }

    private void setLogoutClick() {
        ImageButton logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void callMissCallAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.128:8888/Dialer_service/REST/webservice/GetContactDetail";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("my app", "123"+response);
                try {
                    init(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("my app", "123"+error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void init(JSONArray jsonArray) throws JSONException {
        TableLayout stk = (TableLayout) findViewById(R.id.table);

        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        TableRow.LayoutParams lp =(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        lp.weight=1;

        tv0.setLayoutParams(lp);
        tv0.setText(" S.No ");
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setPadding(0,30,0,30);

        tv0.setBackground(getResources().getDrawable(R.drawable.border_fill));


        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(lp);
        tv1.setText(" MOBILE NO ");
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(0,30,0,30);
        tv1.setBackground(getResources().getDrawable(R.drawable.border_fill));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setLayoutParams(lp);
        tv2.setText("DATE ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(0,30,0,30);
        tv2.setBackground(getResources().getDrawable(R.drawable.border_fill));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(lp);
        tv3.setText("STATUS");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.BLACK);
        tv3.setPadding(0,30,0,30);
        tv3.setBackground(getResources().getDrawable(R.drawable.border_fill));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);

            TableRow tBLine = new TableRow(this);
            //View line = new View(this);
            //TableLayout.LayoutParams lp1 = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 2));
            //lp1.setMargins(5,30,5,30);
           // line.setLayoutParams(lp1);
            //line.setBackgroundColor(Color.rgb(51, 51, 51));
            //stk.addView(line);
            final TableRow tbrow = new TableRow(this);
            //tbrow.setBackground(getResources().getDrawable(R.drawable.selector_focus));
            lp.weight=1;
            TextView t1v = new TextView(this);
            t1v.setLayoutParams(lp);
            t1v.setText("" + i);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setPadding(0,30,0,30);
            t1v.setBackground(getResources().getDrawable(R.drawable.border));
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setLayoutParams(lp);
            //t2v.setText("989898989" + i);
            t2v.setText(obj.getString("phone"));
            final String phone = obj.getString("phone");
            final String j = obj.getString("contactId");
            t2v.setTextColor(Color.BLUE);
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(0,30,0,30);
            t2v.setBackground(getResources().getDrawable(R.drawable.border));
            t2v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    editor.putString("id",j);
                    editor.putString("phone",phone);
                    editor.apply();
                    tbrow.setBackgroundColor(getResources().getColor(R.color.lightPink));
                    //askPermissionForCall(tv.getText().toString());
                    Contact_Us contact_us = new Contact_Us(getApplicationContext());
                    contact_us.callNow(tv.getText().toString());
                }
            });
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setLayoutParams(lp);
            //
             String date = (obj.getString("dtcontactSeen"));
            String substr=date.substring(date.length()-11);
            t3v.setText(substr);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setPadding(0,30,0,30);
            t3v.setBackground(getResources().getDrawable(R.drawable.border));
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setLayoutParams(lp);

           t4v.setText(obj.getString("status"));
           String status=(obj.getString("status"));
            t4v.setTextColor(Color.BLUE);
            if((status.equals("1")))
            {
                t4v.setText("Not Attended");
                t4v.setTextColor(Color.RED);
            }
               else if((status.equals("2")))
                {
                    t4v.setText("Not Picked");
                    t4v.setTextColor(getResources().getColor(R.color.mergenta));
                }

                else if(status.equals("3"))
            {
                t4v.setText("Interested");
                t4v.setTextColor(getResources().getColor(R.color.green));
            }

            else if(status.equals("4"))
            {
                t4v.setText("Not Interested");
            }

            else if(status.equals("5"))
            {
                t4v.setText("Call Later");
                t4v.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            //t4v.setText("Update Status");

            t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(0,30,0,30);
            t4v.setBackground(getResources().getDrawable(R.drawable.border));
//            t4v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    TextView tv = (TextView)v;
//                    editor.putString("id",j);
//                    editor.apply();
//                   Intent i = new Intent(Missed_Call.this,Call_Details.class);
//                    startActivity(i);
//
//                }
//            });
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
}



    private void askPermissionForCall(String phone) {
        phone_number = phone;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.CALL_PHONE
            }, 8);
            return;
        }else {
            Contact_Us contact_us = new Contact_Us(this);
            contact_us.callNow(phone);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 8:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //startActivity(makeCall);
                    Contact_Us contact_us = new Contact_Us(this);
                    if(!(phone_number.equals(""))) {
                        contact_us.callNow(phone_number);
                    }
                }


        }
    }
    public void logout(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setCancelable(true);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure yo want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Missed_Call.this , Login.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("No", null);
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
