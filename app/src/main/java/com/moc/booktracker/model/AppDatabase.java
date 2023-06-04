package com.moc.booktracker.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.moc.booktracker.model.dao.AuthorDao;
import com.moc.booktracker.model.dao.BookDao;
import com.moc.booktracker.model.entity.Author;
import com.moc.booktracker.model.entity.Book;

@Database(entities = {Book.class, Author.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();

    public abstract AuthorDao authorDao();
}
