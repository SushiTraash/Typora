import com.ssm.pojo.Books;
import com.ssm.service.BookService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test1(){
        BookService bookService = (BookService) new ClassPathXmlApplicationContext("applicationContext.xml").getBean("BookServiceImpl");
        for (Books book : bookService.selectAllBooks()) {
            System.out.println(book);
        }
    }
}
