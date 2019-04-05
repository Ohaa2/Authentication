package com.example.pedaars.lister;

 import android.content.Intent;
 import android.support.annotation.NonNull;
 import android.support.annotation.Nullable;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.bumptech.glide.Glide;
 import com.google.android.gms.auth.api.Auth;
 import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
 import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
 import com.google.android.gms.auth.api.signin.GoogleSignInResult;
 import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.SignInButton;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.common.api.ResultCallback;
 import com.google.android.gms.common.api.Status;

 //
 import com.google.android.gms.tasks.Task;
 import com.google.firebase.auth.FirebaseAuth;
 //
 import com.google.firebase.auth.FirebaseUser;
//
 import com.firebase.ui.auth.AuthUI;
//
 import com.google.android.gms.tasks.OnCompleteListener;

 import java.util.Arrays;
 import java.util.List;

public class Login extends AppCompatActivity {//implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
/**
    private LinearLayout Prof_section;
    private Button SignOut;
    private SignInButton SignIn;
    private Button Home;
    private TextView Name, Email;
    private ImageView Prof_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
**/
    //
    private FirebaseAuth mFirebaseAuth;
    //
    public static final int RC_SIGN_IN = 1;
    //
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
         //   new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fui_activity_register_email);
        //setContentView(R.layout.activity_login__demo);
        //
        mFirebaseAuth = FirebaseAuth.getInstance();

        //
        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(Login.this, "User Signed In", Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN
                    );

                }
            }
        };
        /**
        Prof_section = (LinearLayout) findViewById(R.id.prof_section);
        SignOut = (Button) findViewById(R.id.bn_logout);
        SignIn = (SignInButton) findViewById(R.id.bn_login);
        Home = (Button) findViewById(R.id.bn_home);
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        Prof_pic = (ImageView) findViewById(R.id.prof_pic);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Home.setOnClickListener(this);
        Home.setVisibility(View.GONE);
        Prof_section.setVisibility(View.GONE);
        **/
        /**
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_clientid))
                .requestProfile()
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
**/
 }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListner);

    }
    //
    public void signout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Login.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    /**
    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }


 @Override
 public void onClick(View v) {
 switch (v.getId()) {
 case R.id.bn_login:
 signIn();
 break;
 case R.id.bn_logout:
 signOut();
 break;
 case R.id.bn_home:
 homePage();
 break;
 }

 }
 **/
/**
 @Override
 public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

 }
**/
/**
 private void signIn() {
 Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
 startActivityForResult(intent, REQ_CODE);

 }


 private void homePage() {
 Intent intent = new Intent(Login.this, Home.class);
 startActivity(intent);
 }



 private void handleResult(GoogleSignInResult result) {
 if(result.isSuccess()) {

 GoogleSignInAccount account = result.getSignInAccount();
 String idToken = account.getIdToken();
 String name = account.getDisplayName();
 String email = account.getEmail();
 String img_url = account.getPhotoUrl().toString();
 Name.setText(name);
 Email.setText(email);
 Glide.with(this).load(img_url).into(Prof_pic);
 updateUI(true);
 }
 else{
 updateUI(false);
 }

 }


 private void updateUI(boolean isLogin) {
 if(isLogin) {
 Prof_section.setVisibility(View.VISIBLE);
 Home.setVisibility(View.VISIBLE);
 SignIn.setVisibility(View.GONE);
 }
 else{
 Prof_section.setVisibility(View.GONE);
 Home.setVisibility(View.GONE);
 SignIn.setVisibility(View.VISIBLE);
 }

 }


@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE) {

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleResult(result);

 }

 }
**/
}

















/**
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout Prof_section;
    private Button SignOut;
    private SignInButton SignIn;
    private Button Home;
    private TextView Name, Email;
    private ImageView Prof_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login__demo);
        Prof_section = (LinearLayout)findViewById(R.id.prof_section);
        SignOut = (Button)findViewById(R.id.bn_logout);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        Home = (Button)findViewById(R.id.bn_home);
        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        Prof_pic = (ImageView)findViewById(R.id.prof_pic);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Home.setOnClickListener(this);
        Home.setVisibility(View.GONE);
        Prof_section.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_clientid))
                .requestProfile()
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_login:
                signIn();
                break;
            case R.id.bn_logout:
                signOut();
                break;
            case R.id.bn_home:
                homePage();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void homePage() {
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }

    private void handleResult(GoogleSignInResult result) {
        if(result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            String idToken = account.getIdToken();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(Prof_pic);
            updateUI(true);
        }
        else{
            updateUI(false);
        }

    }

    private void updateUI(boolean isLogin) {
        if(isLogin) {
            Prof_section.setVisibility(View.VISIBLE);
            Home.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        }
        else{
            Prof_section.setVisibility(View.GONE);
            Home.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }

    }
}
**/