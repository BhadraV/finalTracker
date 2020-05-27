package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConductorProfileActivity extends AppCompatActivity {


    EditText cname, user, pass, busno, bustype, stime, from, to;

    AutoCompleteTextView s1,s2,s3,s4,s5,s6;
    Button submit;
    int num;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String stops[]={"Ernamkulam","Aluva","Angamaly","Chalakudy","Thrissur","Kunnamkulam","Guruvayur","Thodupuzha","Muvattupuzha","Perumbavoor"
            ,"Meenakshipuram","Puthunagaram","Vadakkanchery","Palakad","VyttilaHub","Alappuzha","Changanassery","Chengannur","Alathur","Coimbatore","MalampuzhaDam"
            ,"Kongad","Pathiripala","Ottapalam","Shornur","Mannuthy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_profile);

        cname = findViewById(R.id.coname);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        busno = findViewById(R.id.busno);
        bustype = findViewById(R.id.bustype);
        stime = findViewById(R.id.stime);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

        s1=(AutoCompleteTextView) findViewById(R.id.s1);
       s2=(AutoCompleteTextView) findViewById(R.id.s2);
        s3=(AutoCompleteTextView) findViewById(R.id.s3);
        s4=(AutoCompleteTextView) findViewById(R.id.s4);
       s5=(AutoCompleteTextView) findViewById(R.id.s5);
       s6=(AutoCompleteTextView) findViewById(R.id.s6);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ConductorProfileActivity.this,android.R.layout.simple_list_item_1,stops);
        s1.setAdapter(adapter);
        s2.setAdapter(adapter);
        s3.setAdapter(adapter);
        s4.setAdapter(adapter);
        s5.setAdapter(adapter);
        s6.setAdapter(adapter);

        databaseReference=FirebaseDatabase.getInstance().getReference("conductors");
        mAuth=FirebaseAuth.getInstance();

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                final String name = cname.getText().toString();
                final String email = user.getText().toString();
                final String password = pass.getText().toString();
                final String busNo = busno.getText().toString();
                final String busType = bustype.getText().toString();
                final String Stime = stime.getText().toString();
                final String From = from.getText().toString();
                final String To = to.getText().toString();





                final String st1=s1.getText().toString();
                final String st2=s2.getText().toString();
                final String st3=s3.getText().toString();
                final String st4=s4.getText().toString();
                final String st5=s5.getText().toString();
               final String st6=s6.getText().toString();



                if (name.isEmpty()) {
                    cname.setError("name required");
                    cname.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    user.setError("email required");
                    user.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pass.setError("password  required");
                    pass.requestFocus();
                    return;
                }
                if (busNo.isEmpty()) {
                    busno.setError("Busno required");
                    cname.requestFocus();
                    return;
                }
                if (busType.isEmpty()) {
                    bustype.setError("bustype required");
                    bustype.requestFocus();
                    return;
                }
                if (Stime.isEmpty()) {
                    stime.setError("starting time required");
                    stime.requestFocus();
                    return;
                }
                if (From.isEmpty()) {
                    from.setError("from required");
                    from.requestFocus();
                    return;
                }
                if (To.isEmpty()) {
                    to.setError("to required");
                    to.requestFocus();
                    return;
                }

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ConductorProfileActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.e("sha","hello" );
                                double longi=0;
                                double lati=0;
                                int stats=0;
                                UserHelperClass helperClass = new UserHelperClass(name, email, password, busNo, busType, Stime, From, To,st1,st2,st3,st4,st5,st6,longi,lati,stats);
                                Log.e("mylog",mAuth.getCurrentUser().getUid());
                                String str=mAuth.getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("conductors").child(mAuth.getCurrentUser().getUid().toString()).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ConductorProfileActivity.this,"Registration successful!!!",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),ConductorLoginActivity.class));
                                    }
                                });
                            }else {
                                Toast.makeText(ConductorProfileActivity.this,"Not successful!!!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


            }
        });
    }


}