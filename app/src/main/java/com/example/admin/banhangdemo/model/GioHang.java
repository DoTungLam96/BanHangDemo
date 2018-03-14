package com.example.admin.banhangdemo.model;

/**
 * Created by Admin on 06-Mar-18.
 */

public class GioHang {
private int idSP;
private long giaSP;
private String tenSP;
private String hinhSP;
private int soLuong;

    public GioHang(int idSP, long giaSP, String tenSP, String hinhSP, int soLuong) {
        this.idSP = idSP;
        this.giaSP = giaSP;
        this.tenSP = tenSP;
        this.hinhSP = hinhSP;
        this.soLuong = soLuong;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
