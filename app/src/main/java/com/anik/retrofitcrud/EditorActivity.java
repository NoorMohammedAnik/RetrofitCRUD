package com.anik.retrofitcrud;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anik.retrofitcrud.model.Contacts;
import com.anik.retrofitcrud.remote.ApiClient;
import com.anik.retrofitcrud.remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    private ApiInterface apiInterface;

    private EditText etxtName, etxtEmail;
    private Button btnSave,btnDelete;

    String getID;
    String getName;
    String getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent i = getIntent();
        getID = i.getStringExtra("id");
        getName = i.getStringExtra("name");
        getEmail = i.getStringExtra("email");

        etxtName = findViewById(R.id.input_name);
        etxtEmail = findViewById(R.id.input_email);
        btnSave = findViewById(R.id.bSimpan);
        btnDelete=findViewById(R.id.btn_delete);



        condition(getID);



    }

    private void insertUser() {

        String sname = etxtName.getText().toString();
        String semail = etxtEmail.getText().toString();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.insertUser(sname, semail);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")){
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(EditorActivity.this, "Error! "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editUser() {

        String sid = getID;
        String sname = etxtName.getText().toString();
        String semail = etxtEmail.getText().toString();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.editUser(sid, sname, semail);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")){
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(EditorActivity.this, "Error! "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void condition(String kond){

        if (kond == null){

            btnDelete.setVisibility(View.INVISIBLE);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertUser();
                }
            });

        } else {

            etxtName.setText(getName);
            etxtEmail.setText(getEmail);

            Log.d("ID",getID);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUser();
                }
            });


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUser();
                }
            });
        }
    }




    private void deleteUser() {

        int id = Integer.parseInt(getID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.deleteUser(id);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")){
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(EditorActivity.this, "Error! "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}