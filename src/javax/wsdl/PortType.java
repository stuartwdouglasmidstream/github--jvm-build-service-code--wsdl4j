package javax.wsdl;

import java.util.*;
import org.w3c.dom.*;

/**
 * This interface represents a port type. It contains information about
 * operations associated with this port type.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public interface PortType extends java.io.Serializable
{
  /**
   * Set the name of this port type.
   *
   * @param name the desired name
   */
  public void setQName(QName name);

  /**
   * Get the name of this port type.
   *
   * @return the port type name
   */
  public QName getQName();

  /**
   * Add an operation to this port type.
   *
   * @param operation the operation to be added
   */
  public void addOperation(Operation operation);

  /**
   * Get the specified operation. Note that operation names can
   * be overloaded within a PortType. In case of overloading, the
   * names of the input and output messages can be used to further
   * refine the search.
   *
   * @param name the name of the desired operation.
   * @param inputName the name of the input message; if this is null
   * it will be ignored.
   * @param outputName the name of the output message; if this is null
   * it will be ignored.
   * @return the corresponding operation, or null if there wasn't
   * any matching operation
   */
  public Operation getOperation(String name,
                                String inputName,
                                String outputName);

  /**
   * Get all the operations defined here.
   */
  public List getOperations();

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

  public void setUndefined(boolean isUndefined);

  public boolean isUndefined();
}