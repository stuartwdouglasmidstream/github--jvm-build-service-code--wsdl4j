package javax.wsdl.extensions;

import java.io.*;
import javax.wsdl.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface ExtensionSerializer
{
  public void marshall(Class parentType,
                       QName elementType,
                       ExtensibilityElement extension,
                       PrintWriter pw,
                       Definition def,
                       ExtensionRegistry extReg)
                         throws WSDLException;
}