package com.ibm.wsdl;

import java.util.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.xml.namespace.*;
import org.w3c.dom.*;

/**
 * This class represents a service, which groups related
 * ports to provide some functionality.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class ServiceImpl implements Service
{
  protected QName name = null;
  protected Map ports = new HashMap();
  protected Element docEl = null;
  protected List extElements = new Vector();
  protected Map extensionAttributes = new HashMap();
  protected List nativeAttributeNames =
    Arrays.asList(Constants.SERVICE_ATTR_NAMES);

  public static final long serialVersionUID = 1;

  /**
   * Set the name of this service.
   *
   * @param name the desired name
   */
  public void setQName(QName name)
  {
    this.name = name;
  }

  /**
   * Get the name of this service.
   *
   * @return the service name
   */
  public QName getQName()
  {
    return name;
  }

  /**
   * Add a port to this service.
   *
   * @param port the port to be added
   */
  public void addPort(Port port)
  {
    ports.put(port.getName(), port);
  }

  /**
   * Get the specified port.
   *
   * @param name the name of the desired port.
   * @return the corresponding port, or null if there wasn't
   * any matching port
   */
  public Port getPort(String name)
  {
    return (Port)ports.get(name);
  }

  /**
   * Get all the ports defined here.
   */
  public Map getPorts()
  {
    return ports;
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
   * Set an extension attribute on this element. Pass in a null
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
   * Retrieve an extension attribute from this element. If the
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

    strBuf.append("Service: name=" + name);

    if (ports != null)
    {
      Iterator portIterator = ports.values().iterator();

      while (portIterator.hasNext())
      {
        strBuf.append("\n" + portIterator.next());
      }
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
