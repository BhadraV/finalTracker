package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BusDetailsActivity extends AppCompatActivity {



    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    FirebaseListAdapter adapter;
    String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

String passedarg=getIntent().getExtras().getString("arg");

        listView = (ListView) findViewById(R.id.listView);
        Query query=FirebaseDatabase.getInstance().getReference().child("conductors").orderByChild("from_to").equalTo(passedarg);
        FirebaseListOptions<User> options=new FirebaseListOptions.Builder<User>().setLayout(R.layout.user_info).setQuery(query,User.class).build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView stime=v.findViewById(R.id.starting_time);
                TextView typ=v.findViewById(R.id.bustypee);
                TextView cuplace=v.findViewById(R.id.cplace);
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                User user=(User)model;
                stime.setText("Starting time: "+user.getStime().toString());
                typ.setText("Type of Bus"+user.getBustype().toString());
                cuplace.setText("Current Location: "+user.getPlace().toString());
                no=user.getBusno().toString();



            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                Intent intent=new Intent(BusDetailsActivity.this,DetailedBusActivity.class);
                intent.putExtra("busno",no);

                startActivity(intent);
                Toast.makeText(BusDetailsActivity.this, "myPos "+i, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
