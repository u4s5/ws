package ifmo.webservices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleSQLDAO {
    private Connection connection;

    public OracleSQLDAO(Connection connection) {
        this.connection = connection;
    }

    private List<Book> getBooks(String query) {
        List<Book> books = new ArrayList<Book>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt(Field.fromValue("id").toString());
                String name = rs.getString(Field.fromValue("name").toString());
                String publishing = rs.getString(Field.fromValue("publishing").toString());
                String author = rs.getString(Field.fromValue("author").toString());
                int year = rs.getInt(Field.fromValue("year").toString());
                int pages = rs.getInt(Field.fromValue("pages").toString());

                Book book = new Book(author, id, name, pages, publishing, year);
                books.add(book);
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OracleSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public List<Book> getAllBooks() {
        return getBooks("select * from books");
    }

    public List<Book> getBooksByFields(List<BookFieldValue> bookRequests) {
        if(bookRequests.size() == 0) return getAllBooks();

        StringBuilder query = new StringBuilder("select * from books where ");

        for (BookFieldValue bookRequest : bookRequests) {
            String equalExpression = String.format("%s = '%s'", bookRequest.getField(), bookRequest.getValue());
            query.append(equalExpression);

            if(!bookRequest.equals(bookRequests.get(bookRequests.size() - 1))) {
                query.append(" and ");
            }
        }
        return getBooks(query.toString());
    }
}