/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl;

import org.w3c.dom.*;
import javax.wsdl.extensions.*;

/**
 * This interface represents the &lt;types&gt; section of a WSDL document.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface Types extends java.io.Serializable, ElementExtensible
{
  /**
   * Set the documentation element for this document. This dependency
   * on org.w3c.dom.Element should eventually be removed when a more
   * appropriate way of representing this information is employed.
   *
   * @param docEl the documentation element
   */
  public void setDocumentationElement(Element docEl);

  /**
   * Get the documentation element. This dependency on org.w3c.dom.Element
   * should eventually be removed when a more appropriate way of
   * representing this information is employed.
   *
   * @return the documentation element
   */
  public Element getDocumentationElement();
}