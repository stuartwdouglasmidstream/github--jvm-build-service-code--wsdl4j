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
public class HTTPUrlEncodedSerializer implements ExtensionSerializer,
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
    HTTPUrlEncoded httpUrlEncoded = (HTTPUrlEncoded)extension;

    if (httpUrlEncoded != null)
    {
      pw.print("        <http:urlEncoded");

      Boolean required = httpUrlEncoded.getRequired();

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
    HTTPUrlEncoded httpUrlEncoded = new HTTPUrlEncoded();
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);

    if (requiredStr != null)
    {
      httpUrlEncoded.setRequired(new Boolean(requiredStr));
    }

    return httpUrlEncoded;
	}
}