package com.enuarmartinez.proyectofinal;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText Username, Password;
    private ListView LV;
    private String OpLg[];
    private Resources Resources;
    private Intent In;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LV = (ListView)findViewById(R.id.LvLg);
        Username = (EditText)findViewById(R.id.TxtUsername);
        Password = (EditText)findViewById(R.id.TxtPassword);
        Resources = this.getResources();
        OpLg =Resources.getStringArray(R.array.lg);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, OpLg);
        LV.setAdapter(Adapter);

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        In = new Intent(MainActivity.this,Remember.class);
                        startActivity(In);
                        break;
                    case 1:
                        In = new Intent(MainActivity.this, SingIn.class);
                        startActivity(In);
                        break;
                }
            }
        });


    }
}
