package com.example.hidden.editimage;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hidden on 11/1/2016.
 */

public class DataPassing implements Parcelable {
    private Uri pathFile;
// Get and Set methods

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(getPathFile());
    }

    // We reconstruct the object reading from the Parcel data
    public DataPassing(Parcel p) {
        setPathFile((Uri)p.readValue(getClass().getClassLoader()));
    }

    public DataPassing() {
    }

    // We need to add a Creator
    public static final Parcelable.Creator<DataPassing> CREATOR = new Parcelable.Creator<DataPassing>() {

        @Override
        public DataPassing createFromParcel(Parcel parcel) {
            return new DataPassing(parcel);
        }

        @Override
        public DataPassing[] newArray(int size) {
            return new DataPassing[size];
        }
    };

    public Uri getPathFile() {
        return pathFile;
    }

    public void setPathFile(Uri pathFile) {
        this.pathFile = pathFile;
    }
}