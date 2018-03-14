package com.example.admin.banhangdemo.model;

/**
 * Created by Admin on 03/01/2018.
 */

public class LoaiSP {

    private int idLoaiSP;
    private String tenLoaiSP;
    private String hinhAnhLoaiSP;

    public LoaiSP(int idLoaiSP, String tenLoaiSP, String hinhAnhLoaiSP) {
        this.idLoaiSP = idLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhAnhLoaiSP = hinhAnhLoaiSP;
    }

    public int getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(int idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnhLoaiSP() {
        return hinhAnhLoaiSP;
    }

    public void setHinhAnhLoaiSP(String hinhAnhLoaiSP) {
        this.hinhAnhLoaiSP = hinhAnhLoaiSP;
    }
}
