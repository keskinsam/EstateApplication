package com.smtgroup.estateapplication.properties;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tugay Demirel.
 *
 */

public class Category implements Parcelable{
    private String id;
    private String topId;
    private String name;

    public Category(){

    }

    public Category(Parcel parcel){
        id = parcel.readString();
        name = parcel.readString();
        topId = parcel.readString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(topId);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel parcel) {
            return new Category(parcel);
        }

        @Override
        public Category[] newArray(int i) {
            return new Category[0];
        }
    };
}
