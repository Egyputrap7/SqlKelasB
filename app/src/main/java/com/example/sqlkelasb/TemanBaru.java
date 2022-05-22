package com.example.sqlkelasb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sqlkelasb.app.Appcontroller;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TemanBaru extends AppCompatActivity {



    private TextInputEditText tNama, tTelpon;
    private Button simpanBtn;
    String nm,tlp;
    int succes;


    private static String url_insert = "http://10.0.2.2:80/PAM/insert.php";
    private static final String TAG = TemanBaru.class.getSimpleName();
    private static final String TAG_SUCCES ="succes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_baru);

        tNama = (TextInputEditText) findViewById(R.id.tietNama);
        tTelpon = (TextInputEditText)  findViewById(R.id.tietTelepon);
        simpanBtn = (Button)  findViewById(R.id.buttonSave);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpanData();
                if(tNama.getText().toString().isEmpty() || tTelpon.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data Belum komplit !", Toast.LENGTH_SHORT).show();

                }
                else{
                    nm = tNama.getText().toString();
                    tlp = tTelpon.getText().toString();

                    HashMap<String,String> qvalues = new HashMap<>();
                    qvalues.put("nama", nm);
                    qvalues.put("telpon", tlp);

                    callHome();
                }
            }
        });
    }

    public void SimpanData(){
        if (tNama.getText().toString().equals("") || tTelpon.getText().toString().equals("")) {
            Toast.makeText(TemanBaru.this,"Semua Harus Ter isi!!",Toast.LENGTH_SHORT).show();
        }
        else {
            nm = tNama.getText().toString();
            tlp = tTelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response: " + response.toString());

                    try {
                        JSONObject Jobj = new JSONObject(response);
                        succes = Jobj.getInt(TAG_SUCCES);
                        if (succes == 1) {
                            Toast.makeText(TemanBaru.this, "Sukses SImpan Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TemanBaru.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"Error : "+ error.getMessage());
                    Toast.makeText(TemanBaru.this,"gagal Simpann Data",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();

                    params.put("nama",nm);
                    params.put("telpon",tlp);

                    return params;
                }
            };
            requestQueue.add(strReq);
        }
    }

    public  void  callHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}