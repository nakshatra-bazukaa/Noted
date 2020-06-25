package com.github.bazukaa.nakshatra.noted.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;

import java.util.List;

@Dao
public interface TrashNoteDao {

    @Insert
    void insert(TrashNote trashNote);

    @Delete
    void delete(TrashNote trashNote);

    @Update
    void update(TrashNote trashNote);

    @Query("SELECT * FROM trash_note_table ORDER BY id DESC")
    LiveData<List<TrashNote>> getTrashNotes();
}
