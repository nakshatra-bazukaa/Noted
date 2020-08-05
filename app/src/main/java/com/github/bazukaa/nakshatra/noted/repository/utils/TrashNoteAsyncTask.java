package com.github.bazukaa.nakshatra.noted.repository.utils;


import android.os.AsyncTask;
import com.github.bazukaa.nakshatra.noted.db.dao.TrashNoteDao;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;

public class TrashNoteAsyncTask extends AsyncTask<TrashNote,Void,Void> {
    public static final int INSERT_TRASH_NOTE_REQUEST = 1;
    public static final int UPDATE_TRASH_NOTE_REQUEST = 2;
    public static final int DELETE_TRASH_NOTE_REQUEST = 3;

    private TrashNoteDao trashNoteDao;
    private int request;

    public TrashNoteAsyncTask(TrashNoteDao trashNoteDao, int request) {
        this.trashNoteDao = trashNoteDao;
        this.request = request;
    }

    @Override
    protected Void doInBackground(TrashNote... trashNotes) {
        if(request == INSERT_TRASH_NOTE_REQUEST)
            trashNoteDao.insert(trashNotes[0]);
        else if(request == UPDATE_TRASH_NOTE_REQUEST)
            trashNoteDao.update(trashNotes[0]);
        else if(request == DELETE_TRASH_NOTE_REQUEST)
            trashNoteDao.delete(trashNotes[0]);

        return null;
    }
}
