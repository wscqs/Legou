package com.cqs.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
   
	private String id;
	private String name;
	private String loginPwd;
	private String payPwd;
	private String tel;
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.loginPwd);
		dest.writeString(this.payPwd);
		dest.writeString(this.tel);
		dest.writeString(this.avatar);
	}

	public User() {
	}

	protected User(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.loginPwd = in.readString();
		this.payPwd = in.readString();
		this.tel = in.readString();
		this.avatar = in.readString();
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
