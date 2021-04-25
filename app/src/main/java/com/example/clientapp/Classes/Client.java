package com.example.clientapp.Classes;

public class Client {
    public String name,telephone,email,addresse,cin;

    public Client(String name, String telephone, String email, String addresse, String cin) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.addresse = addresse;
        this.cin = cin;
    }
    public Client(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    @Override
    public String toString() {
        return cin+"\n"+name+"\n"+telephone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}