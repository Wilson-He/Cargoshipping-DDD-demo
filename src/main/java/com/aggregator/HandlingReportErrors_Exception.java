
package com.aggregator;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 */
@WebFault(name = "HandlingReportErrors", targetNamespace = "http://ws.handling.interfaces.dddsample.citerus.se/")
public class HandlingReportErrors_Exception
        extends Exception {

    /**
     * Java type that goes as soapenv:Fault detail element.
     */
    private HandlingReportErrors faultInfo;

    /**
     * @param message
     * @param faultInfo
     */
    public HandlingReportErrors_Exception(String message, HandlingReportErrors faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * @param message
     * @param faultInfo
     * @param cause
     */
    public HandlingReportErrors_Exception(String message, HandlingReportErrors faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * @return returns fault bean: com.aggregator.HandlingReportErrors
     */
    public HandlingReportErrors getFaultInfo() {
        return faultInfo;
    }

}
