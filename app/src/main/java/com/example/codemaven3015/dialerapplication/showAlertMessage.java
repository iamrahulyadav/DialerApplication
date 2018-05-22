package com.example.codemaven3015.dialerapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;

/**
 * Created by CodeMaven3015 on 3/19/2018.
 */

public class showAlertMessage {
    Context context;
    String message;
    String title;

    public showAlertMessage(Context context, String message, String title){
        this.context = context;
        this.message = message;
        this.title = title;
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
    public void showMessageWithYesAndNo(final YesAlertCallback callback){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callback.yesFunction();
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


public interface YesAlertCallback {
    public void yesFunction();
}

}
