package com.ibm.wsdl;

import org.w3c.dom.*;
import javax.wsdl.*;

/**
 * This class represents an import, and may contain a reference
 * to the imported definition.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class ImportImpl implements Import
{
  protected String namespaceURI = null;
  protected String locationURI = null;
  /*
    This would need to be made into a generic reference to handle other
    types of referenced documents.
  */
  protected Definition definition = null;
  protected Element docEl = null;

  public static final long serialVersionUID = 1;

  public void setNamespaceURI(String namespaceURI)
  {
    this.namespaceURI = namespaceURI;
  }

  public String getNamespaceURI()
  {
    return namespaceURI;
  }

  public void setLocationURI(String locationURI)
  {
    this.locationURI = locationURI;
  }

  public String getLocationURI()
  {
    return locationURI;
  }

  /**
   * This property can be used to hang a referenced Definition,
   * and the top-level Definition (i.e. the one with the &lt;import&gt;)
   * will use this Definition when resolving referenced WSDL parts.
   * This would need to be made into a generic reference to handle
   * other types of referenced documents.
   */
  public void setDefinition(Definition definition)
  {
    this.definition = definition;
  }

  /**
   * This property can be used to hang a referenced Definition,
   * and the top-level Definition (i.e. the one with the &lt;import&gt;)
   * will use this Definition when resolving referenced WSDL parts.
   * This would need to be made into a generic reference to handle
   * other types of referenced documents.
   */
  public Definition getDefinition()
  {
    return definition;
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

    strBuf.append("Import:");

    if (namespaceURI != null)
    {
      strBuf.append("\nnamespaceURI=" + namespaceURI);
    }

    if (locationURI != null)
    {
      strBuf.append("\nlocationURI=" + locationURI);
    }

    if (definition != null)
    {
      strBuf.append("\ndefinition=" + definition);
    }

    return strBuf.toString();
  }
}