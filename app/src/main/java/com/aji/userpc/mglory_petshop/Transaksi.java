package com.aji.userpc.mglory_petshop;

/**
 * Created by user pc on 4/29/2018.
 */

public class Transaksi {
    String id;
    String jumlah;
    String pemesan;
    String tujuan;

    public Transaksi(String id, String jumlah, String pemesan, String tujuan) {
        this.id = id;
        this.jumlah = jumlah;
        this.pemesan = pemesan;
        this.tujuan = tujuan;
    }
    public Transaksi(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getPemesan() {
        return pemesan;
    }

    public void setPemesan(String pemesan) {
        this.pemesan = pemesan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
}
