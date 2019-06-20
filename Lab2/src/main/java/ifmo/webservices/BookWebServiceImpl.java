package ifmo.webservices;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "BookWebService", serviceName = "BookService")
public class BookWebServiceImpl implements BookWebService {

    @WebMethod(operationName = "getAllBooks")
    public List<Book> getAllBooks() {
        OracleSQLDAO dao = new OracleSQLDAO();
        List<Book> books = dao.getAllBooks();
        return books;
    }

    @WebMethod(operationName = "getBooks")
    public List<Book> getBooks(@WebParam(name = "conditions") List<BookFieldValue> conditions) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.getBooksByFields(conditions);
    }

    @WebMethod(operationName = "addBook")
    public int addBook(@WebParam(name = "book") Book book) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.addBook(book);
    }

    @WebMethod(operationName = "modifyBook")
    public boolean modifyBook(@WebParam(name = "id") int id, @WebParam(name = "newValues") List<BookFieldValue> newValues) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.modifyBook(id, newValues);
    }

    @WebMethod(operationName = "deleteBook")
    public boolean deleteBook(@WebParam(name = "id") int id) {
        OracleSQLDAO dao = new OracleSQLDAO();
        return dao.deleteBook(id);
    }
}