package com.aji.userpc.mglory_petshop;


import java.util.List;
import java.util.Vector;

import android.content.res.Resources;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Produk> catalog;
    private static List<Produk> cart;

    public static List<Produk> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Produk>();
        }

        return catalog;
    }

    public static List<Produk> getCart() {
        if(cart == null) {
            cart = new Vector<Produk>();
        }

        return cart;
    }

}