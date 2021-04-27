package com.ssm.service;

import com.ssm.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService {
    //增加
    public int addBook(Books table);

    //删除
    int deleteBookById(int id);

    //更新
    int updateBook(Books table);

    //查询
    Books selectBookById(int id);

    List<Books> selectAllBooks();
    Books searchBookByName(String name);
}
