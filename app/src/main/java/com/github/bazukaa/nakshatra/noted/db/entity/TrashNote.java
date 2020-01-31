package com.github.bazukaa.nakshatra.noted.db.entity;

import androidx.room.Entity;

@Entity(tableName = "trash_note_table")
public class TrashNote {

    private int id;

    private String title;

    private String note;

    private long timestamp;

    public TrashNote(int id, String title, String note, long timestamp) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getTrashId() {
        return id;
    }

    public void setTrashId(int id) {
        this.id = id;
    }

    public String getTrashTitle() {
        return title;
    }

    public void setTrashTitle(String title) {
        this.title = title;
    }

    public String getTrashNote() {
        return note;
    }

    public void setTrashNote(String note) {
        this.note = note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
