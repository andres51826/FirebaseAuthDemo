package com.example.andres.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;

    private Button buttonCerrarSesion;
    private EditText editTextName,editTextTelefono,editTextcedula;
    private Button buttonSave;
    private Button buttonCanchas;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName= (EditText) findViewById(R.id.EditTextName);
        editTextTelefono=(EditText)findViewById(R.id.EditTextTelefono);
        editTextcedula= ( EditText)findViewById(R.id.EditTextcedula);
        buttonSave =(Button)findViewById(R.id.buttonSave);
        buttonCanchas = (Button)findViewById(R.id.btncanchas);


        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()== null){

            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail= (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Bienvenido"+user.getEmail());


        buttonCerrarSesion=(Button) findViewById(R.id.buttonCerrarSesion);

        // agregar botones para el uso del Listener
        buttonCerrarSesion.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonCanchas.setOnClickListener(this);
    }


    private void saveUserInformation(){

        String name = editTextName.getText().toString().trim();
        String telefono =editTextTelefono.getText().toString().trim();
        String cedula= editTextcedula.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,telefono,cedula);

        FirebaseUser user= firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Guardando Informacion...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        if (view==buttonCerrarSesion){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        if (view==buttonSave){
            saveUserInformation();
        }

        if (view==buttonCanchas){
            startActivity(new Intent(this,MapsActivity.class));
        }
    }
}
