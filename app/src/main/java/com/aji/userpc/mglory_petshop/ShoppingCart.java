package com.aji.userpc.mglory_petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    private List<Produk> mCartList;
    private ProdukAdapterCart mProductAdapter;
    int totalHarga;
    TextView tvTotallHarga;
    Button Checkout;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        tvTotallHarga = (TextView) findViewById(R.id.TotalHarga);
        mCartList = ShoppingCartHelper.getCart();
        Checkout = (Button) findViewById(R.id.CheckoutBtn);
        removeButton = (Button) findViewById(R.id.BtnHapus);

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
            totalHarga+=Integer.parseInt(mCartList.get(i).getHargaProduk());
        }

        tvTotallHarga.setText("Rp "+totalHarga);

        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProdukAdapterCart(ShoppingCart.this, mCartList);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Produk selectedProduct = mCartList.get(position);
                if(selectedProduct.selected == true)
                    selectedProduct.selected = false;
                else
                    selectedProduct.selected = true;

                mProductAdapter.notifyDataSetInvalidated();

            }
        });

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String total;
//                total = "Rp "+ String.valueOf(totalHarga);
//                Intent i = new Intent (ShoppingCart.this, CheckoutOrder.class );
//                i.putExtra("total",total);
//                startActivity(i);
            }
        });



        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for(int i=mCartList.size()-1; i>=0; i--) {

                    if(mCartList.get(i).selected) {
                        mCartList.remove(i);
                    }
                }
                mProductAdapter.notifyDataSetChanged();
            }
        });



    }
}
