package com.example.isync.Model;

public class FBUser {
	private  String name;
	private  String email;

public FBUser(String displayName, String displayEmail) {
	this.name = displayName;
	this.email = displayEmail;
}
public FBUser() {

}

public String getName() {
	return name;
}

public void setName(String displayName) {
	this.name = displayName;
}

public String getEmail() {
	return email;
}

public void setEmail(String displayEmail) {
	this.email = displayEmail;
}
}
