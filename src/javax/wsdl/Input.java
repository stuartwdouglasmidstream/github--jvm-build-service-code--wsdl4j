package javax.wsdl;

import org.w3c.dom.*;

/**
 * This interface represents an input message, and contains the name
 * of the input and the message itself.
 *
 * @author Matthew J. Duftler
 */
public interface Input extends java.io.Serializable, AttributeExtensible
{
  /**
   * Set the name of this input message.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this input message.
   *
   * @return the input message name
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
