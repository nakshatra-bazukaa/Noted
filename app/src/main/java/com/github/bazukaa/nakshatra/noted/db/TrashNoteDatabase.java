package com.github.bazukaa.nakshatra.noted.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.bazukaa.nakshatra.noted.db.dao.TrashNoteDao;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;

@Database(entities = {TrashNote.class}, version = 1)
public abstract class TrashNoteDatabase extends RoomDatabase {

    private static TrashNoteDatabase trashDatabaseInstance;

    public abstract TrashNoteDao trashNoteDao();

    public static synchronized TrashNoteDatabase getInstance(Context context){
        if(trashDatabaseInstance == null){
            trashDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    TrashNoteDatabase.class, "trash_note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return trashDatabaseInstance;
    }
}
