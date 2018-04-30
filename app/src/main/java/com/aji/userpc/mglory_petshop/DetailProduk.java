package com.aji.userpc.mglory_petshop;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DetailProduk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        List<Produk> catalog = ShoppingCartHelper.getCatalog(getResources());
        final List<Produk> cart = ShoppingCartHelper.getCart();

        int productIndex = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
        final Produk selectedProduct = catalog.get(productIndex);

        ImageView productImageView = (ImageView) findViewById(R.id.PdImage);
//        productImageView.setImageDrawable(Drawable.createFromPath(selectedProduct.image_url));
        Glide.with(DetailProduk.this).load(selectedProduct.image_url).into(productImageView);

        TextView productTitleTextView = (TextView) findViewById(R.id.PdNama);
        productTitleTextView.setText(selectedProduct.getNamaProduk());

        TextView productPriceTextView = (TextView) findViewById(R.id.PdHarga);
        productPriceTextView.setText(selectedProduct.getHargaProduk());

        TextView productDeskripsi = (TextView) findViewById(R.id.PdDeskripsi);
        productDeskripsi.setText(selectedProduct.getDeskripsiProduk());

        ImageButton addToCartButton = (ImageButton) findViewById(R.id.ButtonAddCart);

        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                cart.add(selectedProduct);
                finish();
            }
        });

        if(cart.contains(selectedProduct)) {
            addToCartButton.setEnabled(false);
        }

    }
}
