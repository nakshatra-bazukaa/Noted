package com.github.bazukaa.nakshatra.noted.model;

import androidx.lifecycle.LiveData;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import java.util.List;

public interface TrashViewModelHelper {

    void insert(TrashNote trashNote);

    void update(TrashNote trashNote);

    void delete(TrashNote trashNote);

    LiveData<List<TrashNote>> getTrashNotes();

    void insert(Note note);
}
