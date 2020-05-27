package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {


    ImageView user;
    TextView profname,profbusno,profbusype,proffrom,profto;
    Button start;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        user=findViewById(R.id.imageView3);
        profname=findViewById(R.id.name);
        profbusno=findViewById(R.id.busnum);
        profbusype=findViewById(R.id.bustyp);
        proffrom=findViewById(R.id.from);
        profto=findViewById(R.id.to);
        start=findViewById(R.id.start);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

       DatabaseReference databaseReference=firebaseDatabase.getReference("conductors").child(firebaseAuth.getCurrentUser().getUid());


       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               UserClass helperClass=dataSnapshot.getValue(UserClass.class);
               profname.setText("Name :"+helperClass.getName());
               profbusno.setText("Bus No :"+helperClass.getBusno());
               profbusype.setText("Bus Type :"+helperClass.getBustype());
               proffrom.setText("From :"+helperClass.getFrom());
               profto.setText("To :"+helperClass.getTo());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText(UserProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
           }
       });




    }
}
