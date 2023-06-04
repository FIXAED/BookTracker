package com.moc.booktracker;

import android.app.Application;

import androidx.room.Room;

import com.moc.booktracker.model.AppDatabase;
import com.moc.booktracker.service.AuthorService;
import com.moc.booktracker.service.BookService;

public class MyApplication extends Application {
    public AppDatabase db;
    public BookService bookService;
    public AuthorService authorService;
    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").build();



        authorService = new AuthorService(db);
        bookService = new BookService(db);

    }
}
