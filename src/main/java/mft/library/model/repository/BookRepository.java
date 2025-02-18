package mft.library.model.repository;

import mft.library.model.entity.Book;

import java.util.Collections;
import java.util.List;

public class BookRepository implements Repository<Book, Long>{

    @Override
    public void save(Book book) throws Exception {

    }

    @Override
    public void edit(Book book) throws Exception {

    }

    @Override
    public void remove(Long id) throws Exception {

    }

    @Override
    public List<Book> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Book findById(Long id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
