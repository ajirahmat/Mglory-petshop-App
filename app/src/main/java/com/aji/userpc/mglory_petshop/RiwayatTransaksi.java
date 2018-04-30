package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RiwayatTransaksi extends AppCompatActivity {

    private static final String TAG = RiwayatTransaksi.class.getSimpleName();
    ListView listViewProduks;
    DatabaseReference databaseProduk;
    FirebaseAuth mAuth;

    List<com.aji.userpc.mglory_petshop.Transaksi> transaksis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);

        mAuth = FirebaseAuth.getInstance();
        listViewProduks = (ListView) findViewById(R.id.ListViewTransaksi);
        transaksis = new ArrayList<>();

        String user = mAuth.getCurrentUser().getDisplayName().toString();
        databaseProduk = FirebaseDatabase.getInstance().getReference("Transaksi").child(user);

        getData();

    }

    private void getData() {
        databaseProduk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                transaksis.clear();


                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {

                    com.aji.userpc.mglory_petshop.Transaksi transaksi = produkSnapshot.getValue(com.aji.userpc.mglory_petshop.Transaksi.class);
                    transaksis.add(transaksi);
                }

                TransaksiAdapter produkAdapter = new TransaksiAdapter(RiwayatTransaksi.this, transaksis);
                listViewProduks.setAdapter( produkAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "eror : .", databaseError.toException());

            }
        });
    }
}
