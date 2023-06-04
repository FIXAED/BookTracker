package com.moc.booktracker.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.moc.booktracker.model.entity.Author;
import com.moc.booktracker.model.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Query("select * from library order by id")
    List<Book> getBooks();

    @Query("select * from library where author_id = :authorId order by id")
    List<Book> getBooksOfAuthor(long authorId);

    @Query("select * from library where id = :id")
    Book getBook(long id);


    @Insert
    long createBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    int deleteBook(Book  book);

}
