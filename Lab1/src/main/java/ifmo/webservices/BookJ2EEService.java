package ifmo.webservices;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebService(name = "BookWebService", serviceName = "BookService")
public class BookJ2EEService implements BookWebService {
    @WebMethod(operationName = "getAllBooks")
    public List<Book> getAllBooks() {
        OracleSQLDAO dao = new OracleSQLDAO(getConnection());
        List<Book> books = dao.getAllBooks();
        return books;
    }

    @WebMethod(operationName = "getBooks")
    public List<Book> getBooks(@WebParam(name = "conditions") List<BookFieldValue> conditions) {
        OracleSQLDAO dao = new OracleSQLDAO(getConnection());
        return dao.getBooksByFields(conditions);
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource)ctx.lookup("ifmo-oracle");
            result = dataSource.getConnection();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(BookJ2EEService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
