package javax.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.extensions.*;

/**
 * This interface represents a fault binding. That is, it contains
 * the information that would be specified in a fault element
 * contained within an operation element contained within a
 * binding element.
 *
 * @author Matthew J. Duftler
 */
public interface BindingFault extends java.io.Serializable, AttributeExtensible
{
  /**
   * Set the name of this fault binding.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this fault binding.
   *
   * @return the fault binding name
   */
  public String getName();

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

  /**
   * Add an extensibility element.
   *
   * @param extElement the extensibility element to be added
   */
  public void addExtensibilityElement(ExtensibilityElement extElement);

  /**
   * Get all the extensibility elements defined here.
   */
  public List getExtensibilityElements();
}
