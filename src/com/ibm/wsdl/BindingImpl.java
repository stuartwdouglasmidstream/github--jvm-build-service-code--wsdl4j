package com.ibm.wsdl;

import java.util.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.xml.namespace.*;
import org.w3c.dom.*;

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
  protected Map extensionAttributes = new HashMap();
  protected List nativeAttributeNames =
    Arrays.asList(Constants.BINDING_ATTR_NAMES);
  protected boolean isUndefined = true;

  public static final long serialVersionUID = 1;

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
    boolean found = false;
    BindingOperation ret = null;
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
        PortType pt = getPortType();
        OperationType opStyle = null;

        if (pt != null)
        {
          Operation tempOp = pt.getOperation(name, inputName, outputName);

          if (tempOp != null)
          {
            opStyle = tempOp.getStyle();
          }
        }

        String defaultInputName = opName;

        if (opStyle == OperationType.REQUEST_RESPONSE)
        {
          defaultInputName = opName + "Request";
        }
        else if (opStyle == OperationType.SOLICIT_RESPONSE)
        {
          defaultInputName = opName + "Solicit";
        }

        boolean specifiedDefault = inputName.equals(defaultInputName);
        BindingInput input = op.getBindingInput();

        if (input != null)
        {
          String opInputName = input.getName();

          if (opInputName == null)
          {
            if (!specifiedDefault)
            {
              op = null;
            }
          }
          else if (!opInputName.equals(inputName))
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
        PortType pt = getPortType();
        OperationType opStyle = null;

        if (pt != null)
        {
          Operation tempOp = pt.getOperation(name, inputName, outputName);

          if (tempOp != null)
          {
            opStyle = tempOp.getStyle();
          }
        }

        String defaultOutputName = opName;

        if (opStyle == OperationType.REQUEST_RESPONSE
            || opStyle == OperationType.SOLICIT_RESPONSE)
        {
          defaultOutputName = opName + "Response";
        }

        boolean specifiedDefault = outputName.equals(defaultOutputName);
        BindingOutput output = op.getBindingOutput();

        if (output != null)
        {
          String opOutputName = output.getName();

          if (opOutputName == null)
          {
            if (!specifiedDefault)
            {
              op = null;
            }
          }
          else if (!opOutputName.equals(outputName))
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
        if (found)
        {
          throw new IllegalArgumentException("Duplicate operation with " +
                                             "name=" + name +
                                             (inputName != null
                                              ? ", inputName=" + inputName
                                              : "") +
                                             (outputName != null
                                              ? ", outputName=" + outputName
                                              : "") +
                                             ", found in portType '" +
                                             getQName() + "'.");
        }
        else
        {
          found = true;
          ret = op;
        }
      }
    }

    return ret;
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

  /**
   * Set an extension attribute on this element. Pass in a null value to remove
   * an extension attribute.
   *
   * @param name the extension attribute name
   * @param value the extension attribute value. Can be a String, a QName, a
   * List of Strings, or a List of QNames.
   *
   * @see #getExtensionAttribute
   * @see #getExtensionAttributes
   * @see ExtensionRegistry#registerExtensionAttributeType
   * @see ExtensionRegistry#queryExtensionAttributeType
   */
  public void setExtensionAttribute(QName name, Object value)
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
   * Retrieve an extension attribute from this element. If the extension
   * attribute is not defined, null is returned.
   *
   * @param name the extension attribute name
   *
   * @return the value of the extension attribute, or null if
   * it is not defined. Can be a String, a QName, a List of Strings, or a List
   * of QNames.
   *
   * @see #setExtensionAttribute
   * @see #getExtensionAttributes
   * @see ExtensionRegistry#registerExtensionAttributeType
   * @see ExtensionRegistry#queryExtensionAttributeType
   */
  public Object getExtensionAttribute(QName name)
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
