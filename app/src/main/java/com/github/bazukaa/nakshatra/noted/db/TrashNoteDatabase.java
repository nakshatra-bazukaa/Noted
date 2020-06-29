package com.github.bazukaa.nakshatra.noted.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.github.bazukaa.nakshatra.noted.db.dao.NoteDao;
import com.github.bazukaa.nakshatra.noted.db.dao.TrashNoteDao;
import com.github.bazukaa.nakshatra.noted.db.entity.Note;
import com.github.bazukaa.nakshatra.noted.db.entity.TrashNote;

@Database(entities = {TrashNote.class}, version = 1)
public abstract class TrashNoteDatabase extends RoomDatabase {

    private static TrashNoteDatabase trashDatabaseInstance;

    public abstract TrashNoteDao trashNoteDao();

    public static synchronized TrashNoteDatabase getInstance(Context context){
        if(trashDatabaseInstance == null){
            trashDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    TrashNoteDatabase.class, "trash_note_database")
                    .addCallback(roomDbCallBack)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return trashDatabaseInstance;
    }

    private static RoomDatabase.Callback roomDbCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateTrashDbTask(trashDatabaseInstance).execute();
        }
    };

    private static class PopulateTrashDbTask extends AsyncTask<Void, Void, Void> {
        private TrashNoteDao trashNoteDao;

        private PopulateTrashDbTask(TrashNoteDatabase trashNoteDatabase){
            trashNoteDao = trashNoteDatabase.trashNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String title = "This is Trash bin";
            String note =
                    "1. To reveal the contents of the note click on it.\n\n" +
                            "2. To delete a note just swipe left or right over its card.\n\n" +
                            "3. Trash notes can also be updated by clicking on its card.\n\n" +
                            "4. Trash notes can be restored by long pressing them.\n\n" +
                            "5. Notes deleted from trash are permanently deleted.\n\n" +
                            "6. Feel free to mail me to make this app more happening by dropping down your valuable suggestion on nakshatravasugupta@gmail.com .";
            long timeStamp = System.currentTimeMillis();
            TrashNote initialNote = new TrashNote(title, note, timeStamp);
            trashNoteDao.insert(initialNote);
            return null;
        }
    }
}
