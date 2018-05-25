package com.example.codemaven3015.dialerapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHelper db;
    float values[] = {300, 400, 100, 500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        values = calculateData(values);
        linear.addView(new MyGraphview(this, values));

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
        //onclickSyncOnOff(true);
    }

    private float[] calculateData(float[] data) {
        float total = 0;
        for (int i = 0; i < data.length; i++) {
            total += data[i];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = 360 * (data[i] / total);
        }
        return data;
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent i;
        int id = item.getItemId();

        if (id == R.id.nav_missed) {
            i = new Intent(MainActivity.this, Missed_Call.class);
            startActivity(i);
        } else if (id == R.id.nav_setting) {
            View popupView = getLayoutInflater().inflate(R.layout.sych_popup,
                    null);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    (width / 3) * 2,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            Switch syncOnOff = popupView.findViewById(R.id.syncOnOff);
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

    public void onclickSyncOnOff(Boolean isChecked) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                //Log.e("Called","  thread  ");
                handler.postDelayed(this, 3000);
            }
        };
        Thread thread = new Thread(runnable);
        handler.postDelayed(runnable, 3000);
        if (isChecked) {
            thread.start();

        } else {

            thread.stop();
        }

    }

    private void onclickSync() {
        try {
            JSONArray listPath = db.getAllPathsNonUploaded();
            if (listPath.length() > 0) {
                for (int i = 0; i < listPath.length(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    String path = jsonObject.getString("path");
                    String id = jsonObject.getString("id");
                    UploadClass uploadClass = new UploadClass(this, path, id);
                    uploadClass.uploadVideoToServer();
                }

            } else {
                db.deleteTableEntries();
                showMessage("Info", "Nothing to sync");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void showMessage(String title, String message) {
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
                Window view = ((AlertDialog) dialog).getWindow();
                view.setBackgroundDrawableResource(R.color.white);
            }
        });
        dialog1.show();
    }

    public class MyGraphview extends View {
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] value_degree;
        private int[] COLORS = {Color.BLUE, Color.GREEN, Color.GRAY, Color.CYAN, Color.RED};
        RectF rectf = new RectF(10, 10, 400, 400);
        int temp = 0;

        public MyGraphview(Context context, float[] values) {
            super(context);
            value_degree = new float[values.length];
            for (int i = 0; i < values.length; i++) {
                value_degree[i] = values[i];
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);

            for (int i = 0; i < value_degree.length; i++) {//values2.length; i++) {
                if (i == 0) {
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, 0, value_degree[i], true, paint);
                } else {
                    temp += (int) value_degree[i - 1];
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, temp, value_degree[i], true, paint);
                }
            }
        }
    }
}
