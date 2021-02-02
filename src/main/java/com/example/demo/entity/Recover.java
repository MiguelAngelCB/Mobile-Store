package com.example.demo.entity;

public class Recover {
    public String nif;
    public String email;

    public Recover(String nif,String email){
        this.nif=nif;
        this.email=email;
    }
    
    public String getNif() {
        return nif;
    }
    
    public String getEmail() {
        return email;
    }
}
