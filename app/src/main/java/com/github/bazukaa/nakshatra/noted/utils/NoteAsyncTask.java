package com.github.bazukaa.nakshatra.noted.utils;

import android.os.AsyncTask;
import com.github.bazukaa.nakshatra.noted.db.dao.NoteDao;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;

public class NoteAsyncTask extends AsyncTask<Note,Void,Void> {
    public static final int INSERT_NOTE_REQUEST = 1;
    public static final int UPDATE_NOTE_REQUEST = 2;
    public static final int DELETE_NOTE_REQUEST = 3;

    private NoteDao noteDao;
    private int request;

    public NoteAsyncTask(NoteDao noteDao, int request) {
        this.noteDao = noteDao;
        this.request = request;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        if(request == INSERT_NOTE_REQUEST)
            noteDao.insert(notes[0]);
        else if(request == UPDATE_NOTE_REQUEST)
            noteDao.update(notes[0]);
        else if(request == DELETE_NOTE_REQUEST)
            noteDao.delete(notes[0]);

        return null;
    }
}
