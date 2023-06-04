package com.moc.booktracker.service;

import android.content.Context;

import com.moc.booktracker.model.AppDatabase;
import com.moc.booktracker.model.dao.BookDao;
import com.moc.booktracker.model.entity.Book;

import java.util.List;

public class BookService {

    private final BookDao bookDao;
    public BookService(AppDatabase database) {
        bookDao = database.bookDao();
    }

    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    public List<Book> getBooks(long authorId) {
        return bookDao.getBooksOfAuthor(authorId);
    }

    public Book getBook(long bookId) {
        return bookDao.getBook(bookId);
    }

    public Book createBook(Book book) {
        long id = bookDao.createBook(book);
        return bookDao.getBook(id);
    }

    public Book editBook(Book book) {
        bookDao.updateBook(book);
        return book;
    }

    public void deleteBook(Book book) throws IllegalArgumentException {
        if (bookDao.deleteBook(book) != 1) {
            throw new IllegalArgumentException("Ошибка!");
        }
    }


}
