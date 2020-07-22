package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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

import java.io.IOException;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private double lati,longi;
    ImageView user;
    TextView profname, profbusno, profbusype, proffrom, profto;
    Button start, finish, logout;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        user = findViewById(R.id.imageView3);
        profname = findViewById(R.id.name);
        profbusno = findViewById(R.id.busnum);
        profbusype = findViewById(R.id.bustyp);
        proffrom = findViewById(R.id.from);
        profto = findViewById(R.id.to);
        start = findViewById(R.id.strt);
        finish = findViewById(R.id.start);
        logout = findViewById(R.id.logout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("conductors").child(firebaseAuth.getCurrentUser().getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserClass helperClass = dataSnapshot.getValue(UserClass.class);
                profname.setText("Name :" + helperClass.getName());
                profbusno.setText("Bus No :" + helperClass.getBusno());
                profbusype.setText("Bus Type :" + helperClass.getBustype());
                proffrom.setText("From :" + helperClass.getFrom());
                profto.setText("To :" + helperClass.getTo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = 1;
                double longii, latii;
                latii = location.getLatitude();
                longii = location.getLongitude();
                String city = null;
                try {
                    Geocoder geocoder = new Geocoder(UserProfileActivity.this);
                    List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
                    city = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userid = user.getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("conductors").child(userid);
                ref.child("status").setValue(num);
                ref.child("latitude").setValue(latii);
                ref.child("longitude").setValue(longii);
                ref.child("place").setValue(city);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logot = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(logot);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                String n="default";
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userid = user.getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("conductors").child(userid);
                ref.child("status").setValue(num);
                ref.child("latitude").setValue(num);
                ref.child("longitude").setValue(num);
                ref.child("place").setValue(n);

            }
        });

    }
    public void onLocationChanged(Location location){
         lati=location.getLatitude();
         longi=location.getLongitude();
    }

}
