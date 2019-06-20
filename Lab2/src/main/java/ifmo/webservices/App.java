package ifmo.webservices;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8081/BookService";
        Endpoint.publish(url, new BookWebServiceImpl());
    }
}
