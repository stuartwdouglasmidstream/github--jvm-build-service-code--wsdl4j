/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package javax.wsdl;

import java.util.*;
import javax.wsdl.extensions.*;
import org.w3c.dom.Element;

/**
 * This interface represents a WSDL operation.
 * It includes information on input, output and fault
 * messages associated with usage of the operation.
 *
 * @author Paul Fremantle (pzf@us.ibm.com)
 * @author Nirmal Mukhi (nmukhi@us.ibm.com)
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface Operation extends java.io.Serializable, ElementExtensible
{
  /**
   * Set the name of this operation.
   *
   * @param name the desired name
   */
  public void setName(String name);

  /**
   * Get the name of this operation.
   *
   * @return the operation name
   */
  public String getName();

  /**
   * Set the input message specification for this operation.
   *
   * @param input the new input message
   */
  public void setInput(Input input);

  /**
   * Get the input message specification for this operation.
   *
   * @return the input message
   */
  public Input getInput();

  /**
   * Set the output message specification for this operation.
   *
   * @param output the new output message
   */
  public void setOutput(Output output);

  /**
   * Get the output message specification for this operation.
   *
   * @return the output message specification for the operation
   */
  public Output getOutput();

  /**
   * Add a fault message that must be associated with this
   * operation.
   *
   * @param fault the new fault message
   */
  public void addFault(Fault fault);

  /**
   * Get the specified fault message.
   *
   * @param name the name of the desired fault message.
   * @return the corresponding fault message, or null if there wasn't
   * any matching message
   */
  public Fault getFault(String name);

  /**
   * Get all the fault messages associated with this operation.
   *
   * @return names of fault messages
   */
  public Map getFaults();

  /**
   * Set the style for this operation (request-response,
   * one way, solicit-response or notification).
   *
   * @param style the new operation style
   */
  public void setStyle(OperationType style);

  /**
   * Get the operation type.
   *
   * @return the operation type
   */
  public OperationType getStyle();

  /**
   * Set the parameter ordering for a request-response,
   * or solicit-response operation.
   *
   * @param parameterOrder, a list of named parameters
   * containing the part names to reflect the desired
   * order of parameters for RPC-style operations
   */
  public void setParameterOrdering(List parameterOrder);

  /**
   * Get the parameter ordering for this operation.
   *
   * @return the parameter ordering, a list consisting
   * of message part names
   */
  public List getParameterOrdering();

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