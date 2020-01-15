package com.github.bazukaa.nakshatra.noted;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabaseInstance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(noteDatabaseInstance == null){
            noteDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .addCallback(roomDbCallBack)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return noteDatabaseInstance;
    }

    private static RoomDatabase.Callback roomDbCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateNoteDbTask(noteDatabaseInstance).execute();
        }
    };

    private static class PopulateNoteDbTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private PopulateNoteDbTask(NoteDatabase noteDatabase){
            noteDao = noteDatabase.noteDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("WalkThrough", "1. To reveal the contents of the card click on it.\n2. To create a note click on the fab at the bottom of the screen, to save a note click on the check button on the top right corner.\n3. To delete a note just swipe left or right over its card.\n4. Saved notes can also be updated by clicking on its card.\n5. Feel free to mail me to make this app more happening by dropping down your valuable suggestion on nakshatravasugupta@gmail.com .", 1));
            return null;
        }
    }

}
