/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl;

import org.w3c.dom.*;
import javax.wsdl.extensions.*;

/**
 * This interface represents an input binding. That is, it contains
 * the information that would be specified in an input element
 * contained within an operation element contained within a
 * binding element.
 *
 * @author Matthew J. Duftler
 */
public interface BindingInput extends java.io.Serializable, ElementExtensible
{
  /**
   * Set the name of this input binding.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this input binding.
   *
   * @return the input binding name
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

}