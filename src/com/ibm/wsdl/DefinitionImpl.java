package com.ibm.wsdl;

import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.xml.namespace.*;
import com.ibm.wsdl.util.xml.*;

/**
 * This class represents a WSDL definition.
 *
 * @author Paul Fremantle
 * @author Nirmal Mukhi
 * @author Matthew J. Duftler
 */
public class DefinitionImpl implements Definition
{
  protected String documentBaseURI = null;
  protected QName name = null;
  protected String targetNamespace = null;
  protected Map namespaces = new HashMap();
  protected Map imports = new HashMap();
  protected Types types = null;
  protected Map messages = new HashMap();
  protected Map bindings = new HashMap();
  protected Map portTypes = new HashMap();
  protected Map services = new HashMap();
  protected Element docEl = null;
  protected List extElements = new Vector();
  protected ExtensionRegistry extReg = null;

  public DefinitionImpl()
  {
    addNamespace(null, Constants.NS_URI_WSDL);
  }

  /**
   * Set the document base URI of this definition. Can be used to
   * represent the origin of the Definition, and can be exploited
   * when resolving relative URIs (e.g. in &lt;import&gt;s).
   *
   * @param documentBaseURI the document base URI of this definition
   */
  public void setDocumentBaseURI(String documentBaseURI)
  {
    this.documentBaseURI = documentBaseURI;
  }

  /**
   * Get the document base URI of this definition.
   *
   * @return the document base URI
   */
  public String getDocumentBaseURI()
  {
    return documentBaseURI;
  }

  /**
   * Set the name of this definition.
   *
   * @param name the desired name
   */
  public void setQName(QName name)
  {
    this.name = name;
  }

  /**
   * Get the name of this definition.
   *
   * @return the definition name
   */
  public QName getQName()
  {
    return name;
  }

  /**
   * Set the target namespace in which WSDL elements are defined.
   *
   * @param namespace the target namespace
   */
  public void setTargetNamespace(String targetNamespace)
  {
	  this.targetNamespace = targetNamespace;
  }

	/**
	 * Get the target namespace in which the WSDL elements
	 * are defined.
   *
	 * @return the target namespace
	 */
	public String getTargetNamespace()
  {
	  return targetNamespace;
  }

  /**
   * This is a way to add a namespace association to a definition.
   * It is similar to adding a namespace prefix declaration to the
   * top of a &lt;wsdl:definition&gt; element. This has nothing to do
   * with the &lt;wsdl:import&gt; element; there are separate methods for
   * dealing with information described by &lt;wsdl:import&gt; elements.
   * There is a default namespace association (which can be
   * overridden) between the null prefix and
   * http://schemas.xmlsoap.org/wsdl/.
   *
   * @param prefix the prefix to use for this namespace (when
   * rendering this information as XML). Use null or an empty string
   * to describe the default namespace (i.e. xmlns="...").
   * @param namespaceURI the namespace URI to associate the prefix
   * with. If you use null, the namespace association will be removed.
   */
   public void addNamespace(String prefix, String namespaceURI)
   {
     if (prefix == null)
     {
       prefix = "";
     }

     if (namespaceURI != null)
     {
       namespaces.put(prefix, namespaceURI);
     }
     else
     {
       namespaces.remove(prefix);
     }
   }

   /**
    * Get the namespace URI associated with this prefix. Or null if
    * there is no namespace URI associated with this prefix. This is
    * unrelated to the &lt;wsdl:import&gt; element.
    *
    * @see #addNamespace(String, String)
    * @see #getPrefix(String)
    */
   public String getNamespace(String prefix)
   {
     return (String)namespaces.get(prefix);
   }

   /**
    * Get a prefix associated with this namespace URI. Or null if
    * there are no prefixes associated with this namespace URI. This is
    * unrelated to the &lt;wsdl:import&gt; element.
    *
    * @see #addNamespace(String, String)
    * @see #getNamespace(String)
    */
   public String getPrefix(String namespaceURI)
   {
     if (namespaceURI == null)
     {
       return null;
     }

     Iterator entryIterator = namespaces.entrySet().iterator();

     while (entryIterator.hasNext())
     {
       Map.Entry entry = (Map.Entry)entryIterator.next();
       String prefix = (String)entry.getKey();
       String assocNamespaceURI = (String)entry.getValue();

       if (namespaceURI.equals(assocNamespaceURI))
       {
         return prefix;
       }
     }

     return null;
   }

   /**
    * Get all namespace associations in this definition. The keys are
    * the prefixes, and the namespace URIs are the values. This is
    * unrelated to the &lt;wsdl:import&gt; element.
    *
    * @see #addNamespace(String, String)
    */
   public Map getNamespaces()
   {
     return namespaces;
   }

  /**
   * Set the types section.
   */
  public void setTypes(Types types)
  {
    this.types = types;
  }

  /**
   * Get the types section.
   *
   * @return the types section
   */
  public Types getTypes()
  {
    return types;
  }

  /**
   * Add an import to this WSDL description.
   *
   * @param importDef the import to be added
   */
  public void addImport(Import importDef)
  {
    String namespaceURI = importDef.getNamespaceURI();
    List importList = (List)imports.get(namespaceURI);

    if (importList == null)
    {
      importList = new Vector();

      imports.put(namespaceURI, importList);
    }

    importList.add(importDef);
  }

  /**
   * Get the list of imports for the specified namespaceURI.
   *
   * @param namespaceURI the namespaceURI associated with the
   * desired imports.
   * @return a list of the corresponding imports, or null if
   * there weren't any matching imports
   */
  public List getImports(String namespaceURI)
  {
    return (List)imports.get(namespaceURI);
  }

  /**
   * Get a map of lists containing all the imports defined here.
   * The map's keys are the namespaceURIs, and the map's values
   * are lists. There is one list for each namespaceURI for which
   * imports have been defined.
   */
  public Map getImports()
  {
    return imports;
  }

  /**
   * Add a message to this WSDL description.
   *
   * @param message the message to be added
   */
  public void addMessage(Message message)
  {
    messages.put(message.getQName(), message);
  }

  /**
   * Get the specified message. Also checks imported documents.
   *
   * @param name the name of the desired message.
   * @return the corresponding message, or null if there wasn't
   * any matching message
   */
  public Message getMessage(QName name)
  {
    Message message = (Message)messages.get(name);

    if (message == null && name != null)
    {
      message = (Message)getFromImports(Constants.ELEM_MESSAGE, name);
    }

    return message;
  }

  /**
   * Remove the specified message from this definition.
   *
   * @param name the name of the message to remove
   * @return the message previously associated with this qname, if there
   * was one; may return null
   */
  public Message removeMessage(QName name)
  {
    return (Message)messages.remove(name);
  }

  /**
   * Get all the messages defined here.
   */
  public Map getMessages()
  {
    return messages;
  }

  /**
   * Add a binding to this WSDL description.
   *
   * @param binding the binding to be added
   */
  public void addBinding(Binding binding)
  {
    bindings.put(binding.getQName(), binding);
  }

  /**
   * Get the specified binding. Also checks imported documents.
   *
   * @param name the name of the desired binding.
   * @return the corresponding binding, or null if there wasn't
   * any matching binding
   */
  public Binding getBinding(QName name)
  {
    Binding binding = (Binding)bindings.get(name);

    if (binding == null && name != null)
    {
      binding = (Binding)getFromImports(Constants.ELEM_BINDING, name);
    }

    return binding;
  }

  /**
   * Remove the specified binding from this definition.
   *
   * @param name the name of the binding to remove
   * @return the binding previously associated with this qname, if there
   * was one; may return null
   */
  public Binding removeBinding(QName name)
  {
    return (Binding)bindings.remove(name);
  }

  /**
   * Get all the bindings defined here.
   */
  public Map getBindings()
  {
    return bindings;
  }

  /**
   * Add a portType to this WSDL description.
   *
   * @param portType the portType to be added
   */
  public void addPortType(PortType portType)
  {
    portTypes.put(portType.getQName(), portType);
  }

  /**
   * Get the specified portType. Also checks imported documents.
   *
   * @param name the name of the desired portType.
   * @return the corresponding portType, or null if there wasn't
   * any matching portType
   */
  public PortType getPortType(QName name)
  {
    PortType portType = (PortType)portTypes.get(name);

    if (portType == null && name != null)
    {
      portType = (PortType)getFromImports(Constants.ELEM_PORT_TYPE, name);
    }

    return portType;
  }

  /**
   * Remove the specified portType from this definition.
   *
   * @param name the name of the portType to remove
   * @return the portType previously associated with this qname, if there
   * was one; may return null
   */
  public PortType removePortType(QName name)
  {
    return (PortType)portTypes.remove(name);
  }

  /**
   * Get all the portTypes defined here.
   */
  public Map getPortTypes()
  {
    return portTypes;
  }

  /**
   * Add a service to this WSDL description.
   *
   * @param service the service to be added
   */
  public void addService(Service service)
  {
    services.put(service.getQName(), service);
  }

  /**
   * Get the specified service. Also checks imported documents.
   *
   * @param name the name of the desired service.
   * @return the corresponding service, or null if there wasn't
   * any matching service
   */
  public Service getService(QName name)
  {
    Service service = (Service)services.get(name);

    if (service == null && name != null)
    {
      service = (Service)getFromImports(Constants.ELEM_SERVICE, name);
    }

    return service;
  }

  /**
   * Remove the specified service from this definition.
   *
   * @param name the name of the service to remove
   * @return the service previously associated with this qname, if there
   * was one; may return null
   */
  public Service removeService(QName name)
  {
    return (Service)services.remove(name);
  }

  /**
   * Get all the services defined here.
   */
  public Map getServices()
  {
    return services;
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
   * Create a new binding.
   *
   * @return the newly created binding
   */
  public Binding createBinding()
  {
    return new BindingImpl();
  }

  /**
   * Create a new binding fault.
   *
   * @return the newly created binding fault
   */
  public BindingFault createBindingFault()
  {
    return new BindingFaultImpl();
  }

  /**
   * Create a new binding input.
   *
   * @return the newly created binding input
   */
  public BindingInput createBindingInput()
  {
    return new BindingInputImpl();
  }

  /**
   * Create a new binding operation.
   *
   * @return the newly created binding operation
   */
  public BindingOperation createBindingOperation()
  {
    return new BindingOperationImpl();
  }

  /**
   * Create a new binding output.
   *
   * @return the newly created binding output
   */
  public BindingOutput createBindingOutput()
  {
    return new BindingOutputImpl();
  }

  /**
   * Create a new fault.
   *
   * @return the newly created fault
   */
  public Fault createFault()
  {
    return new FaultImpl();
  }

  /**
   * Create a new import.
   *
   * @return the newly created import
   */
  public Import createImport()
  {
    return new ImportImpl();
  }

  /**
   * Create a new input.
   *
   * @return the newly created input
   */
  public Input createInput()
  {
    return new InputImpl();
  }

  /**
   * Create a new message.
   *
   * @return the newly created message
   */
  public Message createMessage()
  {
    return new MessageImpl();
  }

  /**
   * Create a new operation.
   *
   * @return the newly created operation
   */
  public Operation createOperation()
  {
    return new OperationImpl();
  }

  /**
   * Create a new output.
   *
   * @return the newly created output
   */
  public Output createOutput()
  {
    return new OutputImpl();
  }

  /**
   * Create a new part.
   *
   * @return the newly created part
   */
  public Part createPart()
  {
    return new PartImpl();
  }

  /**
   * Create a new port.
   *
   * @return the newly created port
   */
  public Port createPort()
  {
    return new PortImpl();
  }

  /**
   * Create a new port type.
   *
   * @return the newly created port type
   */
  public PortType createPortType()
  {
    return new PortTypeImpl();
  }

  /**
   * Create a new service.
   *
   * @return the newly created service
   */
  public Service createService()
  {
    return new ServiceImpl();
  }

  /**
   * Create a new types section.
   *
   * @return the newly created types section
   */
  public Types createTypes()
  {
    return new TypesImpl();
  }

  /**
   * Set the ExtensionRegistry for this Definition.
   */
  public void setExtensionRegistry(ExtensionRegistry extReg)
  {
    this.extReg = extReg;
  }

  /**
   * Get a reference to the ExtensionRegistry for this Definition.
   */
  public ExtensionRegistry getExtensionRegistry()
  {
    return extReg;
  }

  private Object getFromImports(String typeOfDefinition, QName name)
  {
    Object ret = null;
    List importList = getImports(name.getNamespaceURI());

    if (importList != null)
    {
      Iterator importIterator = importList.iterator();

      while (importIterator.hasNext())
      {
        Import importDef = (Import)importIterator.next();

        if (importDef != null)
        {
          Definition importedDef = importDef.getDefinition();
    
          if (importedDef != null)
          {
            /*
              These object comparisons will work fine because
              this private method is only called from within
              this class, using only the pre-defined constants
              from the Constants class as the typeOfDefinition
              argument.
            */
            if (typeOfDefinition == Constants.ELEM_SERVICE)
            {
              ret = importedDef.getService(name);
            }
            else if (typeOfDefinition == Constants.ELEM_MESSAGE)
            {
              ret = importedDef.getMessage(name);
            }
            else if (typeOfDefinition == Constants.ELEM_BINDING)
            {
              ret = importedDef.getBinding(name);
            }
            else if (typeOfDefinition == Constants.ELEM_PORT_TYPE)
            {
              ret = importedDef.getPortType(name);
            }

            if (ret != null)
            {
              return ret;
            }
          }
        }
      }
    }

    return ret;
  }

  public String toString()
  {
    StringBuffer strBuf = new StringBuffer();

    strBuf.append("Definition: name=" + name +
                  " targetNamespace=" + targetNamespace);

    if (imports != null)
    {
      Iterator importIterator = imports.values().iterator();

      while (importIterator.hasNext())
      {
        strBuf.append("\n" + importIterator.next());
      }
    }

    if (types != null)
    {
      strBuf.append("\n" + types);
    }

    if (messages != null)
    {
      Iterator msgsIterator = messages.values().iterator();

      while (msgsIterator.hasNext())
      {
        strBuf.append("\n" + msgsIterator.next());
      }
    }

    if (portTypes != null)
    {
      Iterator portTypeIterator = portTypes.values().iterator();

      while (portTypeIterator.hasNext())
      {
        strBuf.append("\n" + portTypeIterator.next());
      }
    }

    if (bindings != null)
    {
      Iterator bindingIterator = bindings.values().iterator();

      while (bindingIterator.hasNext())
      {
        strBuf.append("\n" + bindingIterator.next());
      }
    }

    if (services != null)
    {
      Iterator serviceIterator = services.values().iterator();

      while (serviceIterator.hasNext())
      {
        strBuf.append("\n" + serviceIterator.next());
      }
    }

    return strBuf.toString();
  }
}