package ifmo.webservices.j2ee;

import ifmo.webservices.Book;
import ifmo.webservices.BookFieldValue;
import ifmo.webservices.Field;
import ifmo.webservices.OracleSQLDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/books")
@Produces({MediaType.APPLICATION_JSON})
public class BookJ2EEResource {
    @GET
    public List<Book> getBooks(
            @QueryParam("author") String author,
            @QueryParam("id") int id,
            @QueryParam("name") String name,
            @QueryParam("pages") int pages,
            @QueryParam("publishing") String publishing,
            @QueryParam("year") int year) {

        OracleSQLDAO dao = new OracleSQLDAO(getConnection());
        List<BookFieldValue> conditions = new ArrayList<>();

        if (author != null) conditions.add(new BookFieldValue(Field.AUTHOR, author));
        if (id != 0) conditions.add(new BookFieldValue(Field.ID, id));
        if (name != null) conditions.add(new BookFieldValue(Field.NAME, name));
        if (pages != 0) conditions.add(new BookFieldValue(Field.PAGES, pages));
        if (publishing != null) conditions.add(new BookFieldValue(Field.PUBLISHING, publishing));
        if (year != 0) conditions.add(new BookFieldValue(Field.YEAR, year));

        return dao.getBooksByFields(conditions);
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource)ctx.lookup("ifmo-oracle");
            result = dataSource.getConnection();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(BookJ2EEResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
