package com.moc.booktracker.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AuthorWithBooks {
    @Embedded
    public Author author;
    @Relation(
            parentColumn = "id",
            entityColumn = "author_id"
    )
    public List<Book> books;
}

