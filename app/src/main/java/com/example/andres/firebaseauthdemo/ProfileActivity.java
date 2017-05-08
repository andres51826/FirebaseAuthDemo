package com.example.andres.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andres.firebaseauthdemo.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;

    private Button buttonCerrarSesion;
    private EditText editTextName, editTextTelefono, editTextcedula;
    private Button buttonSave;
    private Button buttonCanchas;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {

            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        databaseReference = database.getReference("usuarios");
        editTextName = (EditText) findViewById(R.id.EditTextName);
        editTextTelefono = (EditText) findViewById(R.id.EditTextTelefono);
        editTextcedula = (EditText) findViewById(R.id.EditTextcedula);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonCanchas = (Button) findViewById(R.id.btncanchas);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Bienvenido" + user.getEmail());


        buttonCerrarSesion = (Button) findViewById(R.id.buttonCerrarSesion);

        // agregar botones para el uso del Listener
        buttonCerrarSesion.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonCanchas.setOnClickListener(this);
    }


    private void saveUserInformation() {


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot registro : dataSnapshot.getChildren()) {


                    if (registro.child("email").getValue(String.class).equals(firebaseAuth.getCurrentUser().getEmail())) {


                        Usuario usuario = new Usuario();

                        usuario = registro.getValue(Usuario.class);

                        System.out.println(usuario.getUid());
                        usuario.setNombre(editTextName.getText().toString().trim());
                        usuario.setCedula(editTextcedula.getText().toString().trim());
                        usuario.setTelefono(editTextTelefono.getText().toString().trim());
                        System.out.println(registro.getRef().toString());

                        DatabaseReference actualizarReferencia = registro.getRef();
                        actualizarReferencia.setValue(usuario);

                        break;


                    } else {
                        System.out.println("no hay email");
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //  databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Guardando Informacion...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        if (view == buttonCerrarSesion) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == buttonSave) {
            saveUserInformation();
        }

        if (view == buttonCanchas) {
            startActivity(new Intent(this, MapsActivity.class));
        }
    }
}
