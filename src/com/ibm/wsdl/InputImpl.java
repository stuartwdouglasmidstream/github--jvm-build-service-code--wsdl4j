package com.ibm.wsdl;

import org.w3c.dom.*;
import javax.wsdl.*;

/**
 * This class represents an input message, and contains the name
 * of the input and the message itself.
 *
 * @author Matthew J. Duftler
 */
public class InputImpl implements Input
{
  protected String name = null;
  protected Message message = null;
  protected Element docEl = null;

  /**
   * Set the name of this input message.
   *
   * @param name the desired name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Get the name of this input message.
   *
   * @return the input message name
   */
  public String getName()
  {
    return name;
  }

  public void setMessage(Message message)
  {
    this.message = message;
  }

  public Message getMessage()
  {
    return message;
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

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("Input: name=" + name);

    if (message != null)
    {
      strBuf.append("\n" + message);
    }

    return strBuf.toString();
  }
}