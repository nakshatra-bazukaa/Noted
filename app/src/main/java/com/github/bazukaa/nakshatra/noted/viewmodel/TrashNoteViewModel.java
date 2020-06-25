package com.github.bazukaa.nakshatra.noted.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.bazukaa.nakshatra.noted.NoteRepository;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;

import java.util.List;

public class TrashNoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<TrashNote>> trashNotes;

    public TrashNoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        trashNotes = noteRepository.getTrashNotes();
    }

    public void insert(TrashNote trashNote){noteRepository.insert(trashNote);}

    public void update(TrashNote trashNote){noteRepository.update(trashNote);}

    public void delete(TrashNote trashNote){noteRepository.delete(trashNote);}

    public LiveData<List<TrashNote>> getTrashNotes(){return trashNotes;}
}
