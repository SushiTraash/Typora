package com.ssm.service;

import com.ssm.dao.BooksMapper;
import com.ssm.pojo.Books;

import java.util.List;

public class BookServiceImpl implements BookService{

    //service调用Dao层
    private BooksMapper booksMapper;

    public void setBooksMapper(BooksMapper booksMapper) {
        this.booksMapper = booksMapper;
    }

    @Override
    public int addBook(Books table) {

        return booksMapper.addBook(table);
    }

    @Override
    public int deleteBookById(int id) {
        return booksMapper.deleteBookById(id);
    }

    @Override
    public int updateBook(Books table) {
        return booksMapper.updateBook(table);
    }

    @Override
    public Books selectBookById(int id) {
        return booksMapper.selectBookById(id);
    }

    @Override
    public List<Books> selectAllBooks() {
        return booksMapper.selectAllBooks();
    }

    @Override
    public Books searchBookByName(String name) {
        return booksMapper.searchBook(name);
    }
}
