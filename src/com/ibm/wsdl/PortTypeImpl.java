package com.ibm.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;

/**
 * This class represents a port type. It contains information about
 * operations associated with this port type.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class PortTypeImpl implements PortType
{
  protected QName name = null;
  protected List operations = new Vector();
  protected Element docEl = null;
  protected boolean isUndefined = true;

  /**
   * Set the name of this port type.
   *
   * @param name the desired name
   */
  public void setQName(QName name)
  {
    this.name = name;
  }

  /**
   * Get the name of this port type.
   *
   * @return the port type name
   */
  public QName getQName()
  {
    return name;
  }

  /**
   * Add an operation to this port type.
   *
   * @param operation the operation to be added
   */
  public void addOperation(Operation operation)
  {
    operations.add(operation);
  }

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
                                String outputName)
  {
    Iterator opIterator = operations.iterator();

    while (opIterator.hasNext())
    {
      Operation op = (Operation)opIterator.next();
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
        Input input = op.getInput();

        if (input != null)
        {
          String opInputName = input.getName();

          if (opInputName == null
              || !opInputName.equals(inputName))
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
        Output output = op.getOutput();

        if (output != null)
        {
          String opOutputName = output.getName();

          if (opOutputName == null
              || !opOutputName.equals(outputName))
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
   * Get all the operations defined here.
   */
  public List getOperations()
  {
    return operations;
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

    strBuf.append("PortType: name=" + name);

    if (operations != null)
    {
      Iterator opIterator = operations.iterator();

      while (opIterator.hasNext())
      {
        strBuf.append("\n" + opIterator.next());
      }
    }

    return strBuf.toString();
  }
}