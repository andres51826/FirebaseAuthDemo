package com.example.andres.firebaseauthdemo;

/**
 * Created by ANDRES on 28/04/2017.
 */

public class UserInformation {
    public String name;
    public String telefono;
    public String cedula ;
    public UserInformation(){

    }

    public UserInformation(String name, String telefono,String cedula) {
        this.name = name;
        this.telefono = telefono;
        this.cedula=cedula;
    }
}
