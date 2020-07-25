package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView crnt,s1,s2,s3,s4,s5,s6;
    Button map;
    Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bus);

        final String str=getIntent().getExtras().getString("busno","5");

        crnt=findViewById(R.id.current);
        s1=findViewById(R.id.st1);
        s2=findViewById(R.id.st2);
        s3=findViewById(R.id.st3);
        s4=findViewById(R.id.st4);
        s5=findViewById(R.id.st5);
        s6=findViewById(R.id.st6);
        map=findViewById(R.id.map);



        databaseReference= FirebaseDatabase.getInstance().getReference().child("conductors");
        final Query query=databaseReference.orderByChild("busno").equalTo(str);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    DetailsHelper user = snapshot.getValue(DetailsHelper.class);


                    Log.d("h", "hlo" + str);

                    s1.setText("Stop 1: " + user.getS1());
                    s2.setText("Stop 2: " + user.getS2());
                    s3.setText("Stop 3: " + user.getS3());
                    s4.setText("Stop 4: " + user.getS4());
                    s5.setText("Stop 5: " + user.getS5());
                    s6.setText("Stop 6: " + user.getS6());
                    crnt.setText("Current location: " + user.getPlace());
                    lat=user.getLatitude();
                    lon=user.getLongitude();

                }




            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailedBusActivity.this,"not accessible",Toast.LENGTH_LONG).show();
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailedBusActivity.this,MapActivity.class);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lon);
                startActivity(intent);
            }
        });




    }
}