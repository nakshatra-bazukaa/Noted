package com.github.bazukaa.nakshatra.noted.model;

import androidx.lifecycle.LiveData;

import com.github.bazukaa.nakshatra.noted.db.entity.Note;

import java.util.List;

public interface NoteViewModelHelper {

    void insert();

    void update();

    void delete();

    LiveData<List<Note>> getNotes();
}
