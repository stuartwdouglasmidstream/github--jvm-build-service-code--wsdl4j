package com.ibm.wsdl.xml;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.wsdl.factory.*;
import javax.wsdl.xml.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.util.*;
import com.ibm.wsdl.util.xml.*;

/**
 * This interface describes a collection of methods
 * that enable conversion of a WSDL document (in XML,
 * following the WSDL schema described in the WSDL
 * specification) into a WSDL model.
 *
 * @author Matthew J. Duftler
 * @author Nirmal Mukhi
 */
public class WSDLReaderImpl implements WSDLReader
{
  // Used for determining the style of operations.
  private static final List STYLE_ONE_WAY =
    Arrays.asList(new String[]{Constants.ELEM_INPUT});
  private static final List STYLE_REQUEST_RESPONSE =
    Arrays.asList(new String[]{Constants.ELEM_INPUT, Constants.ELEM_OUTPUT});
  private static final List STYLE_SOLICIT_RESPONSE =
    Arrays.asList(new String[]{Constants.ELEM_OUTPUT, Constants.ELEM_INPUT});
  private static final List STYLE_NOTIFICATION =
    Arrays.asList(new String[]{Constants.ELEM_OUTPUT});

  private boolean verbose = true;
  private boolean importDocuments = true;
  private ExtensionRegistry extReg = null;
  private String factoryImplName = null;

  public void setVerbose(boolean verbose)
  {
    this.verbose = verbose;
  }

  public boolean getVerbose()
  {
    return verbose;
  }

  public void setImportDocuments(boolean importDocuments)
  {
    this.importDocuments = importDocuments;
  }

  public boolean getImportDocuments()
  {
    return importDocuments;
  }

  /**
   * Set the extension registry to be used when reading
   * WSDL documents into a WSDL definition. If an
   * extension registry is set, that is the extension
   * registry that will be set as the extensionRegistry
   * property of the definitions resulting from invoking
   * readWSDL(...). Default is null.
   *
   * @param extReg the extension registry to use for new
   * definitions
   */
  public void setExtensionRegistry(ExtensionRegistry extReg)
  {
    this.extReg = extReg;
  }

  /**
   * Get the extension registry, if one was set. Default is
   * null.
   */
  public ExtensionRegistry getExtensionRegistry()
  {
    return extReg;
  }

  /**
   * Set a different factory implementation to use for
   * creating definitions when reading WSDL documents.
   * As some WSDLReader implementations may only be
   * capable of creating definitions using the same
   * factory implementation from which the reader was
   * obtained, this method is optional. Default is null.
   *
   * @param factoryImplName the fully-qualified class name of the
   * class which provides a concrete implementation of the abstract
   * class WSDLFactory.
   * @throws UnsupportedOperationException if this method
   * is invoked on an implementation which does not
   * support it.
   */
  public void setFactoryImplName(String factoryImplName)
    throws UnsupportedOperationException
  {
    this.factoryImplName = factoryImplName;
  }

  /**
   * Get the factoryImplName, if one was set. Default is null.
   */
  public String getFactoryImplName()
  {
    return factoryImplName;
  }

  private Definition parseDefinitions(String documentBaseURI,
                                      Element defEl,
                                      Map importedDefs)
                                        throws WSDLException
  {
    checkElementName(defEl, Constants.Q_ELEM_DEFINITIONS);

    WSDLFactory factory = (factoryImplName != null)
                          ? WSDLFactory.newInstance(factoryImplName)
                          : WSDLFactory.newInstance();
    Definition def = factory.newDefinition();

    if (extReg != null)
    {
      def.setExtensionRegistry(extReg);
    }

    String name = DOMUtils.getAttribute(defEl, Constants.ATTR_NAME);
    String targetNamespace = DOMUtils.getAttribute(defEl,
                                             Constants.ATTR_TARGET_NAMESPACE);
    NamedNodeMap attrs = defEl.getAttributes();

    if (documentBaseURI != null)
    {
      def.setDocumentBaseURI(documentBaseURI);
    }

    if (name != null)
    {
      def.setQName(new QName(targetNamespace, name));
    }

    if (targetNamespace != null)
    {
      def.setTargetNamespace(targetNamespace);
    }

    int size = attrs.getLength();

    for (int i = 0; i < size; i++)
    {
      Attr attr = (Attr)attrs.item(i);
      String namespaceURI = attr.getNamespaceURI();
      String localPart = attr.getLocalName();
      String value = attr.getValue();

      if (namespaceURI != null && namespaceURI.equals(Constants.NS_URI_XMLNS))
      {
        if (localPart != null && !localPart.equals(Constants.ATTR_XMLNS))
        {
          def.addNamespace(localPart, value);
        }
        else
        {
          def.addNamespace(null, value);
        }
      }
    }

    Element tempEl = DOMUtils.getFirstChildElement(defEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_IMPORT.matches(tempEl))
      {
        if (importedDefs == null)
        {
          importedDefs = new Hashtable();
        }

        if (documentBaseURI != null)
        {
          importedDefs.put(documentBaseURI, def);
        }

        def.addImport(parseImport(tempEl, def, importedDefs));
      }
      else if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        def.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_TYPES.matches(tempEl))
      {
        def.setTypes(parseTypes(tempEl, def));
      }
      else if (Constants.Q_ELEM_MESSAGE.matches(tempEl))
      {
        def.addMessage(parseMessage(tempEl, def));
      }
      else if (Constants.Q_ELEM_PORT_TYPE.matches(tempEl))
      {
        def.addPortType(parsePortType(tempEl, def));
      }
      else if (Constants.Q_ELEM_BINDING.matches(tempEl))
      {
        def.addBinding(parseBinding(tempEl, def));
      }
      else if (Constants.Q_ELEM_SERVICE.matches(tempEl))
      {
        def.addService(parseService(tempEl, def));
      }
      else
      {
        def.addExtensibilityElement(
          parseExtensibilityElement(Definition.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return def;
  }

  private Import parseImport(Element importEl,
                             Definition def,
                             Map importedDefs)
                               throws WSDLException
  {
    Import importDef = def.createImport();
    String namespaceURI = DOMUtils.getAttribute(importEl,
                                                Constants.ATTR_NAMESPACE);
    String locationURI = DOMUtils.getAttribute(importEl,
                                               Constants.ATTR_LOCATION);

    if (namespaceURI != null)
    {
      importDef.setNamespaceURI(namespaceURI);
    }

    if (locationURI != null)
    {
      importDef.setLocationURI(locationURI);

      if (importDocuments)
      {
        try
        {
          String contextURI = def.getDocumentBaseURI();
          URL contextURL = (contextURI != null)
                           ? StringUtils.getURL(null, contextURI)
                           : null;
          URL url = StringUtils.getURL(contextURL, locationURI);
          Definition importedDef =
            (Definition)importedDefs.get(url.toString());

          if (importedDef == null)
          {
            Reader reader = StringUtils.getContentAsReader(url);
            InputSource inputSource = new InputSource(reader);
            Document doc = getDocument(inputSource, locationURI);
            Element documentElement = doc.getDocumentElement();

            /*
              Check if it's a wsdl document.
              If it's not, don't retrieve and process it.
              This should later be extended to allow other types of
              documents to be retrieved and processed, such as schema
              documents (".xsd"), etc...
            */
            if (Constants.Q_ELEM_DEFINITIONS.matches(documentElement))
            {
              if (verbose)
              {
                System.out.println("Retrieving document at '" + locationURI +
                                   "'" +
                                   (contextURI == null
                                    ? "."
                                    : ", relative to '" + contextURI + "'."));
              }

              importedDef = readWSDL(url.toString(),
                                     documentElement,
                                     importedDefs);
            }
          }

          if (importedDef != null)
          {
            importDef.setDefinition(importedDef);
          }
        }
        catch (WSDLException e)
        {
          e.setLocation(XPathUtils.getXPathExprFromNode(importEl));

          throw e;
        }
        catch (Throwable t)
        {
          throw new WSDLException(WSDLException.OTHER_ERROR,
                                  "Unable to resolve imported document at '" +
                                  locationURI + "'.", t);
        }
      }
    }

    Element tempEl = DOMUtils.getFirstChildElement(importEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        importDef.setDocumentationElement(tempEl);
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return importDef;
  }

  private static Types parseTypes(Element typesEl, Definition def)
    throws WSDLException
  {
    Types types = def.createTypes();
    Element tempEl = DOMUtils.getFirstChildElement(typesEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        types.setDocumentationElement(tempEl);
      }
      else
      {
        types.addExtensibilityElement(
          parseExtensibilityElement(Types.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return types;
  }

  private static Binding parseBinding(Element bindingEl, Definition def)
    throws WSDLException
  {
    Binding binding = null;
    String name = DOMUtils.getAttribute(bindingEl, Constants.ATTR_NAME);
    QName portTypeName =
      DOMUtils.getQualifiedAttributeValue(bindingEl,
                                          Constants.ATTR_TYPE,
                                          Constants.ELEM_BINDING,
                                          false);
    PortType portType = null;

    if (name != null)
    {
      QName bindingName = new QName(def.getTargetNamespace(), name);

      binding = def.getBinding(bindingName);

      if (binding == null)
      {
        binding = def.createBinding();
        binding.setQName(bindingName);
      }
    }
    else
    {
      binding = def.createBinding();
    }

    // Whether it was retrieved or created, the definition has been found.
    binding.setUndefined(false);

    if (portTypeName != null)
    {
      portType = def.getPortType(portTypeName);

      if (portType == null)
      {
        portType = def.createPortType();
        portType.setQName(portTypeName);
        def.addPortType(portType);
      }

      binding.setPortType(portType);
    }

    Element tempEl = DOMUtils.getFirstChildElement(bindingEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        binding.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_OPERATION.matches(tempEl))
      {
        binding.addBindingOperation(parseBindingOperation(tempEl,
                                                          portType,
                                                          def));
      }
      else
      {
        binding.addExtensibilityElement(parseExtensibilityElement(
          Binding.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return binding;
  }

  private static BindingOperation parseBindingOperation(
    Element bindingOperationEl, PortType portType, Definition def)
      throws WSDLException
  {
    BindingOperation bindingOperation = def.createBindingOperation();
    String name = DOMUtils.getAttribute(bindingOperationEl,
                                        Constants.ATTR_NAME);

    if (name != null)
    {
      bindingOperation.setName(name);
    }

    Element tempEl = DOMUtils.getFirstChildElement(bindingOperationEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        bindingOperation.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_INPUT.matches(tempEl))
      {
        bindingOperation.setBindingInput(parseBindingInput(tempEl, def));
      }
      else if (Constants.Q_ELEM_OUTPUT.matches(tempEl))
      {
        bindingOperation.setBindingOutput(parseBindingOutput(tempEl, def));
      }
      else if (Constants.Q_ELEM_FAULT.matches(tempEl))
      {
        bindingOperation.addBindingFault(parseBindingFault(tempEl, def));
      }
      else
      {
        bindingOperation.addExtensibilityElement(
          parseExtensibilityElement(BindingOperation.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    if (portType != null)
    {
      /*
        This needs some additional work. The (optional) names of the
        input and output elements also need to be taken into account.
      */
      Operation op = portType.getOperation(name, null, null);

      if (op == null)
      {
        op = def.createOperation();
        op.setName(name);
        portType.addOperation(op);
      }

      bindingOperation.setOperation(op);
    }

    return bindingOperation;
  }

  private static BindingInput parseBindingInput(Element bindingInputEl,
                                                Definition def)
                                                  throws WSDLException
  {
    BindingInput bindingInput = def.createBindingInput();
    String name = DOMUtils.getAttribute(bindingInputEl,
                                        Constants.ATTR_NAME);

    if (name != null)
    {
      bindingInput.setName(name);
    }

    Element tempEl = DOMUtils.getFirstChildElement(bindingInputEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        bindingInput.setDocumentationElement(tempEl);
      }
      else
      {
        bindingInput.addExtensibilityElement(
          parseExtensibilityElement(BindingInput.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return bindingInput;
  }

  private static BindingOutput parseBindingOutput(Element bindingOutputEl,
                                                  Definition def)
                                                    throws WSDLException
  {
    BindingOutput bindingOutput = def.createBindingOutput();
    String name = DOMUtils.getAttribute(bindingOutputEl,
                                        Constants.ATTR_NAME);

    if (name != null)
    {
      bindingOutput.setName(name);
    }

    Element tempEl = DOMUtils.getFirstChildElement(bindingOutputEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        bindingOutput.setDocumentationElement(tempEl);
      }
      else
      {
        bindingOutput.addExtensibilityElement(
          parseExtensibilityElement(BindingOutput.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return bindingOutput;
  }

  private static BindingFault parseBindingFault(Element bindingFaultEl,
                                                Definition def)
                                                  throws WSDLException
  {
    BindingFault bindingFault = def.createBindingFault();
    String name = DOMUtils.getAttribute(bindingFaultEl,
                                        Constants.ATTR_NAME);

    if (name != null)
    {
      bindingFault.setName(name);
    }

    Element tempEl = DOMUtils.getFirstChildElement(bindingFaultEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        bindingFault.setDocumentationElement(tempEl);
      }
      else
      {
        bindingFault.addExtensibilityElement(
          parseExtensibilityElement(BindingFault.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return bindingFault;
  }

  private static Message parseMessage(Element msgEl, Definition def)
    throws WSDLException
  {
    Message msg = null;
    String name = DOMUtils.getAttribute(msgEl, Constants.ATTR_NAME);

    if (name != null)
    {
      QName messageName = new QName(def.getTargetNamespace(), name);

      msg = def.getMessage(messageName);

      if (msg == null)
      {
        msg = def.createMessage();
        msg.setQName(messageName);
      }
    }
    else
    {
      msg = def.createMessage();
    }

    // Whether it was retrieved or created, the definition has been found.
    msg.setUndefined(false);

    Element tempEl = DOMUtils.getFirstChildElement(msgEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        msg.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_PART.matches(tempEl))
      {
        msg.addPart(parsePart(tempEl, def));
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return msg;
  }

  private static Part parsePart(Element partEl, Definition def)
    throws WSDLException
  {
    Part part = def.createPart();
    String name = DOMUtils.getAttribute(partEl, Constants.ATTR_NAME);
    QName elementName =
      DOMUtils.getQualifiedAttributeValue(partEl,
                                          Constants.ATTR_ELEMENT,
                                          Constants.ELEM_MESSAGE,
                                          false);
    QName typeName =
      DOMUtils.getQualifiedAttributeValue(partEl,
                                          Constants.ATTR_TYPE,
                                          Constants.ELEM_MESSAGE,
                                          false);

    if (name != null)
    {
      part.setName(name);
    }

    if (elementName != null)
    {
      part.setElementName(elementName);
    }

    if (typeName != null)
    {
      part.setTypeName(typeName);
    }

    Element tempEl = DOMUtils.getFirstChildElement(partEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        part.setDocumentationElement(tempEl);
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    // Need to do something here to locate part definition.

    return part;
  }

  private static PortType parsePortType(Element portTypeEl, Definition def)
    throws WSDLException
  {
    PortType portType = null;
    String name = DOMUtils.getAttribute(portTypeEl, Constants.ATTR_NAME);

    if (name != null)
    {
      QName portTypeName = new QName(def.getTargetNamespace(), name);

      portType = def.getPortType(portTypeName);

      if (portType == null)
      {
        portType = def.createPortType();
        portType.setQName(portTypeName);
      }
    }
    else
    {
      portType = def.createPortType();
    }

    // Whether it was retrieved or created, the definition has been found.
    portType.setUndefined(false);

    Element tempEl = DOMUtils.getFirstChildElement(portTypeEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        portType.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_OPERATION.matches(tempEl))
      {
        portType.addOperation(parseOperation(tempEl, portType, def));
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return portType;
  }

  private static Operation parseOperation(Element opEl,
                                          PortType portType,
                                          Definition def)
                                            throws WSDLException
  {
    Operation op = null;
    String name = DOMUtils.getAttribute(opEl, Constants.ATTR_NAME);
    String parameterOrderStr = DOMUtils.getAttribute(opEl,
                                              Constants.ATTR_PARAMETER_ORDER);

    if (name != null)
    {
      /*
        This needs some additional work. The (optional) names of the
        input and output elements also need to be taken into account.
      */
      op = portType.getOperation(name, null, null);

      if (op == null)
      {
        op = def.createOperation();
        op.setName(name);
      }
    }
    else
    {
      op = def.createOperation();
    }

    // Whether it was retrieved or created, the definition has been found.
    op.setUndefined(false);

    if (parameterOrderStr != null)
    {
      op.setParameterOrdering(StringUtils.parseNMTokens(parameterOrderStr));
    }

    Element tempEl = DOMUtils.getFirstChildElement(opEl);
    List messageOrder = new Vector();

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        op.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_INPUT.matches(tempEl))
      {
        op.setInput(parseInput(tempEl, def));
        messageOrder.add(Constants.ELEM_INPUT);
      }
      else if (Constants.Q_ELEM_OUTPUT.matches(tempEl))
      {
        op.setOutput(parseOutput(tempEl, def));
        messageOrder.add(Constants.ELEM_OUTPUT);
      }
      else if (Constants.Q_ELEM_FAULT.matches(tempEl))
      {
        op.addFault(parseFault(tempEl, def));
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    OperationType style = null;

    if (messageOrder.equals(STYLE_ONE_WAY))
    {
      style = OperationType.ONE_WAY;
    }
    else if (messageOrder.equals(STYLE_REQUEST_RESPONSE))
    {
      style = OperationType.REQUEST_RESPONSE;
    }
    else if (messageOrder.equals(STYLE_SOLICIT_RESPONSE))
    {
      style = OperationType.SOLICIT_RESPONSE;
    }
    else if (messageOrder.equals(STYLE_NOTIFICATION))
    {
      style = OperationType.NOTIFICATION;
    }

    if (style != null)
    {
      op.setStyle(style);
    }

    return op;
  }

  private static Service parseService(Element serviceEl, Definition def)
    throws WSDLException
  {
    Service service = def.createService();
    String name = DOMUtils.getAttribute(serviceEl, Constants.ATTR_NAME);

    if (name != null)
    {
      service.setQName(new QName(def.getTargetNamespace(), name));
    }

    Element tempEl = DOMUtils.getFirstChildElement(serviceEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        service.setDocumentationElement(tempEl);
      }
      else if (Constants.Q_ELEM_PORT.matches(tempEl))
      {
        service.addPort(parsePort(tempEl, def));
      }
      else
      {
        service.addExtensibilityElement(
          parseExtensibilityElement(Service.class, tempEl, def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return service;
  }

  private static Port parsePort(Element portEl, Definition def)
    throws WSDLException
  {
    Port port = def.createPort();
    String name = DOMUtils.getAttribute(portEl, Constants.ATTR_NAME);
    QName bindingStr = 
      DOMUtils.getQualifiedAttributeValue(portEl,
                                          Constants.ATTR_BINDING,
                                          Constants.ELEM_PORT,
                                          false);

    if (name != null)
    {
      port.setName(name);
    }

    if (bindingStr != null)
    {
      Binding binding = def.getBinding(bindingStr);

      if (binding == null)
      {
        binding = def.createBinding();
        binding.setQName(bindingStr);
        def.addBinding(binding);
      }

      port.setBinding(binding);
    }

    Element tempEl = DOMUtils.getFirstChildElement(portEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        port.setDocumentationElement(tempEl);
      }
      else
      {
        port.addExtensibilityElement(parseExtensibilityElement(Port.class,
                                                               tempEl,
                                                               def));
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return port;
  }

  private static ExtensibilityElement parseExtensibilityElement(
    Class parentType, Element el, Definition def) throws WSDLException
  {
    QName elementType = new QName(el);

    try
    {
      ExtensionRegistry extReg = def.getExtensionRegistry();

      if (extReg == null)
      {
        throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                                "No ExtensionRegistry set for this " +
                                "Definition, so unable to deserialize " +
                                "a '" + elementType + "' element in the " +
                                "context of a '" + parentType.getName() +
                                "'.");
      }

      ExtensionDeserializer extDS = extReg.queryDeserializer(parentType,
                                                             elementType);

      return extDS.unmarshall(parentType, elementType, el, def, extReg);
    }
    catch (WSDLException e)
    {
      if (e.getLocation() == null)
      {
        e.setLocation(XPathUtils.getXPathExprFromNode(el));
      }

      throw e;
    }
  }

  private static Input parseInput(Element inputEl, Definition def)
    throws WSDLException
  {
    Input input = def.createInput();
    String name = DOMUtils.getAttribute(inputEl, Constants.ATTR_NAME);
    QName messageName =
      DOMUtils.getQualifiedAttributeValue(inputEl,
                                          Constants.ATTR_MESSAGE,
                                          Constants.ELEM_INPUT,
                                          false);

    if (name != null)
    {
      input.setName(name);
    }

    if (messageName != null)
    {
      Message message = def.getMessage(messageName);

      if (message == null)
      {
        message = def.createMessage();
        message.setQName(messageName);
        def.addMessage(message);
      }

      input.setMessage(message);
    }

    Element tempEl = DOMUtils.getFirstChildElement(inputEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        input.setDocumentationElement(tempEl);
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return input;
  }

  private static Output parseOutput(Element outputEl, Definition def)
    throws WSDLException
  {
    Output output = def.createOutput();
    String name = DOMUtils.getAttribute(outputEl, Constants.ATTR_NAME);
    QName messageName =
      DOMUtils.getQualifiedAttributeValue(outputEl,
                                          Constants.ATTR_MESSAGE,
                                          Constants.ELEM_INPUT,
                                          false);

    if (name != null)
    {
      output.setName(name);
    }

    if (messageName != null)
    {
      Message message = def.getMessage(messageName);

      if (message == null)
      {
        message = def.createMessage();
        message.setQName(messageName);
        def.addMessage(message);
      }

      output.setMessage(message);
    }

    Element tempEl = DOMUtils.getFirstChildElement(outputEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        output.setDocumentationElement(tempEl);
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return output;
  }

  private static Fault parseFault(Element faultEl, Definition def)
    throws WSDLException
  {
    Fault fault = def.createFault();
    String name = DOMUtils.getAttribute(faultEl, Constants.ATTR_NAME);
    QName messageName =
      DOMUtils.getQualifiedAttributeValue(faultEl,
                                          Constants.ATTR_MESSAGE,
                                          Constants.ELEM_INPUT,
                                          false);

    if (name != null)
    {
      fault.setName(name);
    }

    if (messageName != null)
    {
      Message message = def.getMessage(messageName);

      if (message == null)
      {
        message = def.createMessage();
        message.setQName(messageName);
        def.addMessage(message);
      }

      fault.setMessage(message);
    }

    Element tempEl = DOMUtils.getFirstChildElement(faultEl);

    while (tempEl != null)
    {
      if (Constants.Q_ELEM_DOCUMENTATION.matches(tempEl))
      {
        fault.setDocumentationElement(tempEl);
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return fault;
  }

  private static void checkElementName(Element el, QName qname)
    throws WSDLException
  {
    if (!qname.matches(el))
    {
      WSDLException wsdlExc = new WSDLException(WSDLException.INVALID_WSDL,
                                                "Expected element '" +
                                                qname + "'.");

      wsdlExc.setLocation(XPathUtils.getXPathExprFromNode(el));

      throw wsdlExc;
    }
  }

  private static Document getDocument(InputSource inputSource,
                                      String desc) throws WSDLException
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    factory.setNamespaceAware(true);
    factory.setValidating(false);

    try
    {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(inputSource);

      return doc;
    }
    catch (Throwable t)
    {
      throw new WSDLException(WSDLException.PARSER_ERROR,
                              "Problem parsing '" + desc + "'.",
                              t);
    }
  }

  /**
   * Read the WSDL document accessible via the specified
   * URI into a WSDL definition.
   *
   * @param contextURI the context in which to resolve the
   * wsdlURI, if the wsdlURI is relative. Can be null, in which
   * case it will be ignored.
   * @param wsdlURI a URI (can be a filename or URL) pointing to a
   * WSDL XML definition.
   * @return the definition.
   */
  public Definition readWSDL(String contextURI, String wsdlURI)
    throws WSDLException
  {
    try
    {
      if (verbose)
      {
        System.out.println("Retrieving document at '" + wsdlURI + "'" +
                           (contextURI == null
                            ? "."
                            : ", relative to '" + contextURI + "'."));
      }

      URL contextURL = (contextURI != null)
                       ? StringUtils.getURL(null, contextURI)
                       : null;
      URL url = StringUtils.getURL(contextURL, wsdlURI);
      Reader reader = StringUtils.getContentAsReader(url);
      InputSource inputSource = new InputSource(reader);
      Document doc = getDocument(inputSource, wsdlURI);
      Definition def = readWSDL(url.toString(), doc);

      return def;
    }
    catch (WSDLException e)
    {
      throw e;
    }
    catch (Throwable t)
    {
      throw new WSDLException(WSDLException.OTHER_ERROR,
                              "Unable to resolve imported document at '" +
                              wsdlURI + "'.", t);
    }
  }

  /**
   * Read the specified &lt;wsdl:definitions&gt; element into a WSDL
   * definition.
   *
   * @param documentBaseURI the document base URI of the WSDL definition
   * described by the element. Will be set as the documentBaseURI
   * of the returned Definition. Can be null, in which case it
   * will be ignored.
   * @param definitionsElement the &lt;wsdl:definitions&gt; element
   * @return the definition described by the element.
   */
  public Definition readWSDL(String documentBaseURI,
                             Element definitionsElement)
                               throws WSDLException
  {
    return readWSDL(documentBaseURI, definitionsElement, null);
  }

  private Definition readWSDL(String documentBaseURI,
                              Element definitionsElement,
                              Map importedDefs)
                                throws WSDLException
  {
    return parseDefinitions(documentBaseURI, definitionsElement, importedDefs);
  }

  /**
   * Read the specified WSDL document into a WSDL definition.
   *
   * @param documentBaseURI the document base URI of the WSDL definition
   * described by the document. Will be set as the documentBaseURI
   * of the returned Definition. Can be null, in which case it
   * will be ignored.
   * @param wsdlDocument the WSDL document, an XML 
   * document obeying the WSDL schema.
   * @return the definition described in the document.
   */
  public Definition readWSDL(String documentBaseURI, Document wsdlDocument)
    throws WSDLException
  {
    return readWSDL(documentBaseURI, wsdlDocument.getDocumentElement());
  }

  /**
   * Read a WSDL document into a WSDL definition.
   *
   * @param documentBaseURI the document base URI of the WSDL definition
   * described by the document. Will be set as the documentBaseURI
   * of the returned Definition. Can be null, in which case it
   * will be ignored.
   * @param inputSource an InputSource pointing to the
   * WSDL document, an XML document obeying the WSDL schema.
   * @return the definition described in the document pointed to
   * by the InputSource.
   */
  public Definition readWSDL(String documentBaseURI, InputSource inputSource)
    throws WSDLException
  {
    return readWSDL(documentBaseURI,
                    getDocument(inputSource, "- WSDL Document -"));
  }
}