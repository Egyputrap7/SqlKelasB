package com.example.sqlkelasb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sqlkelasb.app.Appcontroller;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;


public class Edit_teman extends AppCompatActivity {
    TextInputEditText Nama,Telepon;
    Button Save;
    String nma,tlp,id;
    Appcontroller controller = new Appcontroller(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        Nama = findViewById(R.id.edNama);
        Telepon = findViewById(R.id.edTelp);
        Save = findViewById(R.id.simpanBtn);

        id = getIntent().getStringExtra("id");
        nma = getIntent().getStringExtra("nama");
        tlp = getIntent().getStringExtra("telpon");

        setTitle("Edit Data");
        Nama.setText(nma);
        Telepon.setText(tlp);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Nama.getText().toString().equals("") || Telepon.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Mohon isi data terlebih dahulu!! !", Toast.LENGTH_SHORT).show();
                }
                else{
                    nma = Nama.getText().toString();
                    tlp = Telepon.getText().toString();
                    HashMap<String,String> Values = new HashMap<>();
                    Values.put("id",id);
                    Values.put("nama", nma);
                    Values.put("telpon", tlp);

                    controller.UpdateData(Values);
                    callHome();
                }
            }
        });
    }
    public void callHome(){
        Intent i = new Intent(Edit_teman.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}