package com.ibm.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.xml.namespace.*;

/**
 * This class describes a message used for communication with an operation.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class MessageImpl implements Message
{
  protected Map parts = new HashMap();
  protected List additionOrderOfParts = new Vector();
  protected QName name = null;
  protected Element docEl = null;
  protected boolean isUndefined = true;

  public static final long serialVersionUID = 1;

  /**
   * Set the name of this message.
   *
   * @param name the desired name
   */
  public void setQName(QName name)
  {
    this.name = name;
  }  

  /**
   * Get the name of this message.
   *
   * @return the message name
   */
  public QName getQName()
  {
    return name;
  }  

  /**
   * Add a part to this message.
   *
   * @param part the part to be added
   */
  public void addPart(Part part)
  {
    String partName = part.getName();

    parts.put(partName, part);
    additionOrderOfParts.add(partName);
  }  

  /**
   * Get the specified part.
   *
   * @param name the name of the desired part.
   * @return the corresponding part, or null if there wasn't
   * any matching part
   */
  public Part getPart(String name)
  {
    return (Part)parts.get(name);
  }  

  /**
   * Get all the parts defined here.
   */
  public Map getParts()
  {
    return parts;
  }

  /**
   * Get an ordered list of parts as specified by the partOrder
   * argument.
   *
   * @param partOrder a list of strings, with each string referring
   * to a part by its name. If this argument is null, the parts are
   * returned in the order in which they were added to the message.
   * @return the list of parts
   */
  public List getOrderedParts(List partOrder)
  {
    List orderedParts = new Vector();

    if (partOrder == null)
    {
      partOrder = additionOrderOfParts;
    }

    Iterator partNameIterator = partOrder.iterator();

    while (partNameIterator.hasNext())
    {
      String partName = (String)partNameIterator.next();
      Part part = getPart(partName);

      if (part != null)
      {
        orderedParts.add(part);
      }
    }

    return orderedParts;
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

  public void setUndefined(boolean isUndefined)
  {
    this.isUndefined = isUndefined;
  }

  public boolean isUndefined()
  {
    return isUndefined;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("Message: name=" + name);

    if (parts != null)
    {
      Iterator partsIterator = parts.values().iterator();

      while (partsIterator.hasNext())
      {
        strBuf.append("\n" + partsIterator.next());
      }
    }

    return strBuf.toString();
  }
}