package com.example.codemaven3015.dialerapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Admin on 4/18/2018.
 */

class Contact_Us {
    Context context;
    Contact_Us(Context context ){
        this.context = context;

    }
    public void callNow (String phone){
        Intent makeCall = new Intent(Intent.ACTION_CALL);
        makeCall.setData(Uri.parse("tel:"+phone));
        context.startActivity(makeCall);
    }
    public void emailNow(String mail){
        context.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:to@gmail.com")));

    }
}
