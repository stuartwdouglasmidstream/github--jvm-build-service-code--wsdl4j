package javax.wsdl;

import org.w3c.dom.*;

/**
 * This interface represents a fault message, and contains the name
 * of the fault and the message itself.
 *
 * @author Matthew J. Duftler
 */
public interface Fault extends java.io.Serializable
{
  /**
   * Set the name of this fault message.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this fault message.
   *
   * @return the fault message name
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