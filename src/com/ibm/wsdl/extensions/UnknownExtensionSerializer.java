package com.ibm.wsdl.extensions;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import com.ibm.wsdl.util.xml.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class UnknownExtensionSerializer implements ExtensionSerializer,
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
    UnknownExtensibilityElement unknownExt =
      (UnknownExtensibilityElement)extension;

    pw.print("    ");

    DOM2Writer.serializeAsXML(unknownExt.getElement(), pw);

    pw.println();
  }
}