/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl;

import org.w3c.dom.*;
import javax.wsdl.extensions.*;

/**
 * This interface represents a port, an endpoint for the
 * functionality described by a particular port type.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public interface Port extends java.io.Serializable, ElementExtensible
{
  /**
   * Set the name of this port.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this port.
   *
   * @return the port name
   */
  public String getName();

  /**
   * Set the binding this port should refer to.
   *
   * @param binding the desired binding
   */
  public void setBinding(Binding binding);

  /**
   * Get the binding this port refers to.
   *
   * @return the binding associated with this port
   */
  public Binding getBinding();

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