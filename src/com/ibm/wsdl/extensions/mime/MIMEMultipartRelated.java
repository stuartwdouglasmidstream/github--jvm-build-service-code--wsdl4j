package com.ibm.wsdl.extensions.mime;

import java.util.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class MIMEMultipartRelated implements ExtensibilityElement,
                                             java.io.Serializable
{
  protected QName elementType = MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED;
  // Uses the wrapper type so we can tell if it was set or not.
  protected Boolean required = null;
  protected List mimeParts = new Vector();

  /**
   * Set the type of this extensibility element.
   *
   * @param elementType the type
   */
  public void setElementType(QName elementType)
  {
    this.elementType = elementType;
  }

  /**
   * Get the type of this extensibility element.
   *
   * @return the extensibility element's type
   */
  public QName getElementType()
  {
    return elementType;
  }

  /**
   * Set whether or not the semantics of this extension
   * are required. Relates to the wsdl:required attribute.
   */
  public void setRequired(Boolean required)
  {
    this.required = required;
  }

  /**
   * Get whether or not the semantics of this extension
   * are required. Relates to the wsdl:required attribute.
   */
  public Boolean getRequired()
  {
    return required;
  }

  /**
   * Add a MIME part to this MIME multipart related.
   *
   * @param mimePart the MIME part to be added
   */
  public void addMIMEPart(MIMEPart mimePart)
  {
    mimeParts.add(mimePart);
  }

  /**
   * Get all the MIME parts defined here.
   */
  public List getMIMEParts()
  {
    return mimeParts;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("MIMEMultipartRelated (" + elementType + "):");
    strBuf.append("\nrequired=" + required);

    if (mimeParts != null)
    {
      Iterator mimePartIterator = mimeParts.iterator();

      while (mimePartIterator.hasNext())
      {
        strBuf.append("\n" + mimePartIterator.next());
      }
    }

    return strBuf.toString();
  }
}