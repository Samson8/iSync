package com.example.isync.Model;

import java.util.ArrayList;
import java.util.List;

public class DataCentric {

	private List<CollegeEntity> collegeEntityList;
	private List<DepartmentEntity> departmentEntityList;
	private static DataCentric ourInstance;

public static DataCentric getInstance(){
	if (ourInstance == null){
		Initiator();
	}
	return  ourInstance;
}

public DataCentric() {}

private static void  Initiator(){
	ourInstance = new DataCentric();
	ourInstance.initializeCollegeList();
	ourInstance.initializeDepartmentList();
}

public DataCentric(List<CollegeEntity> collegeEntityList, List<DepartmentEntity> departmentEntityList) {
	this.collegeEntityList = collegeEntityList;
	this.departmentEntityList = departmentEntityList;
}

public List<CollegeEntity> getCollegeEntityList() {
	return collegeEntityList;
}

public List<DepartmentEntity> getDepartmentEntityList() {
	return departmentEntityList;
}

public static DataCentric getOurInstance() {
	return ourInstance;
}

private void initializeCollegeList(){
		collegeEntityList = new ArrayList<>();
		collegeEntityList.add(new CollegeEntity("0","", "pls select college/ faculty"));
		collegeEntityList.add(new CollegeEntity("1", "AGR", "College of Agriculture"));
		collegeEntityList.add(new CollegeEntity("2", "EDU", "College of Education"));
		collegeEntityList.add(new CollegeEntity("3", "ENG", "College of Engineering"));
		collegeEntityList.add(new CollegeEntity("4", "HUM", "College of Humanities"));
		collegeEntityList.add(new CollegeEntity("5", "ICT", "College of ICT"));
		collegeEntityList.add(new CollegeEntity("6", "LAW", "College of Law"));
		collegeEntityList.add(new CollegeEntity("7", "PAS", "College of Pure and applied"));
		
	}
	private void initializeDepartmentList(){
		departmentEntityList = new ArrayList<>();
		departmentEntityList.add(new DepartmentEntity("0", "","pls select a valid college."));
		departmentEntityList.add(new DepartmentEntity("1", "College of Agriculture", "Agricultural Economics"));
		departmentEntityList.add(new DepartmentEntity("2", "College of Agriculture", "Agricultural production"));
		departmentEntityList.add(new DepartmentEntity("3", "College of Agriculture", "Agricultural extension"));
		departmentEntityList.add(new DepartmentEntity("4", "College of Education", "Early childhood"));
		departmentEntityList.add(new DepartmentEntity("5", "College of Education", "Human Kinetics"));
		departmentEntityList.add(new DepartmentEntity("6", "College of Education", "Kinesology"));
		departmentEntityList.add(new DepartmentEntity("7", "College of Engineering", "Mechanical"));
		departmentEntityList.add(new DepartmentEntity("8", "College of Engineering", "Electrical"));
		departmentEntityList.add(new DepartmentEntity("9", "College of Engineering", "Aeronautics"));
		departmentEntityList.add(new DepartmentEntity("10", "College of Humanities", "Political Science"));
		departmentEntityList.add(new DepartmentEntity("11", "College of Humanities", "Accounting"));
		departmentEntityList.add(new DepartmentEntity("12", "College of Humanities", "Bussiness Edu."));
		departmentEntityList.add(new DepartmentEntity("13", "College of ICT", "Library and Information"));
		departmentEntityList.add(new DepartmentEntity("15", "College of ICT", "Computer Science"));
		departmentEntityList.add(new DepartmentEntity("16", "College of ICT", "Mass Communication"));
		departmentEntityList.add(new DepartmentEntity("17", "College of Law", "LAW1"));
		departmentEntityList.add(new DepartmentEntity("18", "College of Law", "LAW2"));
		departmentEntityList.add(new DepartmentEntity("19", "College of Law", "LAW3"));
		departmentEntityList.add(new DepartmentEntity("20", "College of Pure and applied", "Chemistry"));
		departmentEntityList.add(new DepartmentEntity("21", "College of Pure and applied", "Mathematics"));
		

	}
}
