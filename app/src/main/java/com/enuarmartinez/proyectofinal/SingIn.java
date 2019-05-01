package com.enuarmartinez.proyectofinal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SingIn extends AppCompatActivity {

    private EditText Name, Lastname, Username, Password, Cellphone, Age;
    private Spinner Gender;
    private Resources Resources;
    private String OpGender[];
    private ArrayList<User> Users;
    private ArrayAdapter<String> Adapter;
    private Intent In;
    private Calendar myCalendar = Calendar.getInstance();
    private Button Save;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in);

        Name =(EditText)findViewById(R.id.TxtName);
        Lastname =(EditText)findViewById(R.id.TxtLastname);
        Username =(EditText)findViewById(R.id.TxtUsername);
        Password = (EditText)findViewById(R.id.TxtPassword);
        Cellphone= (EditText)findViewById(R.id.TxtPhone);
        Age = (EditText)findViewById(R.id.TxtBirthdate);
        Save = (Button)findViewById(R.id.btnSave);
        Gender = (Spinner)findViewById(R.id.Gender);
        Resources = this.getResources();
        OpGender = Resources.getStringArray(R.array.gender);
        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, OpGender);
        Gender.setAdapter(Adapter);
        Users = Data.Get();
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public void Save(View view){
        String NameV, LastnameV, UsernameV, PasswordV, GenderV;
        int BirthdateV;
        int CellphoneV;
        NameV = Name.getText().toString();
        LastnameV = Lastname.getText().toString();
        UsernameV = Username.getText().toString();
        PasswordV = Password.getText().toString();
        GenderV = Gender.getSelectedItem().toString();

        /*if (!PasswordV.equals(ConfirmV)){
            Toast.makeText(this, Resources.getString(R.string.warp), Toast.LENGTH_LONG).show();
        }*/

        if (Age.getText().toString().isEmpty()){
            BirthdateV = -1;
        } else {
            BirthdateV = Integer.parseInt(Age.getText().toString());
        }

        if (Cellphone.getText().toString().isEmpty()){
            CellphoneV = -1;
        } else {
            CellphoneV = Integer.parseInt(Cellphone.getText().toString());
        }


        if (NameV.isEmpty() || LastnameV.isEmpty() || UsernameV.isEmpty() ||
        PasswordV.isEmpty() || GenderV.isEmpty() || BirthdateV == -1
        || CellphoneV == -1 /*|| ConfirmV.isEmpty()*/){
            Toast.makeText(this, Resources.getString(R.string.warning), Toast.LENGTH_LONG).show();
            validarCajas(NameV, LastnameV, UsernameV, PasswordV, GenderV, BirthdateV, CellphoneV);
        }else {
            //User u = new User(NameV,LastnameV,UsernameV,PasswordV,GenderV,BirthdateV,CellphoneV);
            /*Map<String, Object> dataUser = new HashMap<>();
            dataUser.put("Name", NameV);
            dataUser.put("Lastame", LastnameV);
            dataUser.put("Username", UsernameV);
            dataUser.put("Password", PasswordV);
            dataUser.put("Gender", GenderV);
            dataUser.put("Birthdate", BirthdateV);
            dataUser.put("Cellphone", CellphoneV);*/

            User U = new User(NameV,LastnameV,UsernameV,PasswordV,GenderV,BirthdateV,CellphoneV);
            databaseReference.child("User").push().setValue(U);
            U.SaveUser();
            Toast.makeText(this, Resources.getString(R.string.successful), Toast.LENGTH_SHORT).show();
        }
    }


    private void validarCajas(String NameV, String LastnameV, String UsernameV, String PasswordV, String GenderV,
            Integer BirthdateV, Integer CellphoneV /*,String ConfirmV*/) {

        if (NameV.isEmpty()){
            Name.setError("Required");
        }
        if (LastnameV.isEmpty()){
            Lastname.setError("Required");
        }
        if (UsernameV.isEmpty()){
            Username.setError("Required");
        }
        if (PasswordV.isEmpty()){
            Password.setError("Required");
        }
        if (BirthdateV == -1){
            Age.setError("Required");
        }
        if (CellphoneV == -1){
            Cellphone.setError("Required");
        }
        /*if (ConfirmV.isEmpty()){
            Confirm.setError("Required");
        }*/
    }


}
