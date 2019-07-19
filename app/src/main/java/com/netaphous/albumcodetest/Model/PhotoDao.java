package com.netaphous.albumcodetest.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM photo_table")
    Observable<List<PhotoPojo>> subscribeToPhotos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable storePhotos(List<PhotoPojo> photos);

    @Query("UPDATE photo_table SET favorited = :newValue WHERE id LIKE :photoId")
    Completable updateFavorites(int photoId, boolean newValue);
}
