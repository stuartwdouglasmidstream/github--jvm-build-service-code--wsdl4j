package javax.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.extensions.*;

/**
 * This interface represents a service, which groups related
 * ports to provide some functionality.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public interface Service extends java.io.Serializable
{
  /**
   * Set the name of this service.
   *
   * @param name the desired name
   */
  public void setQName(QName name);

  /**
   * Get the name of this service.
   *
   * @return the service name
   */
  public QName getQName();

  /**
   * Add a port to this service.
   *
   * @param port the port to be added
   */
  public void addPort(Port port);

  /**
   * Get the specified port.
   *
   * @param name the name of the desired port.
   * @return the corresponding port, or null if there wasn't
   * any matching port
   */
  public Port getPort(String name);

  /**
   * Get all the ports defined here.
   */
  public Map getPorts();

  /**
   * Add an extensibility element.
   *
   * @param extElement the extensibility element to be added
   */
  public void addExtensibilityElement(ExtensibilityElement extElement);

  /**
   * Get all the extensibility elements defined here.
   */
  public List getExtensibilityElements();

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