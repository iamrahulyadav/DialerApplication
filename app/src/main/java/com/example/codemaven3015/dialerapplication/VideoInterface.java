package com.example.codemaven3015.dialerapplication;

/**
 * Created by CodeMaven3015 on 5/14/2018.
 */

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
public interface VideoInterface {
    @Multipart
    @POST("Dialer_service/REST/upload/")
    Call<ResultObject> uploadVideoToServer(
            @Part("file1") RequestBody description,
            @Part MultipartBody.Part file);
}
