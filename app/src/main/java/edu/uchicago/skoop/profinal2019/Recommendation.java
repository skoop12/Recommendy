package edu.uchicago.skoop.profinal2019;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

@Entity(tableName = "recs_table")
public class Recommendation {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "wikipedia")
    private String wikipedia;

    @ColumnInfo(name = "youtube")
    private String youtube;


    public Recommendation(@NonNull int id, @NonNull String name, @NonNull String type, String description, String wikipedia, String youtube) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.wikipedia = wikipedia;
        this.youtube = youtube;
    }

    public int getId() { return this.id; }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }

    public String getDescription() { return this.description; }

    public String getWikipedia() { return this.wikipedia; }

    public String getYoutube() { return this.youtube; }

}
