package com.moc.booktracker.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.moc.booktracker.MyApplication;
import com.moc.booktracker.R;
import com.moc.booktracker.model.entity.Book;
import com.moc.booktracker.service.BookService;


public class BookProfileActivity extends AppCompatActivity {



    private Long authorId = null;
    private Long bookId = null;
    private Book book = null;
    private boolean isForEditing = false;

    private EditText titleEditText;
    private EditText yearOfPublicationEditText;

    private BookService bookService;
    private EditText genreEditText;
    Button saveButton;
    ImageButton deleteButton;

    private BookService studentService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        studentService = ((MyApplication) getApplication()).bookService;

        authorId = getIntent().getLongExtra("authorId", -1);
        if (authorId == -1) {
            finish();
        }

        bookId = getIntent().getLongExtra("id", -1);
        if (bookId == -1) { bookId = null; }
        isForEditing = bookId != null;

        if (getSupportActionBar() != null) {
            if (!isForEditing) {
                getSupportActionBar().setTitle("Добаввление книги");
            } else {
                getSupportActionBar().setTitle("Изменение книги");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleEditText = findViewById(R.id.titleEditText);
        yearOfPublicationEditText = findViewById(R.id.yearOfPublicationEditText);
        genreEditText = findViewById(R.id.genreEditText);

        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);



        if (isForEditing) {
            new Thread(() -> {
                book = studentService.getBook(bookId);
                runOnUiThread(() -> {
                    titleEditText.setText(book.getName());
                    yearOfPublicationEditText.setText(book.getYear_of_publication() + "");
                    genreEditText.setText(book.getGenre());
                });
            }).start();
        }

        saveButton.setOnClickListener((v) -> {

            if (!isForEditing) {
                book = new Book();
            }

            book.setName(titleEditText.getText().toString());
            book.setYear_of_publication(Integer.parseInt(yearOfPublicationEditText.getText().toString()));
            book.setGenre(genreEditText.getText().toString());

            if (book.getName() == null || book.getName().length() == 0) {
                return;
            }

            new Thread(() -> {
                if (!isForEditing) {
                    book.setAuthorId(authorId);
                    book = studentService.createBook(book);
                    bookId = book.getId();
                } else {
                    bookService.editBook(book);
                }

                runOnUiThread(() -> {
                    finish();
                    if (!isForEditing) {
                        deleteButton.setVisibility(View.VISIBLE);
                        isForEditing = true;
                    }
                });

            }).start();
        });

        if (!isForEditing) {
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener((v) -> {
            if (isForEditing) {
                new Thread(() -> {
                    try {
                        bookService.deleteBook(book);
                        runOnUiThread(this::finish);
                    } catch (IllegalArgumentException e) {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
