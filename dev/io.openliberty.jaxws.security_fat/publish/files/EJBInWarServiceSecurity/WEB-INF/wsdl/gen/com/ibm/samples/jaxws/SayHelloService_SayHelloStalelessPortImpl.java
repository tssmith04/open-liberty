
package com.ibm.samples.jaxws;

import javax.jws.WebService;
import javax.xml.ws.BindingType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebService(portName = "SayHelloStalelessPort", serviceName = "SayHelloService", targetNamespace = "http://jaxws.samples.ibm.com", wsdlLocation = "file:/Users/tjjohnso/libertyGit/open-liberty/dev/io.openliberty.jaxws.security_fat/publish/files/EJBInWarServiceSecurity/WEB-INF/wsdl/SayHelloService.wsdl", endpointInterface = "com.ibm.samples.jaxws.SayHello")
@BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
public class SayHelloService_SayHelloStalelessPortImpl
    implements SayHello
{


    public SayHelloService_SayHelloStalelessPortImpl() {
    }

    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    public String sayHello(String name) {
        //replace with your impl here
        return null;
    }

}
