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

public class TransaksiAdapter  extends ArrayAdapter<Transaksi>  {

    private Activity context;
    List<Transaksi> transaksis;

    public TransaksiAdapter(Activity context, List<Transaksi> transaksis) {
        super(context, R.layout.item_layout, transaksis);
        this.context = context;
        this.transaksis = transaksis;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_layout_riwayat, null, true);
        ImageView imageViewProduk = (ImageView) listViewItem.findViewById(R.id.imageView2);
        TextView textViewKode= (TextView) listViewItem.findViewById(R.id.kodeTransaksi);
        TextView textViewHarga = (TextView) listViewItem.findViewById(R.id.totalTransaksi);

        Transaksi pd =  transaksis.get(position);


        textViewHarga.setText((pd.getJumlah()));

        return listViewItem;
    }
}
