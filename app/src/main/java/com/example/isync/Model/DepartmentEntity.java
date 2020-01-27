package com.example.isync.Model;

public class DepartmentEntity {
	private String DepartmentKey;
	private String DepartmentID;
	private  String DepartmentName;

public DepartmentEntity(String departmentKey, String departmentID, String departmentName) {
	DepartmentKey = departmentKey;
	DepartmentID = departmentID;
	DepartmentName = departmentName;
}

public String getDepartmentKey() {
	return DepartmentKey;
}

public String getDepartmentID() {
	return DepartmentID;
}

public String getDepartmentName() {
	return DepartmentName;
}

@Override
public String toString() {
	return DepartmentName;
}
}
