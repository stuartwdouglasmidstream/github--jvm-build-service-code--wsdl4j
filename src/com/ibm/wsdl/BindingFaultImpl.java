package com.ibm.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * This class represents a fault binding. That is, it contains
 * the information that would be specified in an fault element
 * contained within an operation element contained within a
 * binding element.
 *
 * @author Matthew J. Duftler
 */
public class BindingFaultImpl implements BindingFault
{
  protected String name = null;
  protected Element docEl = null;
  protected List extElements = new Vector();

  /**
   * Set the name of this fault binding.
   *
   * @param name the desired name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Get the name of this fault binding.
   *
   * @return the fault binding name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Set the documentation element for this document. This dependency
   * on org.w3c.dom.Element should eventually be removed when a more
   * appropriate way of representing this information is employed.
   *
   * @param docEl the documentation element
   */
  public void setDocumentationElement(Element docEl)
  {
    this.docEl = docEl;
  }

  /**
   * Get the documentation element. This dependency on org.w3c.dom.Element
   * should eventually be removed when a more appropriate way of
   * representing this information is employed.
   *
   * @return the documentation element
   */
  public Element getDocumentationElement()
  {
    return docEl;
  }

  /**
   * Add an extensibility element.
   *
   * @param extElement the extensibility element to be added
   */
  public void addExtensibilityElement(ExtensibilityElement extElement)
  {
    extElements.add(extElement);
  }

  /**
   * Get all the extensibility elements defined here.
   */
  public List getExtensibilityElements()
  {
    return extElements;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("BindingFault: name=" + name);

    if (extElements != null)
    {
      Iterator extIterator = extElements.iterator();

      while (extIterator.hasNext())
      {
        strBuf.append("\n" + extIterator.next());
      }
    }

    return strBuf.toString();
  }
}