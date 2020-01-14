package com.github.bazukaa.nakshatra.noted;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> notes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        notes = noteDao.getNotes();
    }

    public void insert(Note note) {
        new InsertNoteTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteTask(noteDao).execute(note);
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    private static class InsertNoteTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
}
