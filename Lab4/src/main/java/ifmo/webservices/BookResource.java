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
}
