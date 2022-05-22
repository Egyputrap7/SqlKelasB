package com.example.sqlkelasb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Edit_teman extends AppCompatActivity {
    TextView idText;
    EditText Nama,Telepon;
    Button Save;
    String nma,tlp,id,namaEd,teleponEd;
    int sukses;

    private static String url_update = "http://10.0.2.2:80/PAM/editdata.php";
    private static final String TAG = Edit_teman.class.getSimpleName();
    private static  final String TAG_SUCCES = "succes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        idText = findViewById(R.id.textId);
        Nama = findViewById(R.id.editNm);
        Telepon = findViewById(R.id.editTlp);
        Save = findViewById(R.id.simpanBtn);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        nma = bundle.getString("nama");
        tlp = bundle.getString("telpon");

        idText.setText("Id :" + id);
        Nama.setText(nma);
        Telepon.setText(tlp);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditData();
            }
        });
    }


    public void EditData()
    {
        namaEd =Nama.getText().toString();
        teleponEd=Telepon.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringreq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respon: " + response.toString());
                try {
                    JSONObject jobj = new JSONObject(response);
                    sukses = jobj.getInt(TAG_SUCCES);
                    if (sukses == 1) {
                        Toast.makeText(Edit_teman.this, "Sukses mengedit data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Edit_teman.this, "gagal mengedit", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error :" + error.getMessage());
                Toast.makeText(Edit_teman.this,"Gagal Edit data",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("nama",namaEd);
                params.put("telpon",teleponEd);

                return params;
            }
        };
        requestQueue.add(stringreq);
        callHome();
    }
    public void callHome(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}