package com.example.isync.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CollegeEntity implements Parcelable {
	private String collegeKey;
	private String collegeID;
	private String collegeName;

public CollegeEntity(String collegeKey, String collegeID, String collegeName) {
	this.collegeKey = collegeKey;
	this.collegeID = collegeID;
	this.collegeName = collegeName;
}

protected CollegeEntity(Parcel in) {
	collegeKey = in.readString();
	collegeID = in.readString();
	collegeName = in.readString();
}

public static final Creator<CollegeEntity> CREATOR = new Creator<CollegeEntity>() {
	@Override
	public CollegeEntity createFromParcel(Parcel in) {
		return new CollegeEntity(in);
	}
	
	@Override
	public CollegeEntity[] newArray(int size) {
		return new CollegeEntity[size];
	}
};

public String getCollegeKey() {
	return collegeKey;
}

public String getCollegeID() {
	return collegeID;
}

public String getCollegeName() {
	return collegeName;
}

@Override
public String toString() {
	return  collegeName;
}

@Override
public int describeContents() {
	return 0;
}

@Override
public void writeToParcel(Parcel dest, int flags) {
	
	dest.writeString(collegeKey);
	dest.writeString(collegeID);
	dest.writeString(collegeName);
}
}
