package com.ibm.wsdl;

import java.util.*;
import javax.wsdl.*;
import javax.xml.namespace.*;
import org.w3c.dom.*;

/**
 * This class represents a WSDL operation.
 * It includes information on input, output and fault
 * messages associated with usage of the operation.
 *
 * @author Paul Fremantle (pzf@us.ibm.com)
 * @author Nirmal Mukhi (nmukhi@us.ibm.com)
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class OperationImpl implements Operation
{
  protected String name = null;
  protected Input input = null;
  protected Output output = null;
  protected Map faults = new HashMap();
  protected OperationType style = null;
  protected List parameterOrder = null;
  protected Element docEl = null;
  protected Map extensionAttributes = new HashMap();
  protected List nativeAttributeNames =
    Arrays.asList(Constants.OPERATION_ATTR_NAMES);
  protected boolean isUndefined = true;

  public static final long serialVersionUID = 1;

  /**
   * Set the name of this operation.
   *
   * @param name the desired name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Get the name of this operation.
   *
   * @return the operation name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Set the input message specification for this operation.
   *
   * @param input the new input message
   */
  public void setInput(Input input)
  {
    this.input = input;
  }

  /**
   * Get the input message specification for this operation.
   *
   * @return the input message
   */
  public Input getInput()
  {
    return input;
  }

  /**
   * Set the output message specification for this operation.
   *
   * @param output the new output message
   */
  public void setOutput(Output output)
  {
    this.output = output;
  }

  /**
   * Get the output message specification for this operation.
   *
   * @return the output message specification for the operation
   */
  public Output getOutput()
  {
    return output;
  }

  /**
   * Add a fault message that must be associated with this
   * operation.
   *
   * @param fault the new fault message
   */
  public void addFault(Fault fault)
  {
    faults.put(fault.getName(), fault);
  }

  /**
   * Get the specified fault message.
   *
   * @param name the name of the desired fault message.
   * @return the corresponding fault message, or null if there wasn't
   * any matching message
   */
  public Fault getFault(String name)
  {
    return (Fault)faults.get(name);
  }

  /**
   * Get all the fault messages associated with this operation.
   *
   * @return names of fault messages
   */
  public Map getFaults()
  {
    return faults;
  }

  /**
   * Set the style for this operation (request-response,
   * one way, solicit-response or notification).
   *
   * @param style the new operation style
   */
  public void setStyle(OperationType style)
  {
    this.style = style;
  }

  /**
   * Get the operation type.
   *
   * @return the operation type
   */
  public OperationType getStyle()
  {
    return style;
  }

  /**
   * Set the parameter ordering for a request-response,
   * or solicit-response operation.
   *
   * @param parameterOrder, a list of named parameters
   * containing the part names to reflect the desired
   * order of parameters for RPC-style operations
   */
  public void setParameterOrdering(List parameterOrder)
  {
    this.parameterOrder = parameterOrder;
  }

  /**
   * Get the parameter ordering for this operation.
   *
   * @return the parameter ordering, a list consisting
   * of message part names
   */
  public List getParameterOrdering()
  {
    return parameterOrder;
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

  /**
   * Set an extension attribute on this element. Pass in a null
   * value to remove an extension attribute.
   *
   * @param name the extension attribute name
   * @param value the extension attribute value
   *
   * @see #getExtensionAttribute
   * @see #getExtensionAttributes
   */
  public void setExtensionAttribute(QName name, QName value)
  {
    if (value != null)
    {
      extensionAttributes.put(name, value);
    }
    else
    {
      extensionAttributes.remove(name);
    }
  }

  /**
   * Retrieve an extension attribute from this element. If the
   * extension attribute is not defined, null is returned.
   *
   * @param name the extension attribute name
   * @return the value of the extension attribute, or null if
   * it is not defined
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttributes
   */
  public QName getExtensionAttribute(QName name)
  {
    return (QName)extensionAttributes.get(name);
  }

  /**
   * Get the map containing all the extension attributes defined
   * on this element. The keys are the qnames of the attributes.
   *
   * @return a map containing all the extension attributes defined
   * on this element
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttribute
   */
  public Map getExtensionAttributes()
  {
    return extensionAttributes;
  }

  /**
   * Get the list of local attribute names defined for this element in
   * the WSDL specification.
   *
   * @return a List of Strings, one for each local attribute name
   */
  public List getNativeAttributeNames()
  {
    return nativeAttributeNames;
  }

  public void setUndefined(boolean isUndefined)
  {
    this.isUndefined = isUndefined;
  }

  public boolean isUndefined()
  {
    return isUndefined;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("Operation: name=" + name);

    if (parameterOrder != null)
    {
      strBuf.append("\nparameterOrder=" + parameterOrder);
    }

    if (style != null)
    {
      strBuf.append("\nstyle=" + style);
    }

    if (input != null)
    {
      strBuf.append("\n" + input);
    }

    if (output != null)
    {
      strBuf.append("\n" + output);
    }

    if (faults != null)
    {
      Iterator faultIterator = faults.values().iterator();

      while (faultIterator.hasNext())
      {
        strBuf.append("\n" + faultIterator.next());
      }
    }

    Iterator keys = extensionAttributes.keySet().iterator();

    while (keys.hasNext())
    {
      QName name = (QName)keys.next();

      strBuf.append("\nextension attribute: " + name + "=" +
                    extensionAttributes.get(name));
    }

    return strBuf.toString();
  }
}
