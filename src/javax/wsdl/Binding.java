package javax.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.extensions.*;

/**
 * This interface represents a port type binding and describes the
 * protocol required for using operations in a port type.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public interface Binding extends java.io.Serializable
{
  /**
   * Set the name of this binding.
   *
   * @param name the desired name
   */
  public void setQName(QName name);

  /**
   * Get the name of this binding.
   *
   * @return the binding name
   */
  public QName getQName();

  /**
   * Set the port type this is a binding for.
   *
   * @param portType the port type associated with this binding
   */
  public void setPortType(PortType portType);

	/**
	 * Get the port type this is a binding for.
   *
	 * @return the associated port type
	 */
	public PortType getPortType();

  /**
   * Add an operation binding to binding.
   *
   * @param bindingOperation the operation binding to be added
   */
  public void addBindingOperation(BindingOperation bindingOperation);

  /**
   * Get the specified operation binding. Note that operation names can
   * be overloaded within a PortType. In case of overloading, the
   * names of the input and output messages can be used to further
   * refine the search.
   *
   * @param name the name of the desired operation binding.
   * @param inputName the name of the input message; if this is null
   * it will be ignored.
   * @param outputName the name of the output message; if this is null
   * it will be ignored.
   * @return the corresponding operation binding, or null if there wasn't
   * any matching operation binding
   */
  public BindingOperation getBindingOperation(String name,
											                        String inputName,
											                        String outputName);

  /**
   * Get all the operation bindings defined here.
   */
  public List getBindingOperations();

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

  public void setUndefined(boolean isUndefined);

  public boolean isUndefined();
}