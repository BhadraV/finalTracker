package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    CheckBox a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, vv, w, x, y, z;
    Button submit;
    int num;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

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
        a = findViewById(R.id.ekm);
        b = findViewById(R.id.aluva);
        c = findViewById(R.id.angamaly);
        d = findViewById(R.id.chalakudy);
        e = findViewById(R.id.tsr);
        f = findViewById(R.id.kkm);
        g = findViewById(R.id.gvr);
        h = findViewById(R.id.tdpuzha);
        i = findViewById(R.id.mvtpuzha);
        j = findViewById(R.id.permbavoor);
        k = findViewById(R.id.mnkshipurm);
        l = findViewById(R.id.puthnagrm);
        m = findViewById(R.id.vdknchry);
        n = findViewById(R.id.pkd);
        o = findViewById(R.id.vyhub);
        p = findViewById(R.id.alpzha);
        q = findViewById(R.id.chngnasery);
        r = findViewById(R.id.chngannur);
        s = findViewById(R.id.althur);
        t = findViewById(R.id.cbr);
        u = findViewById(R.id.dam);
        vv = findViewById(R.id.kongd);
        w = findViewById(R.id.pathiripala);
        x = findViewById(R.id.ottapalm);
        y = findViewById(R.id.shrnur);
        z = findViewById(R.id.mnthy);

        final String s1 = "Ernamkulam";
        final String s2 = "Aluva";
        final String s3 = "Angamaly";
        final String s4 = "Chalakkudy";
        final String s5 = "Thrissur";
        final String s6 = "Kunnamkulam";
        final String s7 = "Guruvayoor";
        final String s8 = "Thodupuzha";
        final String s9 = "Muvattupuzha";
        final String s10 = "Perumbavoor";

        final String s11 = "Meenakshipuram";
        final String s12 = "Puthunagaram";
        final String s13 = "Vadakkanchery";
        final String s14 = "Palakkad";
        final String s15 = "VyttilaHub";
        final String s16 = "Alappuzha";
        final String s17 = "Changanassery";
        final String s18 = "Chengannur";
        final String s19 = "Alathur";
        final String s20 = "Coimbatore";
        final String s21 = "MalampuzhaDam";
        final String s22 = "Kongad";
        final String s23 = "Pathiripala";
        final String s24 = "Ottapalam";
        final String s25 = "Shornur";
        final String s26 = "Mannuthy";

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
                                UserHelperClass helperClass = new UserHelperClass(name, email, password, busNo, busType, Stime, From, To);
                                FirebaseDatabase.getInstance().getReference("conductors").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ConductorProfileActivity.this,"Registration successful!!!",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),ConductorLoginActivity.class));
                                    }
                                });
                            }
                        }
                    });

            }
        });
    }
}