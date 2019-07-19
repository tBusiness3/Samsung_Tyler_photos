package com.netaphous.albumcodetest.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PhotoPojo.class}, version = 2, exportSchema = false)
public abstract class PhotoDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "photo_database";

    public abstract PhotoDao dao();

private static volatile PhotoDatabase INSTANCE;

    public static PhotoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            PhotoDatabase.class, DATABASE_NAME)
                            //.addMigrations(MIGRATION_16_17)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
