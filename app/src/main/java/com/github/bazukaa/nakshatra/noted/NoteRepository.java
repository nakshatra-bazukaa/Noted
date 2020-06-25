package com.github.bazukaa.nakshatra.noted;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.github.bazukaa.nakshatra.noted.db.NoteDatabase;
import com.github.bazukaa.nakshatra.noted.db.TrashNoteDatabase;
import com.github.bazukaa.nakshatra.noted.db.dao.NoteDao;
import com.github.bazukaa.nakshatra.noted.db.dao.TrashNoteDao;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;
import com.github.bazukaa.nakshatra.noted.utils.NoteAsyncTask;
import com.github.bazukaa.nakshatra.noted.utils.TrashNoteAsyncTask;
import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> notes;

    private TrashNoteDao trashNoteDao;
    private LiveData<List<TrashNote>> trashNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        notes = noteDao.getNotes();

        TrashNoteDatabase trashNoteDatabase = TrashNoteDatabase.getInstance(application);
        trashNoteDao = trashNoteDatabase.trashNoteDao();
        trashNotes = trashNoteDao.getTrashNotes();
    }


    public void insert(Note note) { new NoteAsyncTask(noteDao, NoteAsyncTask.INSERT_NOTE_REQUEST).execute(note); }
    public void update(Note note) { new NoteAsyncTask(noteDao, NoteAsyncTask.UPDATE_NOTE_REQUEST).execute(note); }
    public void delete(Note note) { new NoteAsyncTask(noteDao, NoteAsyncTask.DELETE_NOTE_REQUEST).execute(note); }
    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insert(TrashNote trashNote) {new TrashNoteAsyncTask(trashNoteDao, TrashNoteAsyncTask.INSERT_TRASH_NOTE_REQUEST).execute(trashNote); }
    public void update(TrashNote trashNote){new TrashNoteAsyncTask(trashNoteDao, TrashNoteAsyncTask.UPDATE_TRASH_NOTE_REQUEST).execute(trashNote);}
    public void delete(TrashNote trashNote){new TrashNoteAsyncTask(trashNoteDao, TrashNoteAsyncTask.DELETE_TRASH_NOTE_REQUEST).execute(trashNote); }
    public LiveData<List<TrashNote>> getTrashNotes() {return trashNotes;}

}
