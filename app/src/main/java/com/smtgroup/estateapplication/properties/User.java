package com.smtgroup.estateapplication.properties;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Tugay Demirel.
 *
 */

public class User implements Parcelable {

    private String id;
    private String name;
    private String surname;
    private String phone;
    private String email;

    public User(){

    }

    public User(Parcel parcel){
        id = parcel.readString();
        name = parcel.readString();
        surname = parcel.readString();
        phone = parcel.readString();
        email = parcel.readString();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(phone);
        parcel.writeString(email);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[0];
        }
    };
}
