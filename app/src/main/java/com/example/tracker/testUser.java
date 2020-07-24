package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class testUser extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FusedLocationProviderClient fusedLocationProviderClient;

    ImageView user;
    TextView profname, profbusno, profbusype, proffrom, profto;
    Button start, finish, logout;

    LocationManager locationManager;
    boolean isGpsLocation;
    boolean isNetworkloc;
    ProgressDialog progressDialog;
    Location loc;
    double la, lo;
    DatabaseReference databaseReference;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_user);


        progressDialog = new ProgressDialog(testUser.this);
        progressDialog.setMessage("uploading");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(testUser.this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("conductors").child(firebaseAuth.getCurrentUser().getUid());
        //DatabaseReference databaseReference = firebaseDatabase.getReference("conductors").child("Yq7zveh9MkdV21976IfLexkpZid2");
        user = (ImageView) findViewById(R.id.imageView3);
        profname = findViewById(R.id.st1);
        profbusno = findViewById(R.id.busnum);
        profbusype = findViewById(R.id.bustyp);
        proffrom = findViewById(R.id.from);
        profto = findViewById(R.id.to);
        start = findViewById(R.id.strt);
        finish = findViewById(R.id.start);
        logout = (Button) findViewById(R.id.logout);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserClass helperClass = dataSnapshot.getValue(UserClass.class);
                profname.setText("Name :" + helperClass.getName());
                profbusno.setText("Bus No :" + helperClass.getBusno());
                profbusype.setText("Bus Type :" + helperClass.getBustype());
                proffrom.setText("From :" + helperClass.getFrom());
                profto.setText("To :" + helperClass.getTo());

                Log.d("Value", helperClass.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Logout", "it is working");
                Intent logot = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logot);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fin", "  wrking");
                int num = 0;
                String n = "NotStarted";
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userid = user.getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("conductors").child(userid);
                ref.child("status").setValue(num);
                ref.child("latitude").setValue(num);
                ref.child("longitude").setValue(num);
                ref.child("place").setValue(n);

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Log.d("str", "wrkng");

                String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
                if (EasyPermissions.hasPermissions(testUser.this,perms)){
                    Toast.makeText(testUser.this,"Accessing Location",Toast.LENGTH_SHORT).show();

                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(testUser.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                la=location.getLatitude();
                                lo=location.getLongitude();
                                Log.d("WASABI_WORKING1", "onClick: " + la + lo);
                                String city ;


                                try {

                                    Geocoder geocoder = new Geocoder(testUser.this);
                                    List<Address> addresses = null;
                                    addresses = geocoder.getFromLocation(la, lo, 1);
                                    city = addresses.get(0).getLocality();
                                    Toast.makeText(testUser.this,city,Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String userid = user.getUid();
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("conductors").child(userid);
                                    ref.child("status").setValue(1);
                                    ref.child("latitude").setValue(la);
                                    ref.child("longitude").setValue(lo);
                                    ref.child("place").setValue(city);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                }






                            }
                        }
                    });


                }else {
                    EasyPermissions.requestPermissions(testUser.this,"Location permission is required",123,
                            perms);
                }



//                if (ActivityCompat.checkSelfPermission(testUser.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(testUser.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }

                Log.d("WASABI_WORKING2", "onClick: " + la + lo);



//
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }
    }
}
