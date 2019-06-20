package ifmo.webservices;

import java.util.ArrayList;
import java.util.List;
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
            @QueryParam("year") int year) {

        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        List<BookFieldValue> conditions = new ArrayList<>();

        if (author != null) conditions.add(new BookFieldValue(Field.AUTHOR, author));
        if (id != 0) conditions.add(new BookFieldValue(Field.ID, id));
        if (name != null) conditions.add(new BookFieldValue(Field.NAME, name));
        if (pages != 0) conditions.add(new BookFieldValue(Field.PAGES, pages));
        if (publishing != null) conditions.add(new BookFieldValue(Field.PUBLISHING, publishing));
        if (year != 0) conditions.add(new BookFieldValue(Field.YEAR, year));

        return dao.getBooksByFields(conditions);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public int addBook(Book book) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        return dao.addBook(book);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    public boolean modifyBook(Book book) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());

        List<BookFieldValue> newValues = new ArrayList<>();

        if(book.getAuthor() != null) newValues.add(new BookFieldValue(Field.AUTHOR, book.getAuthor()));
        if(book.getName() != null) newValues.add(new BookFieldValue(Field.NAME, book.getName()));
        if(book.getPublishing() != null) newValues.add(new BookFieldValue(Field.PUBLISHING, book.getPublishing()));
        if(book.getPages() > 0) newValues.add(new BookFieldValue(Field.PAGES, book.getPages()));
        if(book.getYear() > 0) newValues.add(new BookFieldValue(Field.YEAR, book.getYear()));

        return dao.modifyBook(book.id, newValues);
    }

    @DELETE
    public boolean deleteBook(@QueryParam("id") int id) {
        OracleSQLDAO dao = new OracleSQLDAO(ConnectionUtil.getConnection());
        return dao.deleteBook(id);
    }
}
