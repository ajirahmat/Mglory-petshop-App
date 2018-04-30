package com.aji.userpc.mglory_petshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class CheckoutOrder extends AppCompatActivity {

    DatabaseReference databaseProduk;
    public static FirebaseStorage storage;
    public static StorageReference storageRef;
    FirebaseAuth mAuth;
    String totalharga;
    TextView tvTotal;
    private Button btPlacesAPI;
    private TextView tvPlaceAPI;
    Button btnPay;
    private int PLACE_PICKER_REQUEST = 1;
    private ProgressDialog pbDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_order);
        Intent i = getIntent();
        totalharga = i.getStringExtra("total");
        tvTotal = findViewById(R.id.tvTotalHarga);
        tvPlaceAPI = (TextView) findViewById(R.id.tvPlace);
        btPlacesAPI = (Button)findViewById(R.id.btn_place);
        pbDialog = new ProgressDialog(this);
        btnPay = (Button) findViewById(R.id.pay_button);

        databaseProduk = FirebaseDatabase.getInstance().getReference("Transaksi");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        tvTotal.setText(totalharga);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduk();
            }
        });

        btPlacesAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuat Intent untuk Place Picker

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    //menjalankan place picker

                     startActivityForResult(builder.build(CheckoutOrder.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private void addProduk() {

        final String total = tvTotal.getText().toString().trim();
        final String tujuan = tvPlaceAPI.getText().toString().trim();
        String pemesan =  mAuth.getCurrentUser().getDisplayName();

        if (!TextUtils.isEmpty(total)) {

            pbDialog.setMessage("Uploading..");
            pbDialog.setIndeterminate(true);
            pbDialog.show();

            //push atau insert data ke firebase database
            String id = databaseProduk.push().getKey();
            Transaksi produk = new Transaksi (id,total,pemesan,tujuan);
            databaseProduk.child(pemesan).child(id).setValue(produk);
            pbDialog.dismiss();
            Toast.makeText(CheckoutOrder.this, "berhasil transaksi berhasil", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "transaksi gagal ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(CheckoutOrder.this, data);
                String toastMsg = String.format(
                        "Place: %s \n" +
                                "Alamat: %s \n" +
                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
                tvPlaceAPI.setText(toastMsg);
            }
        }
    }
}
