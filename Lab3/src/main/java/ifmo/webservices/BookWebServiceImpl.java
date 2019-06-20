package ifmo.webservices;

import ifmo.webservices.errors.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "BookWebService", serviceName = "BookService")
public class BookWebServiceImpl implements BookWebService {

    @WebMethod(operationName = "getAllBooks")
    public List<Book> getAllBooks() throws DatabaseException {
        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            return dao.getAllBooks();
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), BookServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "getBooks")
    public List<Book> getBooks(@WebParam(name = "conditions") List<BookFieldValue> conditions)
            throws DatabaseException {
        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            return dao.getBooksByFields(conditions);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), BookServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "addBook")
    public int addBook(@WebParam(name = "book") Book book)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException, DatabaseException {

        checkName(book.getName());
        checkAuthor(book.getAuthor());
        checkPages(book.getPages());
        checkYear(book.getYear());

        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            return dao.addBook(book);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), BookServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "modifyBook")
    public boolean modifyBook(@WebParam(name = "id") int id, @WebParam(name = "newValues") List<BookFieldValue> newValues)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException,
            BookNotFoundException, DatabaseException {

        for (BookFieldValue fieldValue : newValues) {
            switch (fieldValue.getField()) {
                case NAME:
                    checkName(fieldValue.getValue().toString());
                    break;
                case AUTHOR:
                    checkAuthor(fieldValue.getValue().toString());
                    break;
                case PAGES:
                    checkPages(fieldValue.getValue().toString());
                    break;
                case YEAR:
                    checkYear(fieldValue.getValue().toString());
                    break;
            }
        }

        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            checkExists(dao, id);
            return dao.modifyBook(id, newValues);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), BookServiceFault.defaultInstance());
        }
    }

    @WebMethod(operationName = "deleteBook")
    public boolean deleteBook(@WebParam(name = "id") int id) throws BookNotFoundException, DatabaseException {
        OracleSQLDAO dao = new OracleSQLDAO();
        try {
            checkExists(dao, id);
            return dao.deleteBook(id);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage(), BookServiceFault.defaultInstance());
        }
    }

    protected void checkExists(OracleSQLDAO dao, int id) throws BookNotFoundException, SQLException {
        if (dao.getBooksByFields(new ArrayList<BookFieldValue>() {{
            add(new BookFieldValue(Field.ID, id));
        }}).size() == 0) {
            throw new BookNotFoundException("Book with such id is not found",
                    BookServiceFault.defaultInstance());
        }
    }

    protected void checkName(String name) throws IllegalNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Book name is not specified",
                    BookServiceFault.defaultInstance());
        }
    }

    protected void checkAuthor(String author) throws IllegalAuthorException {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalAuthorException("Book author is not specified",
                    BookServiceFault.defaultInstance());
        }
    }

    protected void checkPages(String pages) throws IllegalPagesException {
        int pagesInt = -1;
        try {
           pagesInt = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            throw new IllegalPagesException("Pages should be number",
                    BookServiceFault.defaultInstance());
        }
       checkPages(pagesInt);
    }

    protected void checkYear(String year) throws IllegalYearException {
        int yearInt = -1;
        try {
            yearInt = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            throw new IllegalYearException("Years should be number",
                    BookServiceFault.defaultInstance());
        }
        checkYear(yearInt);
    }

    protected void checkPages(int pages) throws IllegalPagesException {
        if (pages <= 0) {
            throw new IllegalPagesException("Pages should be greater than zero",
                    BookServiceFault.defaultInstance());
        }
    }

    protected void checkYear(int year) throws IllegalYearException {
        if (year <= 0) {
            throw new IllegalYearException("Year should be greater than zero",
                    BookServiceFault.defaultInstance());
        }
    }
}