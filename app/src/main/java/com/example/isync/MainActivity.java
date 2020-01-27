package com.example.isync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Constraints;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isync.Model.FBUser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
 private FirebaseDatabase database;
 private DatabaseReference databaseReference;
 private FirebaseAuth mAuth;
 private FirebaseAuth.AuthStateListener mAuthListener;
 private static final String TAG = "MainActivity";
 private AppCompatEditText email;
 private AppCompatEditText password;
 private AppCompatButton btnlogin;
 private AppCompatTextView createProfile;
 private AppCompatTextView forgotPass;
 TextInputLayout emailTextInputLayout;
 TextInputLayout passTextInputLayout;
 FirebaseUser currentUser;
 private static final String EMAIL = "email";
 private static final String NAME = "name";
 CallbackManager callbackManager;
 
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	mAuth = FirebaseAuth.getInstance();
	database = FirebaseDatabase.getInstance();
	databaseReference = database.getReference();
	//facebook login
	callbackManager = CallbackManager.Factory.create();
	LoginButton FBloginButton = (LoginButton) findViewById(R.id.login_button);
	FBloginButton.setPermissions(Arrays.asList(EMAIL, NAME));
	
	email = (AppCompatEditText) findViewById(R.id.emailEd);
	password = (AppCompatEditText) findViewById(R.id.passwordEd);
	btnlogin = (AppCompatButton) findViewById(R.id.loginBtn);
	createProfile = (AppCompatTextView) findViewById(R.id.createTv);
	forgotPass = (AppCompatTextView) findViewById(R.id.ForgotPassTv);
	
	emailTextInputLayout = (TextInputLayout) findViewById(R.id.username_textInputLayout);
	email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (email.getText().toString().isEmpty()){
				emailTextInputLayout.setErrorEnabled(true);
				emailTextInputLayout.setError("email is required*");
			} else {
				emailTextInputLayout.setErrorEnabled(false);
			}
		}
	});
	email.addTextChangedListener(new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (email.getText().toString().isEmpty()){
				emailTextInputLayout.setErrorEnabled(true);
				emailTextInputLayout.setError("email is required*");
			} else {
				emailTextInputLayout.setErrorEnabled(false);
			}
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		
		}
	});
	passTextInputLayout = (TextInputLayout) findViewById(R.id.password_textInputLayout);
	password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(password.getText().toString().isEmpty()){
				passTextInputLayout.setErrorEnabled(true);
				passTextInputLayout.setError("password is required*");
			}else{
				passTextInputLayout.setErrorEnabled(false);
			}
		}
	});
	password.addTextChangedListener(new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if(password.getText().toString().isEmpty()){
				passTextInputLayout.setErrorEnabled(true);
				passTextInputLayout.setError("password is required*");
			}else{
				passTextInputLayout.setErrorEnabled(false);
			}
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		
		}
	});
	passTextInputLayout.setCounterEnabled(true);
	passTextInputLayout.setCounterMaxLength(8);
	FBloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
		@Override
		public void onSuccess(LoginResult loginResult) {
			// App code
			final AccessToken accessToken = loginResult.getAccessToken();
			final FBUser fbUser = new FBUser();
			GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
				@Override
				public void onCompleted(JSONObject user, GraphResponse graphResponse) {
					fbUser.setEmail(user.optString("email"));
					fbUser.setName(user.optString("name"));
//					fbUser.setId(user.optString("id"));
				}
			}).executeAsync();
			Toast.makeText(MainActivity.this, "Facebook working", Toast.LENGTH_LONG).show();
		}
		@Override
		public void onCancel() {
			// App code
			Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
		}
		@Override
		public void onError(FacebookException exception) {
			// App code
			Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_LONG).show();
		}
	});
	
	databaseReference.addValueEventListener(new ValueEventListener() {
		@Override
		public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
		
		
		}
		
		@Override
		public void onCancelled(@NonNull DatabaseError databaseError) {
		
		}
	});
	mAuthListener = new FirebaseAuth.AuthStateListener() {
		@Override
		public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
			FirebaseUser user = mAuth.getCurrentUser();
			if (user != null) {
				

			}else {
				Toast.makeText(MainActivity.this, "Not Signed In", Toast.LENGTH_LONG).show();
			}
		}
	};
	btnlogin.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!TextUtils.isEmpty(email.getText().toString())
						&& !TextUtils.isEmpty(password.getText().toString())) {
				
				String vemail = email.getText().toString();
				String pwd = password.getText().toString();
				
				login(vemail, pwd);
			}else {
				Toast.makeText(MainActivity.this, "Email and password cannot be blank ", Toast.LENGTH_SHORT).show();
//				Log.d(TAG, "onClick: "+ );
			}
		}
		
		
	});
	createProfile.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		startActivity(new Intent(MainActivity.this, CreateProfile.class));
		}
	});
	forgotPass.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(MainActivity.this, "WTF were you thinking .... ", Toast.LENGTH_SHORT).show();
		}
	});

}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	callbackManager.onActivityResult(requestCode, resultCode, data);
}

private void login(String vemail, String pwd) {
	
	mAuth.signInWithEmailAndPassword(vemail, pwd)
				.addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						// Sign in success, update UI with the signed-in user's information
						Log.d(TAG, "signInWithEmail:success");
						currentUser = mAuth.getCurrentUser();
						Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_LONG).show();

//						updateUI(user);
					} else {
						// If sign in fails, display a message to the user.
//						updateUI(null);
						Toast.makeText(MainActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
						
					}
					
				}
			});
	
}

@Override
protected void onStart() {
	super.onStart();
	mAuth.addAuthStateListener(mAuthListener);
}

@Override
protected void onStop() {
	super.onStop();
	if(mAuthListener != null) {
	mAuth.removeAuthStateListener(mAuthListener);
	}
	}
}
