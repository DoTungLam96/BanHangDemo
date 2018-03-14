package com.example.admin.banhangdemo.model;

import java.io.Serializable;

/**
 * Created by Admin on 03/01/2018.
 */

public class SanPham  implements Serializable{
    private int id;
    private String tenSP;
    private int giaSP;
    private String hinhAnhSP;
    private String moTa;
    private int idLoaiSP;

    public SanPham(int id, String tenSP, int giaSP, String hinhAnhSP, String moTa, int idLoaiSP) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhAnhSP = hinhAnhSP;
        this.moTa = moTa;
        this.idLoaiSP = idLoaiSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(int idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }
}
