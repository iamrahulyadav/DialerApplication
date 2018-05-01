package com.example.codemaven3015.dialerapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;

/**
 * Created by Admin on 5/1/2018.
 */

public class ShowMessage {
    String title;
    String message;
    Context context;
    public  ShowMessage(String title,String message,Context context){
        this.title = title;
        this.message = message;
        this.context = context;
    }

    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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


