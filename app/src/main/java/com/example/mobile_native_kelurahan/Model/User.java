package com.example.mobile_native_kelurahan.Model;

public class User {
    private String id;
    private String password;
    private String phoneNumber;
    private String role;
    private Masyarakat masyarakat;

    public User(String id, String password, String phoneNumber, String role, Masyarakat masyarakat) {
        this.id = id;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.masyarakat = masyarakat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Masyarakat getMasyarakat() {
        return masyarakat;
    }

    public void setMasyarakat(Masyarakat masyarakat) {
        this.masyarakat = masyarakat;
    }
}

