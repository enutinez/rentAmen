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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SingIn extends AppCompatActivity {

    private EditText Name, Lastname, Username, Password, Cellphone, Birthdate;
    private Spinner Gender;
    private Resources Resources;
    private String OpGender[];
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
        Birthdate = (EditText)findViewById(R.id.TxtBirthdate);
        Save = (Button)findViewById(R.id.btnSave);
        Birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SingIn.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Gender = (Spinner)findViewById(R.id.Gender);
        Resources = this.getResources();
        OpGender = Resources.getStringArray(R.array.gender);
        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, OpGender);
        Gender.setAdapter(Adapter);
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

    //metodo para mostrar el calendario
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }
    };
    private void actualizarInput() {
        String formatoDeFecha = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

        Birthdate.setText(sdf.format(myCalendar.getTime()));

    }


    public void SaveUser(View view) throws ParseException {
        String NameV, LastnameV, UsernameV, PasswordV, GenderV;
        Calendar BirthdateV = Calendar.getInstance();
        int CellphoneV;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        NameV = Name.getText().toString();
        LastnameV = Lastname.getText().toString();
        UsernameV = Username.getText().toString();
        PasswordV = Password.getText().toString();
        GenderV = Gender.getSelectedItem().toString();
        BirthdateV.setTime(sdf.parse(Birthdate.getText().toString()));
        if (Cellphone.getText().toString().isEmpty()){
            CellphoneV = -1;
        } else {
            CellphoneV = Integer.parseInt(Cellphone.getText().toString());
        }
        if (NameV.isEmpty() || LastnameV.isEmpty() || UsernameV.isEmpty() ||
        PasswordV.isEmpty() || GenderV.isEmpty() || BirthdateV.equals(" ")
        || CellphoneV == -1){
            Toast.makeText(this, Resources.getString(R.string.warning), Toast.LENGTH_LONG).show();

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
            if (BirthdateV.equals(" ")){
                Birthdate.setError("Required");
            }
            if (CellphoneV == -1){
                Cellphone.setError("Required");
            }

        }else {
            User u = new User(NameV,LastnameV,UsernameV,PasswordV,GenderV,BirthdateV,CellphoneV);
            Map<String, Object> dataUser = new HashMap<>();
            dataUser.put("Name", NameV);
            dataUser.put("Lastame", LastnameV);
            dataUser.put("Username", UsernameV);
            dataUser.put("Password", PasswordV);
            dataUser.put("Gender", GenderV);
            dataUser.put("Birthdate", BirthdateV);
            dataUser.put("Cellphone", CellphoneV);

            databaseReference.child("User").push().setValue(dataUser);

            Toast.makeText(this, Resources.getString(R.string.successful), Toast.LENGTH_SHORT).show();
        }
    }

}
