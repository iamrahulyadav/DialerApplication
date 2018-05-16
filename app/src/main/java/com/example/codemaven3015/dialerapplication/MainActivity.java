package com.example.codemaven3015.dialerapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DataBaseHelper(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent i;
        int id = item.getItemId();

        if (id == R.id.nav_missed) {
            i = new Intent(MainActivity.this,Missed_Call.class);
            startActivity(i);
        } else if (id == R.id.action_settings) {
            View popupView = getLayoutInflater().inflate(R.layout.sych_popup,
                    null);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    (width/3)*2,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            Switch syncOnOff =  popupView.findViewById(R.id.syncOnOff);
            syncOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onclickSyncOnOff(isChecked);
                }
            });
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);

            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        } else if (id == R.id.nav_synch) {

             onclickSync();

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onclickSyncOnOff(Boolean isChecked){

    }

    private void onclickSync() {
        try {
            JSONArray listPath = db.getAllPathsNonUploaded();
            if(listPath.length()>0){
                for(int i = 0;i<listPath.length();i++){
                    JSONObject jsonObject = new JSONObject();
                    String path = jsonObject.getString("path");
                    String id= jsonObject.getString("id");
                    UploadClass uploadClass = new UploadClass(this,path,id);
                    uploadClass.uploadVideoToServer();
                }

            }else{
                db.deleteTableEntries();
                showMessage("Info","Nothing to sync");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


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
