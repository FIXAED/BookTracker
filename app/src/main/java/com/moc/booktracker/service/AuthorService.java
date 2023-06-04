package com.moc.booktracker.service;

import com.moc.booktracker.model.AppDatabase;
import com.moc.booktracker.model.dao.AuthorDao;
import com.moc.booktracker.model.entity.Author;

import java.util.List;

public class AuthorService {

    private final AuthorDao authorDao;
    public AuthorService(AppDatabase database) {authorDao = database.authorDao();
    }

    public List<Author> getAuthors(){return authorDao.getAuthors();
    }

    public Author getAuthor(long id) {
        return authorDao.getAuthor(id);
    }


    public Author createAuthor(Author author) {
        long id = authorDao.insertAuthor(author);
        return authorDao.getAuthor(id);
    }

    public Author editAuthor(Author author) {
        authorDao.updateAuthor(author);
        return author;
    }

    public void deleteAuthor(Author author) throws IllegalArgumentException {
        if (authorDao.deleteAuthor(author) != 1) {
            throw new IllegalArgumentException("Ошибка!");
        }
    }
}
