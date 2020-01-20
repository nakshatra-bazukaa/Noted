package com.github.bazukaa.nakshatra.noted.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.bazukaa.nakshatra.noted.NoteRepository;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> notes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        notes = noteRepository.getNotes();
    }

    public void insert(Note note){
        noteRepository.insert(note);
    }

    public  void update(Note note){
        noteRepository.update(note);
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }

    public LiveData<List<Note>> getNotes(){
        return notes;
    }
}
