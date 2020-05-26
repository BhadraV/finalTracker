package com.example.tracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ConductorLoginActivity extends AppCompatActivity {

    EditText user,pass;
    Button consignin,consignup;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_login);


        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        consignin=findViewById(R.id.consignin);
        consignup=findViewById(R.id.consignup);

        consignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intprofile=new Intent(ConductorLoginActivity.this,ConductorProfileActivity.class);
                startActivity(intprofile);
            }
        });
        consignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=user.getText().toString();
                final String password = pass.getText().toString();
                if(username.isEmpty()){
                    user.setError("Cannot be empty!!");
                }
                else if (password.isEmpty()) {
                    pass.setError("Cannot be empty!!");
                }
                else if(!(username.isEmpty())&&!(password.isEmpty()))
                {

                    reference=FirebaseDatabase.getInstance().getReference("conductors");
                    Query checkUser=reference.orderByChild("user").equalTo(username);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                user.setError(null);

                                String passfromDB=dataSnapshot.child(password).child("pass").getValue(String.class);
                                if(passfromDB.equals(password)){
                                    String namefromdb=dataSnapshot.child(username).child("name").getValue(String.class);
                                    String userfromdb=dataSnapshot.child(username).child("user").getValue(String.class);
                                    String busnofromdb=dataSnapshot.child(username).child("busno").getValue(String.class);
                                    String bustypefromdb=dataSnapshot.child(username).child("bustype").getValue(String.class);
                                    String stimefromdb=dataSnapshot.child(username).child("stime").getValue(String.class);
                                    String fromfromdb=dataSnapshot.child(username).child("from").getValue(String.class);
                                    String tofromdb=dataSnapshot.child(username).child("to").getValue(String.class);

                                    Intent intent=new Intent(ConductorLoginActivity.this,UserProfileActivity.class);

                                    intent.putExtra("name",namefromdb);
                                    intent.putExtra("Username",userfromdb);
                                    intent.putExtra("Busno",busnofromdb);
                                    intent.putExtra("BusType",bustypefromdb);
                                    intent.putExtra("StartinTime",stimefromdb);
                                    intent.putExtra("From",fromfromdb);
                                    intent.putExtra("To",tofromdb);

                                    startActivity(intent);

                                }else{
                                    pass.setError("wrong password!!");
                                    pass.requestFocus();
                                }
                            }else {
                                user.setError("No such user exists");
                                user.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else {
                    user.setError("wrong");
                }



            }
        });
    }
}
