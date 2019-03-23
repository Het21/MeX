package com.example.het.mex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
//    private static final String POST_URL = "http://ptsv2.com/t/dz7dh-1551415957/post";
private static final String POST_URL ="https://mex1242523-busy-ratel.eu-gb.mybluemix.net/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mainFun();
    }

    public void mainFun(){
        //        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Signup");
    }

    //    Register button click handler
    public void RegisterButtonClicked(View view) {
        final EditText Name = (EditText)findViewById(R.id.name);
        final EditText e_id = (EditText)findViewById(R.id.e_id);
        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.pwd);

        String name = Name.getText().toString();
        String E_id = e_id.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(name.length()!=0 && E_id.length()!=0 && Email.length()!=0 && Password.length()!=0){
            //        Toast.makeText(Signup.this,"Name:"+Name.getText().toString() + " empId:"+e_id.getText().toString() + " email:"+email.getText().toString()+" password:"+Password.getText().toString(),Toast.LENGTH_LONG).show();

            StringRequest postRequest = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Signup.this,"Registration successful",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Signup.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    //response
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Signup.this,"Error",Toast.LENGTH_SHORT).show();
                    //error
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("name",Name.getText().toString());
                    params.put("employee_id",e_id.getText().toString());
                    params.put("email",email.getText().toString());
                    params.put("password",password.getText().toString());

                    return params;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(postRequest);
        }
        else {
            Toast.makeText(Signup.this,"Please complete all fields",Toast.LENGTH_SHORT).show();
        }
    }
}
