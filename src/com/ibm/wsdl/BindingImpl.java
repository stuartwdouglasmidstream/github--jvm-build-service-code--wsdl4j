package com.ibm.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * This class represents a port type binding and describes the
 * protocol required for using operations in a port type.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class BindingImpl implements Binding
{
  protected QName name = null;
  protected PortType portType = null;
  protected List bindingOperations = new Vector();
  protected Element docEl = null;
  protected List extElements = new Vector();
  protected boolean isUndefined = true;

  /**
   * Set the name of this binding.
   *
   * @param name the desired name
   */
  public void setQName(QName name)
  {
	  this.name = name;
  }  

  /**
   * Get the name of this binding.
   *
   * @return the binding name
   */
  public QName getQName()
  {
	  return name;
  }  

  /**
   * Set the port type this is a binding for.
   *
   * @param portType the port type associated with this binding
   */
  public void setPortType(PortType portType) 
  {
	  this.portType = portType;
  }    

	/**
	 * Get the port type this is a binding for.
   *
	 * @return the associated port type
	 */
	public PortType getPortType()
  {
	  return portType;
  }  

  /**
   * Add an operation binding to binding.
   *
   * @param bindingOperation the operation binding to be added
   */
  public void addBindingOperation(BindingOperation bindingOperation)
  {
	  bindingOperations.add(bindingOperation);
  }  

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
											                        String outputName)
  {
  	Iterator opBindingIterator = bindingOperations.iterator();

    while (opBindingIterator.hasNext())
    {
      BindingOperation op = (BindingOperation)opBindingIterator.next();
      String opName = op.getName();

      if (name != null && opName != null)
      {
        if (!name.equals(opName))
        {
          op = null;
        }
      }
      else if (name != null || opName != null)
      {
        op = null;
      }

      if (op != null && inputName != null)
      {
        BindingInput input = op.getBindingInput();

        if (input != null)
        {
          String opInputName = input.getName();

          if (opInputName == null || !opInputName.equals(inputName))
          {
            op = null;
          }
        }
        else
        {
          op = null;
        }
	    }

      if (op != null && outputName != null)
      {
        BindingOutput output = op.getBindingOutput();

        if (output != null)
        {
          String opOutputName = output.getName();

          if (opOutputName == null || !opOutputName.equals(outputName))
          {
            op = null;
          }
        }
        else
        {
          op = null;
        }
      }

      if (op != null)
      {
        return op;
      }
    }

  	return null;
  }  

  /**
   * Get all the operation bindings defined here.
   */
  public List getBindingOperations()
  {
  	return bindingOperations;
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

	  strBuf.append("Binding: name=" + name);

    if (portType != null)
    {
      strBuf.append("\n" + portType);
    }

    if (bindingOperations != null)
    {
      Iterator bindingOperationIterator = bindingOperations.iterator();

      while (bindingOperationIterator.hasNext())
      {
        strBuf.append("\n" + bindingOperationIterator.next());
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