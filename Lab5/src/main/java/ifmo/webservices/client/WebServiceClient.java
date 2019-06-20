package ifmo.webservices.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import ifmo.webservices.Book;
import ifmo.webservices.BookFieldValue;
import ifmo.webservices.Field;
import com.sun.jersey.api.client.ClientResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum MenuOption {AddField, Add, Delete, Modify, Print, Clear, Find, Exit}

public class WebServiceClient {

    private static final String standaloneUrl = "http://localhost:8081/rest/books";

    private Client client;
    private String url;

    private List<BookFieldValue> conditions = new ArrayList<>();

    public WebServiceClient(Client client, String url) {
        this.client = client;
        this.url = url;
    }

    public static void main(String[] args) {
        try {
            WebServiceClient client = new WebServiceClient(Client.create(), standaloneUrl);
            client.startListening();

        } catch (WebServiceException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void startListening() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    printMenu();
                    processOption(in);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }

        } catch (MalformedURLException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("------------Menu------------");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%2d. %s\n", i + 1, getOptionText(options[i]));
        }
    }

    private int readIntValue(BufferedReader in) throws IOException {
        int option = -1;

        String input = in.readLine();
        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return option;
        }

        return option;
    }

    private void processOption(BufferedReader in) throws IOException {
        int option = readIntValue(in);

        if (option < 1 || option > MenuOption.values().length) {
            System.err.println("Wrong option");
            return;
        }

        MenuOption menuOption = MenuOption.values()[option - 1];

        switch (menuOption) {
            case AddField:
                addCondition(in);
                break;
            case Add:
                add(in);
                break;
            case Delete:
                delete(in);
                break;
            case Modify:
                modify(in);
                break;
            case Find:
                findResults();
                break;
            case Print:
                printConditions();
                break;
            case Clear:
                clearConditions();
                break;
            case Exit:
                exit();
                break;
        }
    }

    private String getOptionText(MenuOption menuOption) {
        switch (menuOption) {
            case AddField:
                return "Add search condition";
            case Add:
                return "Add new book";
            case Delete:
                return "Delete book";
            case Modify:
                return "Modify book";
            case Find:
                return "Find results";
            case Print:
                return "Print saved conditions";
            case Clear:
                return "Clear saved conditions";
            case Exit:
                return "Exit";
            default:
                return "Option not supported";
        }
    }

    private void addCondition(BufferedReader in) throws IOException {
        System.out.println("Choose field:");

        Field[] fields = Field.values();
        for (int i = 0; i < fields.length; i++) {
            System.out.printf("%2d. %s\n", i + 1, fields[i]);
        }

        int field = readIntValue(in);

        if (field < 1 || field > fields.length) {
            System.err.println("Wrong option");
            return;
        }

        System.out.println("Print expected field value:");
        String value = in.readLine();

        BookFieldValue condition = new BookFieldValue(fields[field - 1], value);
        this.conditions.add(condition);

        System.out.println("Condition saved: " + condition);
    }

    private void add(BufferedReader in) {
        try {
            WebResource webResource = client.resource(this.url);

            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, inputFields(in));

            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Request failed");
            }

            GenericType<Integer> type = new GenericType<Integer>() {};
            int id = response.getEntity(type);
            System.out.println("Book added: id = " + id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private Map<String,Object> inputFields (BufferedReader in) throws IOException {
        Field[] fields = Field.values();
        Map<String,Object> body = new HashMap<>();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i] != Field.ID) {
                System.out.printf("Print '%s' value (to skip press enter):\n", fields[i]);
                String value = in.readLine();

                if (!value.isEmpty()) {
                    body.put(fields[i].toString(), value);
                }
            }
        }

        return body;
    }

    private void delete(BufferedReader in) {
        try {
            int id = findBookById(in);
            if (id < 0) {
                return;
            }

            WebResource webResource = client.resource(this.url);
            webResource = webResource.queryParam("id", String.valueOf(id));

            ClientResponse response = webResource.delete(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Request failed");
            }

            GenericType<Boolean> type = new GenericType<Boolean>() {};
            boolean success = response.getEntity(type);
            if (success) {
                System.out.println("Book deleted");
            } else {
                System.out.println("Book deletion failed");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void modify(BufferedReader in) {
        try {
            int id = findBookById(in);
            if (id < 0) {
                return;
            }

            WebResource webResource = client.resource(this.url);

            Map<String, Object> body = inputFields(in);
            body.put("id", id);

            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON)
                    .put(ClientResponse.class, body);

            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Request failed");
            }

            GenericType<Boolean> type = new GenericType<Boolean>() {};
            boolean success = response.getEntity(type);
            if (success) {
                System.out.println("Book modified");
            } else {
                System.out.println("Book modification failed");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private int findBookById(BufferedReader in) throws IOException {
        System.out.println("Print id:");
        int id = readIntValue(in);

        if (id < 0) {
            System.err.println("Wrong id value");
            return -1;
        }

        WebResource webResource = client.resource(this.url);
        webResource = webResource.queryParam("id", String.valueOf(id));

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }

        GenericType<List<Book>> type = new GenericType<List<Book>>() {};
        List<Book> books = response.getEntity(type);

        if (books.size() == 0) {
            System.err.println("Book with such id was not found");
            return -1;
        } else {
            System.out.println("Book found: " + books.get(0));
            return id;
        }
    }

    private void findResults() {
        WebResource webResource = client.resource(this.url);
        for (BookFieldValue condition : conditions) {
            webResource = webResource.queryParam(
                    condition.getField().toString(),
                    condition.getValue().toString());
        }

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }

        GenericType<List<Book>> type = new GenericType<List<Book>>() {};
        printBooks(response.getEntity(type));
    }

    private void printConditions() {
        if (this.conditions.size() == 0) {
            System.out.println("No conditions saved");
            return;
        }

        System.out.println("Saved conditions:");

        for (BookFieldValue condition : this.conditions) {
            System.out.println(condition);
        }
    }

    private void clearConditions() {
        this.conditions.clear();
        System.out.println("Saved conditions cleared");
    }

    private void exit() {
        System.exit(0);
    }

    private void printBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("Total books: " + books.size());
    }
}