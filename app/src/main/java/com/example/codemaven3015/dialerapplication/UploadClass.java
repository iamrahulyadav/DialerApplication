package com.example.codemaven3015.dialerapplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CodeMaven3015 on 5/15/2018.
 */

public class UploadClass {
    Context context;
    String path;
    String id = "";
    DataBaseHelper db;
    public UploadClass(Context context, String path){
        this.context = context;
        db = new DataBaseHelper(context);
        this.path = path;
    }
    public UploadClass(Context context, String path, String id){
        this.context = context;
        this.path = path;
        db = new DataBaseHelper(context);
        this.id = id;
    }
    public void uploadVideoToServer(){
        File videoFile = new File(path);
        RequestBody videoBody = RequestBody.create(MediaType.parse("audio/*"), videoFile);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", videoFile.getName(), videoBody);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.128:8888/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        VideoInterface vInterface = retrofit.create(VideoInterface.class);
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        Call<ResultObject> serverCom = vInterface.uploadVideoToServer(description,vFile);
        serverCom.enqueue(new Callback<ResultObject>() {
            @Override
            public void onResponse(Call<ResultObject> call, Response<ResultObject> response) {
                 ResultObject result = response.body();
                   if(result != null) {
                      Toast.makeText(context, "Result " + result.getSuccess(), Toast.LENGTH_LONG).show();
                      Log.d("check", "Result " + result.getSuccess());
                      if(!id.equals("")){
                          db.updateFlag(id);
                      }
                }
            }

            @Override
            public void onFailure(Call<ResultObject> call, Throwable t) {
                Log.d("check", "Error message " + t.getMessage());
            }
        });
    }
}
