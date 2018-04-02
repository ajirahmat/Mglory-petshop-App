package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SignOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
    }

    public void Logout(View view) {
    Intent intent = new Intent(SignOut.this, MainActivity.class);
    FirebaseAuth.getInstance();
    startActivity(intent);
    }
}