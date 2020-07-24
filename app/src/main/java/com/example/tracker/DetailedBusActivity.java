package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailedBusActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    String str=getIntent().getExtras().getString("busno");
    TextView crnt,s1,s2,s3,s4,s5,s6;
    Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bus);

        crnt=findViewById(R.id.current);
        s1=findViewById(R.id.st1);
        s2=findViewById(R.id.st2);
        s3=findViewById(R.id.st3);
        s4=findViewById(R.id.st4);
        s5=findViewById(R.id.st5);
        s6=findViewById(R.id.st6);
        map=findViewById(R.id.map);



        databaseReference= FirebaseDatabase.getInstance().getReference().child("conductors");
        Query query=databaseReference.orderByChild("busno").equalTo(str);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                   Help user=datasnapshot.getValue(Help.class);
                    s1.setText("stop 1: "+user.getS1());
                    s2.setText("stop 2: "+user.getS2());
                    s3.setText("stop 3: "+user.getS3());
                    s4.setText("stop 4: "+user.getS4());
                    s5.setText("stop 1: "+user.getS5());
                    s6.setText("stop 1: "+user.getS6());
                    crnt.setText("Current location: "+user.getPlace());



                
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}