package com.ibm.wsdl;

import java.util.*;
import javax.wsdl.*;
import javax.xml.namespace.*;
import org.w3c.dom.*;

/**
 * This class represents a message part and contains the part's
 * name, elementName, typeName, and any extensibility attributes.
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
  protected Map extensionAttributes = new HashMap();

  public static final long serialVersionUID = 1;

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
   * Set an extension attribute on this part. Pass in a null
   * value to remove an extension attribute.
   *
   * @param name the extension attribute name
   * @param value the extension attribute value
   *
   * @see #getExtensionAttribute
   * @see #getExtensionAttributes
   */
  public void setExtensionAttribute(QName name, QName value)
  {
    if (value != null)
    {
      extensionAttributes.put(name, value);
    }
    else
    {
      extensionAttributes.remove(name);
    }
  }

  /**
   * Retrieve an extension attribute from this part. If the
   * extension attribute is not defined, null is returned.
   *
   * @param name the extension attribute name
   * @return the value of the extension attribute, or null if
   * it is not defined
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttributes
   */
  public QName getExtensionAttribute(QName name)
  {
    return (QName)extensionAttributes.get(name);
  }

  /**
   * Get the map containing all the extension attributes defined
   * on this part. The keys are the qnames of the attributes.
   *
   * @return a map containing all the extension attributes defined
   * on this part
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttribute
   */
  public Map getExtensionAttributes()
  {
    return extensionAttributes;
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

    Iterator keys = extensionAttributes.keySet().iterator();

    while (keys.hasNext())
    {
      QName name = (QName)keys.next();

      strBuf.append("\nextension attribute: " + name + "=" +
                    extensionAttributes.get(name));
    }

    return strBuf.toString();
  }
}