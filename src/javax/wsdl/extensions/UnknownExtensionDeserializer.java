package javax.wsdl.extensions;

import org.w3c.dom.*;
import javax.wsdl.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.util.xml.*;

/**
 * This class deserializes arbitrary elements into UnknownExtensibilityElement
 * instances.
 *
 * @see UnknownExtensibilityElement
 *
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
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);

    unknownExt.setElementType(elementType);

    if (requiredStr != null)
    {
      unknownExt.setRequired(new Boolean(requiredStr));
    }

    unknownExt.setElement(el);

    return unknownExt;
  }
}