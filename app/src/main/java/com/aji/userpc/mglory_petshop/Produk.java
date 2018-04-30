package com.aji.userpc.mglory_petshop;

/**
 * Created by user pc on 3/17/2018.
 */

public class Produk {
    private String id;
    private String namaProduk;
    private String hargaProduk;
    private String deskripsiProduk;
    private String kategoriProduk;
    public String image_url;
    public boolean selected;

    public Produk(){

    }
    public Produk(String id, String namaProduk, String hargaProduk, String deskripsiProduk, String kategoriProduk,String image_url) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.kategoriProduk = kategoriProduk;
        this.image_url = image_url;
    }

    public Produk(String id, String namaProduk, String hargaProduk, String deskripsiProduk, String kategoriProduk) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.kategoriProduk = kategoriProduk;
    }

    public Produk(String id, String namaProduk, String hargaProduk, String kategoriProduk) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.kategoriProduk = kategoriProduk;
    }


    public String getImage_url() {
        return image_url;
    }

    public String getId() {
        return id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getHargaProduk() {

        return hargaProduk;
    }

    public String getDeskripsiProduk() {

        return deskripsiProduk;
    }

    public String getKategoriProduk() {

        return kategoriProduk;
    }
}
