package javax.wsdl.extensions.mime;

import java.util.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface MIMEMultipartRelated extends ExtensibilityElement,
                                              java.io.Serializable
{
  /**
   * Add a MIME part to this MIME multipart related.
   *
   * @param mimePart the MIME part to be added
   */
  public void addMIMEPart(MIMEPart mimePart);

  /**
   * Get all the MIME parts defined here.
   */
  public List getMIMEParts();
}