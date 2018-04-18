package com.example.codemaven3015.dialerapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Missed_Call extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed__call);
        init();
    }
    public void init(){
        TableLayout stk = (TableLayout) findViewById(R.id.table);

        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        TableRow.LayoutParams lp =(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        lp.weight=1;
        tv0.setLayoutParams(lp);
        tv0.setText(" S.No ");
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);


        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(lp);
        tv1.setText(" MOBILE NO ");
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setLayoutParams(lp);
        tv2.setText("DATE ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(lp);
        tv3.setText("STATUS");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < 25; i++) {

            TableRow tBLine = new TableRow(this);
            View line = new View(this);
            TableLayout.LayoutParams lp1 = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 2));
            lp1.setMargins(5,30,5,30);
            line.setLayoutParams(lp1);
            line.setBackgroundColor(Color.rgb(51, 51, 51));
            stk.addView(line);
            TableRow tbrow = new TableRow(this);
            lp.weight=1;
            TextView t1v = new TextView(this);
            t1v.setLayoutParams(lp);
            t1v.setText("" + i);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setLayoutParams(lp);
            t2v.setText("Product " + i);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setLayoutParams(lp);
            t3v.setText("Rs." + i);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setLayoutParams(lp);
            t4v.setText("" + i * 15 / 32 * 10);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
}
}
