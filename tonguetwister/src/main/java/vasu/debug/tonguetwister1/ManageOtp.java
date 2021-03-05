package vasu.debug.tonguetwister1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ManageOtp extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";


    EditText otpCode;
    Button btnVerifyOtp;
    String phoneNumber;
    String mVerificationId;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    TextView mverificationIDTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        phoneNumber = getIntent().getStringExtra("mobile");
        otpCode = (EditText) findViewById(R.id.editText_otp);
        btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
        mverificationIDTextView = findViewById(R.id.tv_verificationId);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mverificationIDTextView.setText(verificationId+"\n*****\nforceresendToken:"+forceResendingToken);
                mVerificationId = verificationId;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                // [START_EXCLUDE silent]
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(ManageOtp.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        };


        initiateOtp(phoneNumber);

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpCode.getText().toString().isEmpty()){
                    Toast.makeText(ManageOtp.this,"invalid OTP",Toast.LENGTH_LONG).show();
                }else if(otpCode.getText().toString().length() != 6) {
                    Toast.makeText(ManageOtp.this,"OTP is exactly 6 digits long.",Toast.LENGTH_LONG).show();
                }else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otpCode.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });



    }

    private void initiateOtp(String phoneNumber) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ManageOtp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(ManageOtp.this,"signed-in correctly",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ManageOtp.this, UserDashboard.class));
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            String msg = task.getException().getMessage();
                            mverificationIDTextView.setText(msg);
                            Toast.makeText(ManageOtp.this,"Sign-in failed.",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}