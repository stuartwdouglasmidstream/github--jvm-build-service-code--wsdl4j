package com.ibm.wsdl.extensions.soap;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.util.xml.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class SOAPBindingSerializer implements ExtensionSerializer,
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
    SOAPBinding soapBinding = (SOAPBinding)extension;

    if (soapBinding != null)
    {
      pw.print("    <soap:binding");

      DOMUtils.printAttribute(SOAPConstants.ATTR_STYLE,
                              soapBinding.getStyle(),
                              pw);
      DOMUtils.printAttribute(SOAPConstants.ATTR_TRANSPORT,
                              soapBinding.getTransportURI(),
                              pw);

      Boolean required = soapBinding.getRequired();

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
    SOAPBinding soapBinding = new SOAPBinding();
    String transportURI = DOMUtils.getAttribute(el,
                                                SOAPConstants.ATTR_TRANSPORT);
    String style = DOMUtils.getAttribute(el, SOAPConstants.ATTR_STYLE);
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);

    if (transportURI != null)
    {
      soapBinding.setTransportURI(transportURI);
    }

    if (style != null)
    {
      soapBinding.setStyle(style);
    }

    if (requiredStr != null)
    {
      soapBinding.setRequired(new Boolean(requiredStr));
    }

    return soapBinding;
  }
}