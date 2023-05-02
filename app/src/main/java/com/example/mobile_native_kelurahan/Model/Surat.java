package com.example.mobile_native_kelurahan.Model;

import java.io.Serializable;

public class Surat implements Serializable {
    private String idSurat;
    private String namaSurat;
    private String image;

    public Surat(String idSurat, String namaSurat, String image) {
        this.idSurat = idSurat;
        this.namaSurat = namaSurat;
        this.image = image;
    }

    public String getIdSurat() {
        return idSurat;
    }

    public String getNamaSurat() {
        return namaSurat;
    }

    public String getImage() {
        return image;
    }
}
