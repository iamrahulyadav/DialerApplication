package com.example.codemaven3015.dialerapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Admin on 4/11/2018.
 */

public class RecordingService extends Service {
    private MediaRecorder rec;
    AudioManager audioManager;
    private boolean recordstarted = false;
    private File file;
    CharSequence sdf;
    String path="";
    CharSequence timeValue = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DataBaseHelper db;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startId) {
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        db = new DataBaseHelper(getApplicationContext());

      file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        TelephonyManager manager=(TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener() {
                           @Override
                           public void onCallStateChanged(int state, String incomingNumber) {
                               //super.onCallStateChanged(state, incomingNumber); {
                                   if (TelephonyManager.CALL_STATE_IDLE == state ) {
                                       Log.e("1","check");
                                       if(rec != null) {
                                           if (recordstarted) {
                                               recordstarted = false;
                                               rec.stop();
                                               audioManager.setMode(AudioManager.MODE_NORMAL);
                                               Log.e("REC4", "stopped");
                                               rec.reset();
                                               Log.e("REC5", "reset");
                                               rec.release();
                                               Log.e("REC6", "release");
                                               recordstarted = false;
                                               Log.e("REC7", "stopped");
                                               if(!path.isEmpty()) {
                                                   db.updatetableRecording(path, 0);
                                               }

                                               stopSelf();
                                               Intent i = new Intent(getApplicationContext(),Call_Details.class);
                                               i.putExtra("TimeValue",timeValue.toString());
                                               i.putExtra("path",path);
                                               startActivity(i);

                                           }
                                       }

                                   } else if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                                       Log.e("2","check");
                                       Date date=new Date();
                                       sdf = DateFormat.format("MM-dd-YY-hh-mm-ss",date.getTime());
                                       timeValue = DateFormat.format("hh-mm-ss",date.getTime());
                                       rec=new MediaRecorder();
                                       sdf = sharedPreferences.getString("phone","")+sdf;
                                       rec.setAudioSource(MediaRecorder.AudioSource.MIC);
                                       rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                                       rec.setOutputFile(file.getAbsolutePath()+"/"+sdf+"rec.3gp");
                                       path = file.getAbsolutePath()+"/"+sdf+"rec.3gp";

                                       Log.e("REC1",file.getAbsolutePath()+"/"+sdf+"rec.3gp");
                                       rec.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                                       try {
                                           rec.prepare();
                                           Log.e("REC2","prepare");
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }

                                       audioManager.setMode(AudioManager.MODE_IN_CALL);
                                       audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0);
                                       rec.start();
                                       Log.e("REC3","start");
                                       recordstarted = true;
                                   }


                           }
                       }

            ,PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
        }
    }







