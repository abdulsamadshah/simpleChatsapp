package com.example.simplechatsappsamad.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.simplechatsappsamad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText t2;
    Button b2;
    String phonenumber;
    String otpid;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
//        getSupportActionBar().setTitle("VerifyDataActivity");
        //this top app color change
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.gray));
        }

        getSupportActionBar().hide();

        phonenumber = getIntent().getStringExtra("mnumber");
        t2 = findViewById(R.id.t2);
        b2 = findViewById(R.id.b2);
        mAuth = FirebaseAuth.getInstance();

        initiateotp();
        if (mAuth.getCurrentUser() !=null){
            Intent intent=new Intent(getApplicationContext(),SetupProfileActivity.class);
            startActivity(intent);
        }

        t2.setHint("Enter Otp Code");
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setHint(null);
                t2.setText("Enter Otp Code");


            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (t2.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Blank Field can not be processed", Toast.LENGTH_LONG).show();
                else if (t2.getText().toString().length() != 6)
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }



    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        Intent intent=new Intent(getApplicationContext(),SetupProfileActivity.class);
                        startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Signin Code Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}