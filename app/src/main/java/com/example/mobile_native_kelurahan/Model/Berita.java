package com.example.mobile_native_kelurahan.Model;

public class Berita {
    private String id;
    private String judul;
    private String subTitle;
    private String deskripsi;
    private String image;

    public Berita(String id, String judul, String subTitle, String deskripsi, String image) {
        this.id = id;
        this.judul = judul;
        this.subTitle = subTitle;
        this.deskripsi = deskripsi;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getImage() {
        return image;
    }
}
