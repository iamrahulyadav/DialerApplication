package com.example.codemaven3015.dialerapplication;

import android.app.Service;
import android.content.Intent;
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
    String path="sdcard/alarms/";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startId) {

      file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Date date=new Date();
        CharSequence sdf= DateFormat.format("MM-dd-YY-hh-mm-ss",date.getTime());
        rec=new MediaRecorder();
        rec.setAudioSource(MediaRecorder.AudioSource.MIC);
        rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        rec.setOutputFile(file.getAbsolutePath()+"/"+sdf+"rec.3gp");
        Log.e("REC1",file.getAbsolutePath()+"/"+sdf+"rec.3gp");
        rec.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        TelephonyManager manager=(TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener() {
                           @Override
                           public void onCallStateChanged(int state, String incomingNumber) {
                               //super.onCallStateChanged(state, incomingNumber); {
                                   if (TelephonyManager.CALL_STATE_IDLE == state ) {
                                       Log.e("1","check");
                                       if(recordstarted) {
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

                                           stopSelf();

                                       }

                                   } else if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                                       Log.e("2","check");
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







