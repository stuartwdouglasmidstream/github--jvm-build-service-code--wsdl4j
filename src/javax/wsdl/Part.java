package javax.wsdl;

import org.w3c.dom.*;

/**
 * This interface represents a message part and contains the part's
 * name, elementName, and typeName.
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