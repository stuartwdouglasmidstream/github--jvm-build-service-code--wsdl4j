package javax.wsdl;

import java.util.*;
import javax.xml.namespace.*;
import org.w3c.dom.*;

/**
 * This interface represents a message part and contains the part's
 * name, elementName, typeName, and any extensibility attributes.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public interface Part extends java.io.Serializable
{
  /**
   * Set the name of this part.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this part.
   *
   * @return the part name
   */
  public String getName();

  public void setElementName(QName elementName);

  public QName getElementName();

  public void setTypeName(QName typeName);

  public QName getTypeName();

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
  public void setExtensionAttribute(QName name, QName value);

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
  public QName getExtensionAttribute(QName name);

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
  public Map getExtensionAttributes();

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