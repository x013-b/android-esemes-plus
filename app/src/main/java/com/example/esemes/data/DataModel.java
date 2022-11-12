package com.example.esemes.data;

import android.widget.Button;

public class DataModel {
    String nazwa;
    String numer;

    public DataModel(String nazwa, String numer) {
        this.nazwa = nazwa;
        this.numer = numer;
    }


    public String getNazwa() {
        return nazwa;
    }

    public String getNumer() {
        return numer;
    }

}

