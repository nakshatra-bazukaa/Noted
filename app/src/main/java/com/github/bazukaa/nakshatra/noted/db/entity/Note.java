package com.github.bazukaa.nakshatra.noted.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "note_text")
    private String note;

    @ColumnInfo(name = "date_time")
    private long timeStamp;

    @ColumnInfo(name = "image_path")
    private String imgPath;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "web_link")
    private String webLink;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
