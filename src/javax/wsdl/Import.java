package javax.wsdl;

import org.w3c.dom.*;

public interface Import extends java.io.Serializable
{
  public void setNamespaceURI(String namespaceURI);

  public String getNamespaceURI();

  public void setLocationURI(String locationURI);

  public String getLocationURI();

  /**
   * This property can be used to hang a referenced Definition,
   * and the top-level Definition (i.e. the one with the <import>)
   * will use this Definition when resolving referenced WSDL parts.
   * This would need to be made into a generic reference to handle
   * other types of referenced documents.
   */
  public void setDefinition(Definition definition);

  /**
   * This property can be used to hang a referenced Definition,
   * and the top-level Definition (i.e. the one with the <import>)
   * will use this Definition when resolving referenced WSDL parts.
   * This would need to be made into a generic reference to handle
   * other types of referenced documents.
   */
  public Definition getDefinition();

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