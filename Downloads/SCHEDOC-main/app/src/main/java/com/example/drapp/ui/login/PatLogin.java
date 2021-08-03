package com.example.drapp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drapp.R;
import com.example.drapp.databinding.ActivityPatLoginBinding;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class PatLogin extends AppCompatActivity {
    private static final int RC_SIGN_IN =9001 ;
    private static final String TAG ="Login" ;
//    private CallbackManager callbackManager;
//    AccessTokenTracker accessTokenTracker;
//    ProfileTracker profileTracker;
    private FirebaseAuth mAuth;
    Button emailsingnin;
    Button signup;
//    Button fbsignin;
    EditText emaile;
    EditText passworde;
//    ProgressBar progressBar;
    String name;
    String email;
    Button SpecLogin;
//    Uri photoUrl;
//    Spinner spinner;
    private GoogleSignInClient mGoogleSignInClient;

    private String AuthId = "347359716425-j6jue60v6njovaisjsqfmrebr9933qkh.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_login);
        mAuth = FirebaseAuth.getInstance();
//        fbsignin=findViewById(R.id.login_button);
        emailsingnin=findViewById(R.id.loginBtn);
        emaile=findViewById(R.id.emaile);
        passworde=findViewById(R.id.passworde);
//        progressBar=findViewById(R.id.progressBar);


        emailsingnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emaile.getText().toString();
                String password=passworde.getText().toString();

                if(email.equals("")){
                    emaile.setError("Please enter your email");
                    return;
                }
                if(password.equals("")){
                    passworde.setError("Please enter your password");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(PatLogin.this, new OnCompleteListener<AuthResult>() {
                            private static final String TAG ="Email Sign In" ;

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(PatLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                    updateUI((FirebaseUser) null);
                                }

                                // ...
                            }
                        });

            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(AuthId).requestEmail()
                .build();

        ////
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_button);
        mGoogleSignInClient = GoogleSignIn.getClient(PatLogin.this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });




        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatLogin.this,PatientRegisterActivity.class);
                startActivity(intent);

            }
        });

        SpecLogin=findViewById(R.id.SpecLog);
        SpecLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatLogin.this,DoctorLogin.class);
                startActivity(intent);

            }
        });


//        fbsignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callbackManager = CallbackManager.Factory.create();
//                AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//
//
//
//                LoginManager.getInstance().logInWithReadPermissions(LoginMain.this, Arrays.asList("public_profile"));
//
//                callbackManager = CallbackManager.Factory.create();
//
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                Profile profile =Profile.getCurrentProfile();
//                                //nextActivity(profile);
//                                Toast.makeText(getApplicationContext(),"Loggin",Toast.LENGTH_SHORT).show();
//                                name=profile.getName();
//                                email=profile.getId();
//                                photoUrl=profile.getProfilePictureUri(200,400);
//                                accessTokenTracker.startTracking();
//                                profileTracker.startTracking();
//                                begin();
//
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                // App code
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                // App code
//                                exception.printStackTrace();
//                            }
//                        });
//
//                accessTokenTracker = new AccessTokenTracker() {
//                    @Override
//                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//
//                    }
//                };
//                profileTracker =new ProfileTracker() {
//                    @Override
//                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//
//                    }
//                };
//
//            }
//        });



    }

/*    protected void nextActivity(Profile profile)
    {
        if(profile!=null)
        {
        }
    }*/

    // @Override
    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    private void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            name = user.getDisplayName();
            email = user.getEmail();
//            photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            begin();

        }
        else
        {
            //Toast.makeText("LoginMain.this","User Not found").show();
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void updateUI(GoogleSignInAccount account) {
        if(account!=null)
        {
            account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            name=account.getDisplayName();
            email=account.getEmail();
//            photoUrl=account.getPhotoUrl();
            begin();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


        }
        else
        {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d(TAG, "stringMsg:success");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI((GoogleSignInAccount) null);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }


    private void begin() {
//        Intent intent=new Intent(PatLogin.this,PatDash.class);
//        //intent.putExtra(name,"name");
//        //intent.putExtra(email,"email");
//        ///intent.putExtra(String.valueOf(photoUrl),"photoUrl");
//        Bundle bundle=new Bundle();
//        bundle.putString("name",name);
//        bundle.putString("email",email);

        // bundle.putString("photoUrl",photoUrl.toString());
//        intent.putExtras(bundle);
//        Toast.makeText(PatLogin.this, ""+email, Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
        Query checkUser = reference.orderByChild("email").equalTo(email);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Intent intent=new Intent(PatLogin.this,PatDash.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("name",name);
//                    bundle.putString("email",email);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(PatLogin.this, "Account doesn't exist, please sign up first.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), GInfo.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
//        startActivity(intent);
    }
}