package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckoutOrder extends AppCompatActivity {
    String totalharga;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_order);
        Intent i = getIntent();
        totalharga = i.getStringExtra("total");
        tvTotal = findViewById(R.id.tvTotalHarga);

        tvTotal.setText(totalharga);
    }
}
