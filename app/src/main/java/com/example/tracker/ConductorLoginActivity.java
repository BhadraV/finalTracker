package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ConductorLoginActivity extends AppCompatActivity {
    EditText user,pass;
    Button signin,signup;
    FirebaseAuth firebaseauth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_login);
        setTitle("Conductor SignIn");

        firebaseauth=FirebaseAuth.getInstance();
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        signin=findViewById(R.id.consignin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr=user.getText().toString();
                String passwrd=pass.getText().toString();
                if(usr.isEmpty()){
                    user.setError("Please anter Username!!!");
                    user.requestFocus();
                }
                else if(passwrd.isEmpty()){
                    pass.setError("Please anter Password!!!");
                    pass.requestFocus();
                }
                else if(usr.isEmpty() && passwrd.isEmpty()){
                    Toast.makeText(ConductorLoginActivity.this,"Fields are empty!!!",Toast.LENGTH_LONG).show();
                }
                else if(!(usr.isEmpty() && passwrd.isEmpty()))
                {
                    firebaseauth.signInWithEmailAndPassword(usr,passwrd).addOnCompleteListener(ConductorLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(ConductorLoginActivity.this,"Login Error,Please Try Again!!!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Intent intlogin=new Intent(ConductorLoginActivity.this,testUser.class);
                                startActivity(intlogin);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(ConductorLoginActivity.this,"Error Occured!!!!!",Toast.LENGTH_LONG).show();
                }

            }
        });

        signup=findViewById(R.id.consignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insignup=new Intent(ConductorLoginActivity.this,ConductorProfileActivity.class);
                startActivity(insignup);
            }
        });
    }
}
