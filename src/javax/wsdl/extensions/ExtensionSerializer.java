package javax.wsdl.extensions;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface ExtensionSerializer
{
  /**
   * This should probably be improved to use an NSStack instead of
   * having the Definition hold the declarations in a flat table.
   */
  public void marshall(Class parentType,
                       Class extensionType,
                       ExtensibilityElement extension,
                       PrintWriter pw,
                       Definition def,
                       ExtensionRegistry extReg)
                         throws WSDLException;
}