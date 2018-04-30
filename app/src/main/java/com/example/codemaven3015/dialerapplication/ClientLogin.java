package com.example.codemaven3015.dialerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClientLogin extends AppCompatActivity {
    Button submitClientButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);
        onClickSubmitButton();

    }

    private void onClickSubmitButton() {
        submitClientButton = findViewById(R.id.submitClientButton);
        submitClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientLogin.this, Login.class);
                startActivity(i);
            }
        });
    }
}
