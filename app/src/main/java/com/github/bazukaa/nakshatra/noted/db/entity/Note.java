package com.github.bazukaa.nakshatra.noted.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String note;

    private long timeStamp;

    @Ignore
    public Note(int id, String title, String note, long timeStamp) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.timeStamp = timeStamp;
    }

    public Note(String title, String note, long timeStamp) {
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

    public long getTimeStamp() { return timeStamp; }

    public void setTimeStamp(int timeStamp) { this.timeStamp = timeStamp; }
}
