/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl;

import org.w3c.dom.*;
import javax.wsdl.extensions.AttributeExtensible;

/**
 * This interface represents an output message, and contains the name
 * of the output and the message itself.
 *
 * @author Matthew J. Duftler
 */
public interface Output extends java.io.Serializable, AttributeExtensible
{
  /**
   * Set the name of this output message.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this output message.
   *
   * @return the output message name
   */
  public String getName();

  public void setMessage(Message message);

  public Message getMessage();

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