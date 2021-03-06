package com.example.andres.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andres.firebaseauthdemo.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){

            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog=new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this,"porfavor entre su email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){

            Toast.makeText(this,"porfavor entre su clave",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registrando Usuario ....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference nodoFirebase = database.getReference("usuarios");

                            Usuario usuario= new Usuario();

                            usuario.setNombre("sin nombre");
                            usuario.setTelefono("no tiene");
                            usuario.setCedula("no tiene");
                            usuario.setUid(firebaseAuth.getCurrentUser().getUid());
                            usuario.setEmail(firebaseAuth.getCurrentUser().getEmail());

                            nodoFirebase.push().setValue(usuario);
                                finish();
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                            Toast.makeText(MainActivity.this, "registrado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();
                    }

                });
    }

    @Override
    public void onClick(View view) {
if (view == buttonRegister){

    registerUser();
}
        if (view==textViewSignin){

            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
