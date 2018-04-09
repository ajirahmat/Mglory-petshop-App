package com.aji.userpc.mglory_petshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by user pc on 3/19/2018.
 */

public class ProdukAdapterUser  extends ArrayAdapter<Produk> {

    private Activity context;
    List<Produk> produks;

    public ProdukAdapterUser(Activity context, List<Produk> produks) {
        super(context, R.layout.item_layout, produks);
        this.context = context;
        this.produks = produks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_layout, null, true);
        ImageView imageViewProduk = (ImageView) listViewItem.findViewById(R.id.produkImage);
        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.nama_produk);
        TextView textViewHarga = (TextView) listViewItem.findViewById(R.id.harga_produk);

        Produk pd =  produks.get(position);

        Glide.with(context)
                .load(pd.getImage_url())
                .into(imageViewProduk );

        textViewNama.setText(pd.getNamaProduk());
        textViewHarga.setText((pd.getHargaProduk()));

        return listViewItem;
    }
}
