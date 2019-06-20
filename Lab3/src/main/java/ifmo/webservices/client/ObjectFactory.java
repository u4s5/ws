
package ifmo.webservices.client;

import ifmo.webservices.Book;
import ifmo.webservices.BookFieldValue;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the ifmo.webservices package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddBookResponse_QNAME = new QName("http://webservices.ifmo/", "addBookResponse");
    private final static QName _DeleteBookResponse_QNAME = new QName("http://webservices.ifmo/", "deleteBookResponse");
    private final static QName _GetBooks_QNAME = new QName("http://webservices.ifmo/", "getBooks");
    private final static QName _DeleteBook_QNAME = new QName("http://webservices.ifmo/", "deleteBook");
    private final static QName _GetAllBooks_QNAME = new QName("http://webservices.ifmo/", "getAllBooks");
    private final static QName _ModifyBookResponse_QNAME = new QName("http://webservices.ifmo/", "modifyBookResponse");
    private final static QName _GetBooksResponse_QNAME = new QName("http://webservices.ifmo/", "getBooksResponse");
    private final static QName _ModifyBook_QNAME = new QName("http://webservices.ifmo/", "modifyBook");
    private final static QName _AddBook_QNAME = new QName("http://webservices.ifmo/", "addBook");
    private final static QName _GetAllBooksResponse_QNAME = new QName("http://webservices.ifmo/", "getAllBooksResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ifmo.webservices
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ModifyBookResponse }
     *
     */
    public ModifyBookResponse createModifyBookResponse() {
        return new ModifyBookResponse();
    }

    /**
     * Create an instance of {@link DeleteBook }
     *
     */
    public DeleteBook createDeleteBook() {
        return new DeleteBook();
    }

    /**
     * Create an instance of {@link GetAllBooks }
     *
     */
    public GetAllBooks createGetAllBooks() {
        return new GetAllBooks();
    }

    /**
     * Create an instance of {@link GetBooks }
     *
     */
    public GetBooks createGetBooks() {
        return new GetBooks();
    }

    /**
     * Create an instance of {@link DeleteBookResponse }
     *
     */
    public DeleteBookResponse createDeleteBookResponse() {
        return new DeleteBookResponse();
    }

    /**
     * Create an instance of {@link AddBookResponse }
     *
     */
    public AddBookResponse createAddBookResponse() {
        return new AddBookResponse();
    }

    /**
     * Create an instance of {@link GetAllBooksResponse }
     *
     */
    public GetAllBooksResponse createGetAllBooksResponse() {
        return new GetAllBooksResponse();
    }

    /**
     * Create an instance of {@link AddBook }
     *
     */
    public AddBook createAddBook() {
        return new AddBook();
    }

    /**
     * Create an instance of {@link GetBooksResponse }
     *
     */
    public GetBooksResponse createGetBooksResponse() {
        return new GetBooksResponse();
    }

    /**
     * Create an instance of {@link ModifyBook }
     *
     */
    public ModifyBook createModifyBook() {
        return new ModifyBook();
    }

    /**
     * Create an instance of {@link Book }
     *
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link BookFieldValue }
     *
     */
    public BookFieldValue createBookCondition() {
        return new BookFieldValue();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBookResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "addBookResponse")
    public JAXBElement<AddBookResponse> createAddBookResponse(AddBookResponse value) {
        return new JAXBElement<AddBookResponse>(_AddBookResponse_QNAME, AddBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBookResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "deleteBookResponse")
    public JAXBElement<DeleteBookResponse> createDeleteBookResponse(DeleteBookResponse value) {
        return new JAXBElement<DeleteBookResponse>(_DeleteBookResponse_QNAME, DeleteBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBooks }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getBooks")
    public JAXBElement<GetBooks> createGetBooks(GetBooks value) {
        return new JAXBElement<GetBooks>(_GetBooks_QNAME, GetBooks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBook }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "deleteBook")
    public JAXBElement<DeleteBook> createDeleteBook(DeleteBook value) {
        return new JAXBElement<DeleteBook>(_DeleteBook_QNAME, DeleteBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllBooks }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getAllBooks")
    public JAXBElement<GetAllBooks> createGetAllBooks(GetAllBooks value) {
        return new JAXBElement<GetAllBooks>(_GetAllBooks_QNAME, GetAllBooks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyBookResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "modifyBookResponse")
    public JAXBElement<ModifyBookResponse> createModifyBookResponse(ModifyBookResponse value) {
        return new JAXBElement<ModifyBookResponse>(_ModifyBookResponse_QNAME, ModifyBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBooksResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getBooksResponse")
    public JAXBElement<GetBooksResponse> createGetBooksResponse(GetBooksResponse value) {
        return new JAXBElement<GetBooksResponse>(_GetBooksResponse_QNAME, GetBooksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyBook }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "modifyBook")
    public JAXBElement<ModifyBook> createModifyBook(ModifyBook value) {
        return new JAXBElement<ModifyBook>(_ModifyBook_QNAME, ModifyBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBook }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "addBook")
    public JAXBElement<AddBook> createAddBook(AddBook value) {
        return new JAXBElement<AddBook>(_AddBook_QNAME, AddBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllBooksResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservices.ifmo/", name = "getAllBooksResponse")
    public JAXBElement<GetAllBooksResponse> createGetAllBooksResponse(GetAllBooksResponse value) {
        return new JAXBElement<GetAllBooksResponse>(_GetAllBooksResponse_QNAME, GetAllBooksResponse.class, null, value);
    }

}
