package com.example.mobile_native_kelurahan.Model;

public class Status {
    private String uuid;
    private String status;
    private String keterangan;
    private String createdAt;
    private String filePdf;
    private String IdMasyarakat;
    private String IdSurat;
    private String nik;
    private String namaLengkap;
    private String jenisKelamin;
    private String tempatLahir;
    private String tglLahir;
    private String agama;
    private String pendidikan;
    private String pekerjaan;
    private String golonganDarah;
    private String statusPerkawinan;
    private String tglPerkawinan;
    private String statusKeluarga;
    private String kewarganegaraan;
    private String noPaspor;
    private String noKitap;
    private String namaAyah;
    private String namaIbu;
    private String id;
    private String namaSurat;
    private String image;

    public Status(String uuid, String status, String keterangan, String createdAt, String filePdf, String idMasyarakat, String idSurat, String nik, String namaLengkap, String jenisKelamin, String tempatLahir, String tglLahir, String agama, String pendidikan, String pekerjaan, String golonganDarah, String statusPerkawinan, String tglPerkawinan, String statusKeluarga, String kewarganegaraan, String noPaspor, String noKitap, String namaAyah, String namaIbu, String id, String namaSurat, String image) {
        this.uuid = uuid;
        this.status = status;
        this.keterangan = keterangan;
        this.createdAt = createdAt;
        this.filePdf = filePdf;
        this.IdMasyarakat = idMasyarakat;
        this.IdSurat = idSurat;
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.jenisKelamin = jenisKelamin;
        this.tempatLahir = tempatLahir;
        this.tglLahir = tglLahir;
        this.agama = agama;
        this.pendidikan = pendidikan;
        this.pekerjaan = pekerjaan;
        this.golonganDarah = golonganDarah;
        this.statusPerkawinan = statusPerkawinan;
        this.tglPerkawinan = tglPerkawinan;
        this.statusKeluarga = statusKeluarga;
        this.kewarganegaraan = kewarganegaraan;
        this.noPaspor = noPaspor;
        this.noKitap = noKitap;
        this.namaAyah = namaAyah;
        this.namaIbu = namaIbu;
        this.id = id;
        this.namaSurat = namaSurat;
        this.image = image;
    }

    public String getUuid() {
        return uuid;
    }

    public String getStatus() {
        return status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getIdMasyarakat() {
        return IdMasyarakat;
    }

    public String getIdSurat() {
        return IdSurat;
    }

    public String getNik() {
        return nik;
    }

    public String getFilePdf() {
        return filePdf;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public String getAgama() {
        return agama;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public String getStatusPerkawinan() {
        return statusPerkawinan;
    }

    public String getTglPerkawinan() {
        return tglPerkawinan;
    }

    public String getStatusKeluarga() {
        return statusKeluarga;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public String getNoPaspor() {
        return noPaspor;
    }

    public String getNoKitap() {
        return noKitap;
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public String getId() {
        return id;
    }

    public String getNamaSurat() {
        return namaSurat;
    }

    public String getImage() {
        return image;
    }
}
