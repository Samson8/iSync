package com.example.isync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.isync.Model.CollegeEntity;
import com.example.isync.Model.DataCentric;
import com.example.isync.Model.DepartmentEntity;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CreateProfile extends AppCompatActivity{
	Toolbar toolbar;
	private String TAG = "CreateProfile";
//	creating variables to hold values from views
	private AppCompatEditText firstname;
	private AppCompatEditText lastname;
	private RadioGroup gender;
	private AppCompatEditText phone;
	private AppCompatEditText email;
	private AppCompatSpinner college;
	private AppCompatSpinner department;
	private AppCompatSpinner level;
	private AppCompatEditText matricNo;
	private AppCompatEditText password;
	private AppCompatEditText password2;
//	linking each textInputLayout
	TextInputLayout fnameTextLayout;
	TextInputLayout lnameTextLayout;
//	TextInputLayout genderTextLayout;
	TextInputLayout phoneTextLayout;
	TextInputLayout emailTextLayout;
	TextInputLayout collegeTextLayout;
	TextInputLayout departmentTextLayout;
	TextInputLayout levelTextLayout;
	TextInputLayout matricNoTextLayout;
	TextInputLayout pass1TextLayout;
	TextInputLayout pass2TextLayout;
	
	private List<CollegeEntity> colleges;
	private List<DepartmentEntity> departments;

	private int selectedPosition;
	private String SelectedCollege = "";
	
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_create_profile);
	toolbar = (Toolbar) findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	firstname = (AppCompatEditText) findViewById(R.id.firstnameEd);
	lastname = (AppCompatEditText) findViewById(R.id.lastnameEd);
	gender = (RadioGroup) findViewById(R.id.genderRg);
	phone = (AppCompatEditText) findViewById(R.id.phoneEd);
	email = (AppCompatEditText) findViewById(R.id.emailEd);
	college = (AppCompatSpinner) findViewById(R.id.collegeSp);
	department = (AppCompatSpinner) findViewById(R.id.departmentSp);
	level = (AppCompatSpinner) findViewById(R.id.levelSp);
	matricNo = (AppCompatEditText) findViewById(R.id.matricEd);
	password = (AppCompatEditText) findViewById(R.id.passEd1);
	password2 = (AppCompatEditText) findViewById(R.id.passEd2);
	
	fnameTextLayout = findViewById(R.id.fname_textInputLayout);
	lnameTextLayout = findViewById(R.id.lname_textInputLayout);
	phoneTextLayout = findViewById(R.id.phone_textInputLayout);
	emailTextLayout = findViewById(R.id.email_textInputLayout);
	collegeTextLayout = findViewById(R.id.college_textInputLayout);
	departmentTextLayout = findViewById(R.id.department_textInputLayout);
	levelTextLayout = findViewById(R.id.level_textInputLayout);
	matricNoTextLayout = findViewById(R.id.matric_textInputLayout);
	pass1TextLayout = findViewById(R.id.pass1_textInputLayout);
	pass2TextLayout = findViewById(R.id.pass2_textInputLayout);
	
	gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
	{
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch(checkedId){
				case R.id.maleRd:
					// do operations specific to this selection
					Toast.makeText(getApplicationContext(), "male", Toast.LENGTH_LONG).show();
					break;
				case R.id.femaleRd:
					// do operations specific to this selection
					Toast.makeText(getApplicationContext(), "female", Toast.LENGTH_LONG).show();
				break;
			}
		}
	});
	

	viewMustNotBeEmpty(firstname, fnameTextLayout, "firstname is required*");
	viewMustNotBeEmpty(lastname, lnameTextLayout, "lastname is required*");
	viewMustNotBeEmpty(phone, phoneTextLayout, "phone number is required*");
	viewMustNotBeEmpty(email, emailTextLayout, "email is required*");
	viewMustNotBeEmpty(matricNo, matricNoTextLayout, "matric. number is required*");
	viewMustNotBeEmpty(password, pass1TextLayout, "password is required*");
//	viewMustNotBeEmpty(password2, pass2TextLayout, "password is required");
	password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (password2.getText().toString().isEmpty()) {
				pass2TextLayout.setErrorEnabled(true);
				pass2TextLayout.setError("password is required");
			}else{
				pass2TextLayout.setErrorEnabled(false);
			}
		}
	});
	
	password2.addTextChangedListener(new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (password2.getText().toString().isEmpty()) {
				pass2TextLayout.setErrorEnabled(true);
				pass2TextLayout.setError("password is required");
			}else if(password2.getText().toString() != password.getText().toString()) {
				pass2TextLayout.setErrorEnabled(true);
				pass2TextLayout.setError("password do not match");
			}else{
				pass2TextLayout.setErrorEnabled(false);
			}
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		
		}
	});
	
	colleges = DataCentric.getInstance().getCollegeEntityList();
	ArrayAdapter<CollegeEntity> collegeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,  colleges);
	collegeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	college.setAdapter(collegeArrayAdapter);
	//this is to populate the department spinner depending on the college selected
	college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			try {
				if (position != 0) {
					collegeTextLayout.setErrorEnabled(false);
					selectedPosition = position;
					SelectedCollege = college.getItemAtPosition(position).toString();
					Toast.makeText(CreateProfile.this, SelectedCollege, Toast.LENGTH_LONG).show();
					getListOfDepartments(SelectedCollege);
				}else if(position==0){
//					Toast.makeText(CreateProfile.this, "hey", Toast.LENGTH_LONG).show();
					collegeTextLayout.setErrorEnabled(true);
					collegeTextLayout.setError("Invalid selection");
				}else {}
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "onItemSelected: "+ e);
			}
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		
		}
	});
	

}
private void getListOfDepartments(String collegeFromSpinner){
	ArrayList<String> Depts= new ArrayList<String>();
	departments = DataCentric.getInstance().getDepartmentEntityList();
	for(int i= 0; i<departments.size(); i++){
		if(i==departments.size()) {
			break;
	
		}else {
			if (departments.get(i).getDepartmentID().equals(collegeFromSpinner)) {
				String res = departments.get(i).toString();
				Depts.add(res);
				
			}
		}

	}
	Log.d(TAG, "getListOfDepartments: " + Depts);
	ArrayAdapter<String> departmentArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,  Depts);
	departmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	department.setAdapter(departmentArrayAdapter);
	
}




private void viewMustNotBeEmpty(final AppCompatEditText view, final TextInputLayout textInputLayout, final String message) {
	view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (view.getText().toString().isEmpty()){
				textInputLayout.setErrorEnabled(true);
				textInputLayout.setError(message);
			} else {
				textInputLayout.setErrorEnabled(false);
			}
		}
	});
	
	view.addTextChangedListener(new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (view.getText().toString().isEmpty()){
				textInputLayout.setErrorEnabled(true);
				textInputLayout.setError(message);
			} else {
				textInputLayout.setErrorEnabled(false);
			}
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		
		}
	});
	
}
}
