package com.ssm.dao;

import com.ssm.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BooksMapper {
    //增加
    public int addBook(Books table);

    //删除
    int deleteBookById(@Param("bookId") int id);

    //更新
    int updateBook(Books table);

    //查询
    Books selectBookById(@Param("bookId") int id);

    List<Books> selectAllBooks();
}
