package javax.wsdl;

import java.util.*;
import javax.xml.namespace.*;

/**
 * Classes that implement this interface can contain extensibility
 * attributes.
 *
 * @author Matthew J. Duftler
 * @author Paul Fremantle
 */
public interface AttributeExtensible
{
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
   * Get the list of local attribute names defined for this element in
   * the WSDL specification.
   *
   * @return a List of Strings, one for each local attribute name
   */
  public List getNativeAttributeNames();
}