package com.ibm.wsdl;

import org.w3c.dom.*;
import javax.wsdl.*;
import javax.xml.namespace.*;

/**
 * This class represents a message part and contains the part's
 * name, elementName, and typeName.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class PartImpl implements Part
{
  protected String name = null;
  protected QName elementName = null;
  protected QName typeName = null;
  protected Element docEl = null;

  /**
   * Set the name of this part.
   *
   * @param name the desired name
   */
  public void setName(String name)
  {
    this.name = name;
  }  

  /**
   * Get the name of this part.
   *
   * @return the part name
   */
  public String getName()
  {
    return name;
  }  

  public void setElementName(QName elementName)
  {
    this.elementName = elementName;
  }

  public QName getElementName()
  {
    return elementName;
  }

  public void setTypeName(QName typeName)
  {
    this.typeName = typeName;
  }

  public QName getTypeName()
  {
    return typeName;
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

    strBuf.append("Part: name=" + name);

    if (elementName != null)
    {
      strBuf.append("\nelementName=" + elementName);
    }

    if (typeName != null)
    {
      strBuf.append("\ntypeName=" + typeName);
    }

    return strBuf.toString();
  }
}