package com.ibm.wsdl;

import java.util.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.xml.namespace.*;
import org.w3c.dom.*;

/**
 * This class represents a port, an endpoint for the
 * functionality described by a particular port type.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class PortImpl implements Port
{
  protected String name = null;
  protected Binding binding = null;
  protected Element docEl = null;
  protected List extElements = new Vector();
  protected Map extensionAttributes = new HashMap();
  protected List nativeAttributeNames =
    Arrays.asList(Constants.PORT_ATTR_NAMES);

  public static final long serialVersionUID = 1;

  /**
   * Set the name of this port.
   *
   * @param name the desired name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Get the name of this port.
   *
   * @return the port name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Set the binding this port should refer to.
   *
   * @param binding the desired binding
   */
  public void setBinding(Binding binding)
  {
    this.binding = binding;
  }

  /**
   * Get the binding this port refers to.
   *
   * @return the binding associated with this port
   */
  public Binding getBinding()
  {
    return binding;
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

  /**
   * Set an extension attribute on this element. Pass in a null value to remove
   * an extension attribute.
   *
   * @param name the extension attribute name
   * @param value the extension attribute value. Can be a String, a QName, a
   * List of Strings, or a List of QNames.
   *
   * @see #getExtensionAttribute
   * @see #getExtensionAttributes
   * @see ExtensionRegistry#registerExtensionAttributeType
   * @see ExtensionRegistry#queryExtensionAttributeType
   */
  public void setExtensionAttribute(QName name, Object value)
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
   * Retrieve an extension attribute from this element. If the extension
   * attribute is not defined, null is returned.
   *
   * @param name the extension attribute name
   *
   * @return the value of the extension attribute, or null if
   * it is not defined. Can be a String, a QName, a List of Strings, or a List
   * of QNames.
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttributes
   * @see ExtensionRegistry#registerExtensionAttributeType
   * @see ExtensionRegistry#queryExtensionAttributeType
   */
  public Object getExtensionAttribute(QName name)
  {
    return extensionAttributes.get(name);
  }

  /**
   * Get the map containing all the extension attributes defined
   * on this element. The keys are the qnames of the attributes.
   *
   * @return a map containing all the extension attributes defined
   * on this element
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttribute
   */
  public Map getExtensionAttributes()
  {
    return extensionAttributes;
  }

  /**
   * Get the list of local attribute names defined for this element in
   * the WSDL specification.
   *
   * @return a List of Strings, one for each local attribute name
   */
  public List getNativeAttributeNames()
  {
    return nativeAttributeNames;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("Port: name=" + name);

    if (binding != null)
    {
      strBuf.append("\n" + binding);
    }

    if (extElements != null)
    {
      Iterator extIterator = extElements.iterator();

      while (extIterator.hasNext())
      {
        strBuf.append("\n" + extIterator.next());
      }
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
