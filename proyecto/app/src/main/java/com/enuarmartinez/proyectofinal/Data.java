package com.enuarmartinez.proyectofinal;

import java.util.ArrayList;

public class Data {

    private static ArrayList<User> Users = new ArrayList<>();
    public static void Save (User u){ Users.add(u);}
    public static ArrayList<User> Get(){return Users;}
}
