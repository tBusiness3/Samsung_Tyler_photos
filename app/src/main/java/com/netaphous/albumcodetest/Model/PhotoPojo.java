package com.netaphous.albumcodetest.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photo_table")
public class PhotoPojo implements Parcelable {
    public int albumId;
    @PrimaryKey
    @ColumnInfo
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String url;
    @ColumnInfo
    public String thumbnailUrl;
    @ColumnInfo
    public boolean favorited = false;

    public PhotoPojo(){
    }

    protected PhotoPojo(Parcel in) {
        albumId = in.readInt();
        id = in.readInt();
        title = in.readString();
        url = in.readString();
        thumbnailUrl = in.readString();
        favorited = in.readByte() != 0;
    }

    public static final Creator<PhotoPojo> CREATOR = new Creator<PhotoPojo>() {
        @Override
        public PhotoPojo createFromParcel(Parcel in) {
            return new PhotoPojo(in);
        }

        @Override
        public PhotoPojo[] newArray(int size) {
            return new PhotoPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(albumId);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(thumbnailUrl);
        dest.writeByte((byte) (favorited ? 1 : 0));
    }
}

//"albumId": 1,
//    "id": 1,
//    "title": "accusamus beatae ad facilis cum similique qui sunt",
//    "url": "https://via.placeholder.com/600/92c952",
//    "thumbnailUrl": "https://via.placeholder.com/150/92c952"