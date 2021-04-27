package com.ssm.controller;

import com.ssm.pojo.Books;
import com.ssm.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    //controller 层调用service层
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;
    @RequestMapping("/allBook")
    public String selectAllBook(Model model){
        List<Books> list = bookService.selectAllBooks();
        model.addAttribute("list",list);
        return "allBook";
    }
    @RequestMapping("/toAddPage")
    public String toAddPage(Model model){
        return "addBook";
    }
    @RequestMapping("/addNewBook")
    public String addBook(Books books){
        System.out.println("add:"+books);
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }
    @RequestMapping("/toUpdateBookPage")
    public String toUpdateBookPage(int id,Model model){
        Books books = bookService.selectBookById(id);
        model.addAttribute("books",books);//model 用于传递参数给下面  return的页面 updatePage

        return "updatePage";
    }
    @RequestMapping("/updateBook")
    public String updateBook(Books books){
        bookService.updateBook(books);
        return "redirect:/book/allBook";
    }

    //restful风格
    @RequestMapping("/deleteBook/{bid}")
    public String deleteBookREST(@PathVariable("bid") int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }
    @RequestMapping("/searchBook")
    public String searchBook(String bookName, Model model){

        List<Books> list = new ArrayList<>();
        list.add(bookService.searchBookByName(bookName));
        if(list.get(0) == null){
            list = bookService.selectAllBooks();
            model.addAttribute("error","没有这本书");

        }
        System.out.println(list.size());
        model.addAttribute("list",list);
        return "allBook";
    }
}
