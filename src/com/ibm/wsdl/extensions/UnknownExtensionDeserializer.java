package com.ibm.wsdl.extensions;

import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class UnknownExtensionDeserializer implements ExtensionDeserializer,
                                                     java.io.Serializable
{
  public ExtensibilityElement unmarshall(Class parentType,
                                         QName elementType,
                                         Element el,
                                         Definition def,
                                         ExtensionRegistry extReg)
                                           throws WSDLException
  {
    UnknownExtensibilityElement unknownExt = new UnknownExtensibilityElement();

    unknownExt.setElement(el);

    return unknownExt;
  }
}