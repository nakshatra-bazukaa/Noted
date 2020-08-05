package com.github.bazukaa.nakshatra.noted.viewmodelhelper;

import androidx.lifecycle.LiveData;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import java.util.List;

public interface NoteViewModelHelper {

    void insert(Note note);

    void update(Note note);

    void delete(Note note);

    LiveData<List<Note>> getNotes();

    void insert(TrashNote trashNote);
}
