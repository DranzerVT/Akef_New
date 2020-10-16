package com.android.akef.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.akef.Tables.Tournament;

@Database(entities = {Tournament.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static String DATABASE_NAME = "akef.db";

    public abstract TournamentDao tournamentDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {

            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .fallbackToDestructiveMigration()
                            .addMigrations() //edit_scheme2
                            .allowMainThreadQueries()

                            .build();

        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
