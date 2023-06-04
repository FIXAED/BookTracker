package com.moc.booktracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.moc.booktracker.MyApplication;
import com.moc.booktracker.R;
import com.moc.booktracker.controller.adapter.AuthorAdapter;
import com.moc.booktracker.model.entity.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {

    private MyApplication app;
    private ListView groupListView;
    private AuthorAdapter authorAdapter;
    private List<Author> authors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Писатели");
        }

        app = (MyApplication) getApplication();

        groupListView = findViewById(R.id.authorListView);
        groupListView.setEmptyView(findViewById(android.R.id.empty));

        authorAdapter = new AuthorAdapter(this, authors);
        groupListView.setAdapter(authorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateAuthors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_author, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_author) {
            new AuthorProfileDialogFragment().show(
                    getSupportFragmentManager(), AuthorProfileDialogFragment.TAG);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void updateAuthors() {
        new Thread(() -> {
            List<Author> authors = app.authorService.getAuthors();
            runOnUiThread(() -> {
                authorAdapter.clear();
                authorAdapter.addAll(authors);
                authorAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    public void authorDeletionConfirmed(Author auhtor) {
        new Thread(() -> {
            try {
                app.authorService.deleteAuthor(auhtor);
                runOnUiThread(this::updateAuthors);
            } catch (IllegalArgumentException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Автор содержит книги", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

}