package com.notesapp.HA;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface NoteDao {

    @Query( "select * from notes")
    List<Notes> getNotes();

    @Insert
    void addNotes(Notes notes);

    @Delete
    void deleteNotes(Notes notes);
}
