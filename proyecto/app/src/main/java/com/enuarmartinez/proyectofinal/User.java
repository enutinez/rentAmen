package com.enuarmartinez.proyectofinal;

import java.util.Calendar;

public class User {

 private String Name, Lasttname,Username, Password, Sex;
 private int Cellphone, Age;

    public User(String name, String lasttname, String username, String password, String sex, int age, int cellphone) {

        Name = name;
        Lasttname = lasttname;
        Username = username;
        Password = password;
        Sex = sex;
        Cellphone = cellphone;
        Age = age;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLasttname() {
        return Lasttname;
    }

    public void setLasttname(String lasttname) {
        Lasttname = lasttname;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public int getCellphone() {
        return Cellphone;
    }

    public void setCellphone(int cellphone) {
        Cellphone = cellphone;
    }

    public void SaveUser (){ Data.Save(this);}

    public String toString(){
        return Name+" "+Lasttname;
    }


}
