
package com.aggregator;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

// web service 服务端接口

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 */

// targetNamespace: 指定从 Web Service 生成的 WSDL 和 XML 元素的 XML 名称空间。缺省值为从包含该 Web Service 的包名映射的名称空间。（字符串）

@WebService(name = "HandlingReportService", targetNamespace = "http://ws.handling.interfaces.dddsample.citerus.se/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface HandlingReportService {


    /**
     * @param arg0
     * @throws HandlingReportErrors_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "submitReport", targetNamespace = "http://ws.handling.interfaces.dddsample.citerus.se/", className = "com.aggregator.SubmitReport")
    @ResponseWrapper(localName = "submitReportResponse", targetNamespace = "http://ws.handling.interfaces.dddsample.citerus.se/", className = "com.aggregator.SubmitReportResponse")
    public void submitReport(
            @WebParam(name = "arg0", targetNamespace = "")
                    HandlingReport arg0)
            throws HandlingReportErrors_Exception
    ;

}