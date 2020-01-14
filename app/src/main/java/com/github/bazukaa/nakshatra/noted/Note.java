package com.github.bazukaa.nakshatra.noted;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String note;

    private int timeStamp;

    public Note(String title, String note, int timeStamp) {
        this.title = title;
        this.note = note;
        this.timeStamp = timeStamp;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public int getTimeStamp() { return timeStamp; }

    public void setTimeStamp(int timeStamp) { this.timeStamp = timeStamp; }
}
