package com.ibm.wsdl.extensions.soap;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.util.*;
import com.ibm.wsdl.util.xml.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class SOAPOperationSerializer implements ExtensionSerializer,
                                                ExtensionDeserializer,
                                                Serializable
{
  public void marshall(Class parentType,
                       Class extensionType,
                       ExtensibilityElement extension,
                       PrintWriter pw,
                       Definition def,
                       ExtensionRegistry extReg)
                         throws WSDLException
  {
    SOAPOperation soapOperation = (SOAPOperation)extension;

    if (soapOperation != null)
    {
      pw.print("      <soap:operation");

      DOMUtils.printAttribute(SOAPConstants.ATTR_SOAP_ACTION,
                              soapOperation.getSoapActionURI(),
                              pw);
      DOMUtils.printAttribute(SOAPConstants.ATTR_STYLE,
                              soapOperation.getStyle(),
                              pw);

      Boolean required = soapOperation.getRequired();

      if (required != null)
      {
        DOMUtils.printQualifiedAttribute(Constants.Q_ATTR_REQUIRED,
                                         required.toString(),
                                         def,
                                         pw);
      }

      pw.println("/>");
    }
  }

  public ExtensibilityElement unmarshall(Class parentType,
                                         QName elementType,
                                         Element el,
                                         Definition def,
                                         ExtensionRegistry extReg)
                                           throws WSDLException
  {
    SOAPOperation soapOperation = new SOAPOperation();
    String soapActionURI = DOMUtils.getAttribute(el,
                                             SOAPConstants.ATTR_SOAP_ACTION);
    String style = DOMUtils.getAttribute(el, SOAPConstants.ATTR_STYLE);
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);

    if (soapActionURI != null)
    {
      soapOperation.setSoapActionURI(soapActionURI);
    }

    if (style != null)
    {
      soapOperation.setStyle(style);
    }

    if (requiredStr != null)
    {
      soapOperation.setRequired(new Boolean(requiredStr));
    }

    return soapOperation;
  }
}