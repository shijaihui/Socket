
package server;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ServiceImplementService", targetNamespace = "http://server/", wsdlLocation = "http://127.0.0.1:8888/WebServices/Service?wsdl")
public class ServiceImplementService
    extends Service
{

    private final static URL SERVICEIMPLEMENTSERVICE_WSDL_LOCATION;
    private final static WebServiceException SERVICEIMPLEMENTSERVICE_EXCEPTION;
    private final static QName SERVICEIMPLEMENTSERVICE_QNAME = new QName("http://server/", "ServiceImplementService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://127.0.0.1:8888/WebServices/Service?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SERVICEIMPLEMENTSERVICE_WSDL_LOCATION = url;
        SERVICEIMPLEMENTSERVICE_EXCEPTION = e;
    }

    public ServiceImplementService() {
        super(__getWsdlLocation(), SERVICEIMPLEMENTSERVICE_QNAME);
    }

    public ServiceImplementService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SERVICEIMPLEMENTSERVICE_QNAME, features);
    }

    public ServiceImplementService(URL wsdlLocation) {
        super(wsdlLocation, SERVICEIMPLEMENTSERVICE_QNAME);
    }

    public ServiceImplementService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVICEIMPLEMENTSERVICE_QNAME, features);
    }

    public ServiceImplementService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServiceImplementService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ServiceImplement
     */
    @WebEndpoint(name = "ServiceImplementPort")
    public ServiceImplement getServiceImplementPort() {
        return super.getPort(new QName("http://server/", "ServiceImplementPort"), ServiceImplement.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServiceImplement
     */
    @WebEndpoint(name = "ServiceImplementPort")
    public ServiceImplement getServiceImplementPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://server/", "ServiceImplementPort"), ServiceImplement.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SERVICEIMPLEMENTSERVICE_EXCEPTION!= null) {
            throw SERVICEIMPLEMENTSERVICE_EXCEPTION;
        }
        return SERVICEIMPLEMENTSERVICE_WSDL_LOCATION;
    }

}
