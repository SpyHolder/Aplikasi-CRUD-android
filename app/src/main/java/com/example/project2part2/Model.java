package com.example.project2part2;

public class Model {
    private String id,namaTamu,keperluan,perusahaan,jamMasuk,JamKeluar,fotoKeterangan;

    public Model() {

    }

    public Model(String id, String namaTamu, String keperluan, String perusahaan, String jamMasuk, String jamKeluar, String fotoKeterangan) {
        this.id = id;
        this.namaTamu = namaTamu;
        this.keperluan = keperluan;
        this.perusahaan = perusahaan;
        this.jamMasuk = jamMasuk;
        this.JamKeluar = jamKeluar;
        this.fotoKeterangan = fotoKeterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaTamu() {
        return namaTamu;
    }

    public void setNamaTamu(String namaTamu) {
        this.namaTamu = namaTamu;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(String jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public String getJamKeluar() {
        return JamKeluar;
    }

    public void setJamKeluar(String jamKeluar) {
        JamKeluar = jamKeluar;
    }

    public String getFotoKeterangan() {
        return fotoKeterangan;
    }

    public void setFotoKeterangan(String fotoKeterangan) {
        this.fotoKeterangan = fotoKeterangan;
    }
}
