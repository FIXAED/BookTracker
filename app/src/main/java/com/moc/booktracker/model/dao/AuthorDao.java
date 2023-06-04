package com.moc.booktracker.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.moc.booktracker.model.entity.Author;

import java.util.List;

@Dao
public interface AuthorDao {
    @Query("select * from authors order by id")
    List<Author> getAuthors();

    @Query("select * from authors where id = :id")
    Author getAuthor(long id);



    @Insert
    long insertAuthor(Author author);

    @Update
    void updateAuthor(Author author);

    @Delete
    int deleteAuthor(Author author);
}
