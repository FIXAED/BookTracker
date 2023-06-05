package com.moc.booktracker.controller;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.moc.booktracker.MyApplication;
import com.moc.booktracker.R;
import com.moc.booktracker.controller.adapter.BookAdapter;
import com.moc.booktracker.model.entity.Author;
import com.moc.booktracker.model.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private long authorId;
    private MyApplication app;
    private ListView bookListView;
    private BookAdapter bookAdapter;
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        authorId = getIntent().getLongExtra("id", 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Библиотека");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        app = (MyApplication) getApplication();
        bookListView = findViewById(R.id.bookListView);
        bookListView.setEmptyView(findViewById(android.R.id.empty));

        new Thread(() -> {
            Author author = app.authorService.getAuthor(authorId);
            if (author == null) {
                return;
            }
            runOnUiThread(() -> {
                bookAdapter = new BookAdapter(this, books, author);
                bookListView.setAdapter(bookAdapter);
            });
        }).start();


    }


    @Override
    protected void onStart() {
        super.onStart();

        new Thread(() -> {
            List<Book> books;
            books = app.bookService.getBooks(authorId);

            runOnUiThread(() -> {
                bookAdapter.clear();
                bookAdapter.addAll(books);
                bookAdapter.notifyDataSetChanged();
            });
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_create_book){
            Intent intent = new Intent(this, BookProfileActivity.class);
            intent.putExtra("authorId", authorId);
            startActivity(intent);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

}