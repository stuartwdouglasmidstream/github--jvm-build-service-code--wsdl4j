/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl.extensions.mime;

import java.util.*;
import javax.wsdl.extensions.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface MIMEPart extends ExtensibilityElement, java.io.Serializable
{
  /**
   * Add an extensibility element. This is where the MIME
   * elements go.
   *
   * @param extElement the extensibility element to be added
   */
  public void addExtensibilityElement(ExtensibilityElement extElement);

  /**
   * Get all the extensibility elements defined here.
   */
  public List getExtensibilityElements();
}