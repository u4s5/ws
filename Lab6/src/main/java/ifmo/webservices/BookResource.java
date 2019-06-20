package ifmo.webservices;

import ifmo.webservices.errors.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/books")
@Produces({MediaType.APPLICATION_JSON})
public class BookResource {
    @GET
    public List<Book> getBooks(
            @QueryParam("author") String author,
            @QueryParam("id") int id,
            @QueryParam("name") String name,
            @QueryParam("pages") int pages,
            @QueryParam("publishing") String publishing,
            @QueryParam("year") int year) throws DatabaseException {

        try {
            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            List<BookFieldValue> conditions = new ArrayList<>();

            if (author != null) conditions.add(new BookFieldValue(Field.AUTHOR, author));
            if (id != 0) conditions.add(new BookFieldValue(Field.ID, id));
            if (name != null) conditions.add(new BookFieldValue(Field.NAME, name));
            if (pages != 0) conditions.add(new BookFieldValue(Field.PAGES, pages));
            if (publishing != null) conditions.add(new BookFieldValue(Field.PUBLISHING, publishing));
            if (year != 0) conditions.add(new BookFieldValue(Field.YEAR, year));

            return dao.getBooksByFields(conditions);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public int addBook(Book book)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException, DatabaseException {
        try {
            checkName(book.getName());
            checkAuthor(book.getAuthor());
            checkPages(book.getPages());
            checkYear(book.getYear());

            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            return dao.addBook(book);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean modifyBook(Book book)
            throws IllegalNameException,
            IllegalAuthorException,
            IllegalPagesException,
            IllegalYearException,
            BookNotFoundException, DatabaseException {

        try {
            checkName(book.getName());
            checkAuthor(book.getAuthor());
            checkPages(book.getPages());
            checkYear(book.getYear());

            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            checkExists(dao, book.getId());

            List<BookFieldValue> newValues = new ArrayList<>();

            if (book.getAuthor() != null) newValues.add(new BookFieldValue(Field.AUTHOR, book.getAuthor()));
            if (book.getName() != null) newValues.add(new BookFieldValue(Field.NAME, book.getName()));
            if (book.getPublishing() != null) newValues.add(new BookFieldValue(Field.PUBLISHING, book.getPublishing()));
            if (book.getPages() > 0) newValues.add(new BookFieldValue(Field.PAGES, book.getPages()));
            if (book.getYear() > 0) newValues.add(new BookFieldValue(Field.YEAR, book.getYear()));

            return dao.modifyBook(book.id, newValues);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @DELETE
    public boolean deleteBook(@QueryParam("id") int id) throws BookNotFoundException, DatabaseException {
        try {
            OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
            checkExists(dao, id);
            return dao.deleteBook(id);
        } catch (SQLException e) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(e.getMessage());
        }
    }

    protected void checkExists(OracleSQLDAO dao, final int id) throws BookNotFoundException, SQLException {
        if (dao.getBooksByFields(new ArrayList<BookFieldValue>() {{
            add(new BookFieldValue(Field.ID, id));
        }}).size() == 0) {
            throw new BookNotFoundException("Book with such id is not found");
        }
    }

    protected void checkName(String name) throws IllegalNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Book name is not specified");
        }
    }

    protected void checkAuthor(String author) throws IllegalAuthorException {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalAuthorException("Book author is not specified");
        }
    }

    protected void checkPages(int pages) throws IllegalPagesException {
        if (pages <= 0) {
            throw new IllegalPagesException("Pages should be greater than zero");
        }
    }

    protected void checkYear(int year) throws IllegalYearException {
        if (year <= 0) {
            throw new IllegalYearException("Year should be greater than zero");
        }
    }
}
