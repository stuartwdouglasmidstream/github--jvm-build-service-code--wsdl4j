package com.ibm.wsdl.extensions.http;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.util.xml.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class HTTPOperationSerializer implements ExtensionSerializer,
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
    HTTPOperation httpOperation = (HTTPOperation)extension;

    if (httpOperation != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(HTTPConstants.NS_URI_HTTP,
                                   "operation",
                                   def);

      pw.print("      <" + tagName);

      DOMUtils.printAttribute(Constants.ATTR_LOCATION,
                              httpOperation.getLocationURI(),
                              pw);

      Boolean required = httpOperation.getRequired();

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
    HTTPOperation httpOperation = new HTTPOperation();
    String locationURI = DOMUtils.getAttribute(el, Constants.ATTR_LOCATION);
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);

    if (locationURI != null)
    {
      httpOperation.setLocationURI(locationURI);
    }

    if (requiredStr != null)
    {
      httpOperation.setRequired(new Boolean(requiredStr));
    }

    return httpOperation;
	}
}