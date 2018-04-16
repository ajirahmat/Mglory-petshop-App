package com.aji.userpc.mglory_petshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ListProduk extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ListView listViewProduk;
    RecyclerView recyclerView;
    DatabaseReference databaseProduk;

    List<Produk> produks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);

        listViewProduk = (ListView) findViewById(R.id.listViewProduks2);



        String kategori = getIntent().getStringExtra("kategori");
        databaseProduk = FirebaseDatabase.getInstance().getReference("Produk").child(kategori);

        produks = ShoppingCartHelper.getCatalog(getResources());


//        listViewProduk.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Produk produk = produks.get(i);
//                showUpdateDeleteDialog(produk.getId(), produk.getNamaProduk(),
//                        produk.getHargaProduk(), produk.getKategoriProduk());
//                return true;
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseProduk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                produks.clear();


                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {

                    Produk produk = produkSnapshot.getValue(Produk.class);
                    produks.add(produk);
                }

                ProdukAdapterUser produkAdapter = new ProdukAdapterUser(ListProduk.this, produks);
                listViewProduk.setAdapter(produkAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "eror : .", databaseError.toException());

            }
        });
    }
}
