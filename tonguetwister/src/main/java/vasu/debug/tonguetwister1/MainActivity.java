package vasu.debug.tonguetwister1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;


public class MainActivity extends AppCompatActivity {

    Button userDash,sellerDash;
//    ------------google sign in--------
private static final int RC_SIGN_IN = 9001;
    private SignInButton siBtn;
    private FirebaseAuth mAuth;//--------------------------------FirebaseAuth var-----------------<------------------<<-----
    private GoogleSignInClient mGoogleSignInClient;
//    ------------google sign in---xxx-----
//   --------- mobile signin-----------------
    CountryCodePicker ccp;
    EditText userMobileNumber;
    Button btnSignIn,btn2;
//   -------- mobile signin---xxx--------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDash = findViewById(R.id.btn_userdashboard);
        userDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserDashboard.class));
            }
        });

        sellerDash = findViewById(R.id.btn_sellerdashboard);
        sellerDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SellerDashboard.class));
            }
        });

// ---------------------------google sign in---------------------
        FirebaseApp.initializeApp(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        processRequest();
        siBtn = findViewById(R.id.signInButton);
        siBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });
// ---------------------------google sign in xxx---------------------
//------------------------------mobile sign in------------------------
        userMobileNumber = (EditText) findViewById(R.id.user_mobile_number);
        mAuth = FirebaseAuth.getInstance();

        ccp = (CountryCodePicker) findViewById(R.id.country_code_picker);
        ccp.registerCarrierNumberEditText(userMobileNumber);

        btnSignIn = (Button) findViewById(R.id.btn_signInByMobile);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManageOtp.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ","") );
                startActivity(intent);
            }
        });

//------------------------------mobile sign in-----xxx----------------

    }// onCreate XXX

//    ---------------Google sign in------------------------------------

    private void processRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.gc_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void processLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    //    ---------------Google sign in--xxx----------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//    ---------------Google sign in------------------------------------
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                String msg = e.getMessage() + ":Google Sign In failed";
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

            }
        }
        //    ---------------Google sign in--xxx----------------------------------
    }

    @Override
    public void onStart() {
        super.onStart();


//    ---------------Google sign in------------------------------------
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!= null){
            Intent intent = new Intent(getApplicationContext(),UserDashboard.class);
            startActivity(intent);
        }

 //    ---------------Google sign in--xxx----------------------------------
    }

    //    ---------------Google sign in------------------------------------
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(getApplicationContext(),UserDashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"problem in firebase login",Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
    //    ---------------Google sign in--xxx----------------------------------

}