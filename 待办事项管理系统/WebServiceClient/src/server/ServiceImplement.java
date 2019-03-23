
package server;

import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ServiceImplement", targetNamespace = "http://server/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServiceImplement {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "register", targetNamespace = "http://server/", className = "server.Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://server/", className = "server.RegisterResponse")
    @Action(input = "http://server/ServiceImplement/registerRequest", output = "http://server/ServiceImplement/registerResponse")
    public boolean register(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getError", targetNamespace = "http://server/", className = "server.GetError")
    @ResponseWrapper(localName = "getErrorResponse", targetNamespace = "http://server/", className = "server.GetErrorResponse")
    @Action(input = "http://server/ServiceImplement/getErrorRequest", output = "http://server/ServiceImplement/getErrorResponse")
    public int getError();

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "clearMatters", targetNamespace = "http://server/", className = "server.ClearMatters")
    @ResponseWrapper(localName = "clearMattersResponse", targetNamespace = "http://server/", className = "server.ClearMattersResponse")
    @Action(input = "http://server/ServiceImplement/clearMattersRequest", output = "http://server/ServiceImplement/clearMattersResponse")
    public boolean clearMatters(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteMatter", targetNamespace = "http://server/", className = "server.DeleteMatter")
    @ResponseWrapper(localName = "deleteMatterResponse", targetNamespace = "http://server/", className = "server.DeleteMatterResponse")
    @Action(input = "http://server/ServiceImplement/deleteMatterRequest", output = "http://server/ServiceImplement/deleteMatterResponse")
    public boolean deleteMatter(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        User arg1);

    /**
     * 
     * @param arg2
     * @param end
     * @param start
     * @return
     *     returns java.util.List<server.Matter>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryMatters", targetNamespace = "http://server/", className = "server.QueryMatters")
    @ResponseWrapper(localName = "queryMattersResponse", targetNamespace = "http://server/", className = "server.QueryMattersResponse")
    @Action(input = "http://server/ServiceImplement/queryMattersRequest", output = "http://server/ServiceImplement/queryMattersResponse")
    public List<Matter> queryMatters(
        @WebParam(name = "arg0", targetNamespace = "")
        Date start,
        @WebParam(name = "arg1", targetNamespace = "")
        Date end,
        @WebParam(name = "arg2", targetNamespace = "")
        User arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isRegister", targetNamespace = "http://server/", className = "server.IsRegister")
    @ResponseWrapper(localName = "isRegisterResponse", targetNamespace = "http://server/", className = "server.IsRegisterResponse")
    @Action(input = "http://server/ServiceImplement/isRegisterRequest", output = "http://server/ServiceImplement/isRegisterResponse")
    public boolean isRegister(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0);

    /**
     * 
     * @param arg3
     * @param date2
     * @param date
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addMatter", targetNamespace = "http://server/", className = "server.AddMatter")
    @ResponseWrapper(localName = "addMatterResponse", targetNamespace = "http://server/", className = "server.AddMatterResponse")
    @Action(input = "http://server/ServiceImplement/addMatterRequest", output = "http://server/ServiceImplement/addMatterResponse")
    public boolean addMatter(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Date date,
        @WebParam(name = "arg2", targetNamespace = "")
        Date date2,
        @WebParam(name = "arg3", targetNamespace = "")
        User arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "checkUser", targetNamespace = "http://server/", className = "server.CheckUser")
    @ResponseWrapper(localName = "checkUserResponse", targetNamespace = "http://server/", className = "server.CheckUserResponse")
    @Action(input = "http://server/ServiceImplement/checkUserRequest", output = "http://server/ServiceImplement/checkUserResponse")
    public int checkUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}
