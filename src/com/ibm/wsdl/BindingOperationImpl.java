package com.ibm.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * This class represents a WSDL operation binding.
 * That is, it holds the information that would be
 * specified in the operation element contained within
 * a binding element.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class BindingOperationImpl implements BindingOperation
{
  protected String name = null;
  protected Operation operation = null;
	protected BindingInput bindingInput = null;
	protected BindingOutput bindingOutput = null;
	protected Map bindingFaults = new HashMap();
  protected Element docEl = null;
  protected List extElements = new Vector();

  public static final long serialVersionUID = 1;

  /**
   * Set the name of this operation binding.
   *
   * @param name the desired name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Get the name of this operation binding.
   *
   * @return the operation binding name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Set the operation that this operation binding binds.
   *
   * @param operation the operation this operation binding binds
   */
  public void setOperation(Operation operation)
  {
    this.operation = operation;
  }

  /**
   * Get the operation that this operation binding binds.
   *
   * @return the operation that this operation binding binds
   */
  public Operation getOperation()
  {
    return operation;
  }

  /**
   * Set the input binding for this operation binding.
   *
   * @param input the new input binding
   */
  public void setBindingInput(BindingInput bindingInput)
  {
    this.bindingInput = bindingInput;
  }

  /**
   * Get the input binding for this operation binding.
   *
   * @return the input binding
   */
  public BindingInput getBindingInput()
  {
    return bindingInput;
  }

  /**
   * Set the output binding for this operation binding.
   *
   * @param output the new output binding
   */
  public void setBindingOutput(BindingOutput bindingOutput)
  {
    this.bindingOutput = bindingOutput;
  }

  /**
   * Get the output binding for this operation binding.
   *
   * @return the output binding for the operation binding
   */
  public BindingOutput getBindingOutput()
  {
    return bindingOutput;
  }

	/**
	 * Add a fault binding.
   *
	 * @param fault the new fault binding
	 */
  public void addBindingFault(BindingFault bindingFault)
  {
	  bindingFaults.put(bindingFault.getName(), bindingFault);
  }

  /**
   * Get the specified fault binding.
   *
   * @param name the name of the desired fault binding.
   * @return the corresponding fault binding, or null if there wasn't
   * any matching fault binding
   */
  public BindingFault getBindingFault(String name)
  {
    return (BindingFault)bindingFaults.get(name);
  }

	/**
	 * Get all the fault bindings associated with this operation binding.
   *
	 * @return names of fault bindings
	 */
  public Map getBindingFaults()
  {
	  return bindingFaults;
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
   * Add an extensibility element.
   *
   * @param extElement the extensibility element to be added
   */
  public void addExtensibilityElement(ExtensibilityElement extElement)
  {
    extElements.add(extElement);
  }

  /**
   * Get all the extensibility elements defined here.
   */
  public List getExtensibilityElements()
  {
    return extElements;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("BindingOperation: name=" + name);

    if (bindingInput != null)
    {
      strBuf.append("\n" + bindingInput);
    }

    if (bindingOutput != null)
    {
      strBuf.append("\n" + bindingOutput);
    }

    if (bindingFaults != null)
    {
      Iterator faultIterator = bindingFaults.values().iterator();

      while (faultIterator.hasNext())
      {
        strBuf.append("\n" + faultIterator.next());
      }
    }

    if (extElements != null)
    {
      Iterator extIterator = extElements.iterator();

      while (extIterator.hasNext())
      {
        strBuf.append("\n" + extIterator.next());
      }
    }

    return strBuf.toString();
  }
}