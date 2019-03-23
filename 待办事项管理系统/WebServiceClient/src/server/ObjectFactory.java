
package server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the server package. 
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

    private final static QName _AddMatterResponse_QNAME = new QName("http://server/", "addMatterResponse");
    private final static QName _DeleteMatter_QNAME = new QName("http://server/", "deleteMatter");
    private final static QName _AddMatter_QNAME = new QName("http://server/", "addMatter");
    private final static QName _DeleteMatterResponse_QNAME = new QName("http://server/", "deleteMatterResponse");
    private final static QName _QueryMatters_QNAME = new QName("http://server/", "queryMatters");
    private final static QName _IsRegister_QNAME = new QName("http://server/", "isRegister");
    private final static QName _ClearMatters_QNAME = new QName("http://server/", "clearMatters");
    private final static QName _Register_QNAME = new QName("http://server/", "register");
    private final static QName _QueryMattersResponse_QNAME = new QName("http://server/", "queryMattersResponse");
    private final static QName _CheckUserResponse_QNAME = new QName("http://server/", "checkUserResponse");
    private final static QName _ClearMattersResponse_QNAME = new QName("http://server/", "clearMattersResponse");
    private final static QName _RegisterResponse_QNAME = new QName("http://server/", "registerResponse");
    private final static QName _GetErrorResponse_QNAME = new QName("http://server/", "getErrorResponse");
    private final static QName _IsRegisterResponse_QNAME = new QName("http://server/", "isRegisterResponse");
    private final static QName _GetError_QNAME = new QName("http://server/", "getError");
    private final static QName _CheckUser_QNAME = new QName("http://server/", "checkUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteMatterResponse }
     * 
     */
    public DeleteMatterResponse createDeleteMatterResponse() {
        return new DeleteMatterResponse();
    }

    /**
     * Create an instance of {@link QueryMatters }
     * 
     */
    public QueryMatters createQueryMatters() {
        return new QueryMatters();
    }

    /**
     * Create an instance of {@link DeleteMatter }
     * 
     */
    public DeleteMatter createDeleteMatter() {
        return new DeleteMatter();
    }

    /**
     * Create an instance of {@link AddMatter }
     * 
     */
    public AddMatter createAddMatter() {
        return new AddMatter();
    }

    /**
     * Create an instance of {@link AddMatterResponse }
     * 
     */
    public AddMatterResponse createAddMatterResponse() {
        return new AddMatterResponse();
    }

    /**
     * Create an instance of {@link GetError }
     * 
     */
    public GetError createGetError() {
        return new GetError();
    }

    /**
     * Create an instance of {@link CheckUser }
     * 
     */
    public CheckUser createCheckUser() {
        return new CheckUser();
    }

    /**
     * Create an instance of {@link GetErrorResponse }
     * 
     */
    public GetErrorResponse createGetErrorResponse() {
        return new GetErrorResponse();
    }

    /**
     * Create an instance of {@link IsRegisterResponse }
     * 
     */
    public IsRegisterResponse createIsRegisterResponse() {
        return new IsRegisterResponse();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link QueryMattersResponse }
     * 
     */
    public QueryMattersResponse createQueryMattersResponse() {
        return new QueryMattersResponse();
    }

    /**
     * Create an instance of {@link CheckUserResponse }
     * 
     */
    public CheckUserResponse createCheckUserResponse() {
        return new CheckUserResponse();
    }

    /**
     * Create an instance of {@link ClearMattersResponse }
     * 
     */
    public ClearMattersResponse createClearMattersResponse() {
        return new ClearMattersResponse();
    }

    /**
     * Create an instance of {@link IsRegister }
     * 
     */
    public IsRegister createIsRegister() {
        return new IsRegister();
    }

    /**
     * Create an instance of {@link ClearMatters }
     * 
     */
    public ClearMatters createClearMatters() {
        return new ClearMatters();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link Matter }
     * 
     */
    public Matter createMatter() {
        return new Matter();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMatterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "addMatterResponse")
    public JAXBElement<AddMatterResponse> createAddMatterResponse(AddMatterResponse value) {
        return new JAXBElement<AddMatterResponse>(_AddMatterResponse_QNAME, AddMatterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMatter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "deleteMatter")
    public JAXBElement<DeleteMatter> createDeleteMatter(DeleteMatter value) {
        return new JAXBElement<DeleteMatter>(_DeleteMatter_QNAME, DeleteMatter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMatter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "addMatter")
    public JAXBElement<AddMatter> createAddMatter(AddMatter value) {
        return new JAXBElement<AddMatter>(_AddMatter_QNAME, AddMatter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMatterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "deleteMatterResponse")
    public JAXBElement<DeleteMatterResponse> createDeleteMatterResponse(DeleteMatterResponse value) {
        return new JAXBElement<DeleteMatterResponse>(_DeleteMatterResponse_QNAME, DeleteMatterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryMatters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "queryMatters")
    public JAXBElement<QueryMatters> createQueryMatters(QueryMatters value) {
        return new JAXBElement<QueryMatters>(_QueryMatters_QNAME, QueryMatters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsRegister }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "isRegister")
    public JAXBElement<IsRegister> createIsRegister(IsRegister value) {
        return new JAXBElement<IsRegister>(_IsRegister_QNAME, IsRegister.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearMatters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "clearMatters")
    public JAXBElement<ClearMatters> createClearMatters(ClearMatters value) {
        return new JAXBElement<ClearMatters>(_ClearMatters_QNAME, ClearMatters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryMattersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "queryMattersResponse")
    public JAXBElement<QueryMattersResponse> createQueryMattersResponse(QueryMattersResponse value) {
        return new JAXBElement<QueryMattersResponse>(_QueryMattersResponse_QNAME, QueryMattersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "checkUserResponse")
    public JAXBElement<CheckUserResponse> createCheckUserResponse(CheckUserResponse value) {
        return new JAXBElement<CheckUserResponse>(_CheckUserResponse_QNAME, CheckUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearMattersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "clearMattersResponse")
    public JAXBElement<ClearMattersResponse> createClearMattersResponse(ClearMattersResponse value) {
        return new JAXBElement<ClearMattersResponse>(_ClearMattersResponse_QNAME, ClearMattersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetErrorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "getErrorResponse")
    public JAXBElement<GetErrorResponse> createGetErrorResponse(GetErrorResponse value) {
        return new JAXBElement<GetErrorResponse>(_GetErrorResponse_QNAME, GetErrorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsRegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "isRegisterResponse")
    public JAXBElement<IsRegisterResponse> createIsRegisterResponse(IsRegisterResponse value) {
        return new JAXBElement<IsRegisterResponse>(_IsRegisterResponse_QNAME, IsRegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "getError")
    public JAXBElement<GetError> createGetError(GetError value) {
        return new JAXBElement<GetError>(_GetError_QNAME, GetError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "checkUser")
    public JAXBElement<CheckUser> createCheckUser(CheckUser value) {
        return new JAXBElement<CheckUser>(_CheckUser_QNAME, CheckUser.class, null, value);
    }

}
