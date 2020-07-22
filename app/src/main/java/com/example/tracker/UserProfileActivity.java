package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {


    ImageView user;
    TextView profname,profbusno,profbusype,proffrom,profto;
    Button start,finish,logout;
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
        start=findViewById(R.id.strt);
        finish=findViewById(R.id.start);
        logout=findViewById(R.id.logout);

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



start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int num=1;
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("conductors").child(userid);
        ref.child("status").setValue(num);
    }
});
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent logot=new Intent(UserProfileActivity.this,MainActivity.class);
        startActivity(logot);
    }
});
finish.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int num=0;
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("conductors").child(userid);
        ref.child("status").setValue(num);
        
    }
});
    }
}
