package javax.wsdl.extensions;

import org.w3c.dom.*;
import javax.wsdl.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface ExtensionDeserializer
{
  public ExtensibilityElement unmarshall(Class parentType,
                                         QName elementType,
                                         Element el,
                                         Definition def,
                                         ExtensionRegistry extReg)
                                           throws WSDLException;
}