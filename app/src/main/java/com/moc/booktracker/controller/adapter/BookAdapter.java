package com.moc.booktracker.controller.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moc.booktracker.R;
import com.moc.booktracker.controller.BookProfileActivity;
import com.moc.booktracker.model.entity.Author;
import com.moc.booktracker.model.entity.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private LayoutInflater inflater;

    private int layout;
    private List<Book> books;

    private Author author;


    public BookAdapter(@NonNull Context context, @NonNull List<Book> books, @NonNull Author author) {
        super(context, R.layout.item_author, books);
        this.author = author;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, null);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView genreTextView = convertView.findViewById(R.id.genreTextView);
        TextView yearOfPublication = convertView.findViewById(R.id.yearOfPublicationTextView);
        TextView authorTextView = convertView.findViewById(R.id.authorTextView);

        Book book = getItem(position);

        titleTextView.setText(book.getName());
        authorTextView.setText(author.getFull_name());
        yearOfPublication.setText(book.getYear_of_publication());

        convertView.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), BookProfileActivity.class).putExtra("id", book.getId()).putExtra("getId", book.getAuthorId())
            );
        });


        return convertView;
    }
}
