package com.ibm.wsdl.xml;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.namespace.*;
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
 * This class describes a collection of methods
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

  protected boolean verbose = true;
  protected boolean importDocuments = true;
  protected ExtensionRegistry extReg = null;
  protected String factoryImplName = null;
  protected WSDLLocator loc = null;

  /**
   * Sets the specified feature to the specified value.
   * <p>
   * The supported features are:
   * <p>
   * <table border=1>
   *   <tr>
   *     <th>Name</th>
   *     <th>Description</th>
   *     <th>Default Value</th>
   *   </tr>
   *   <tr>
   *     <td><center>javax.wsdl.verbose</center></td>
   *     <td>If set to true, status messages will be displayed.</td>
   *     <td><center>true</center></td>
   *   </tr>
   *   <tr>
   *     <td><center>javax.wsdl.importDocuments</center></td>
   *     <td>If set to true, imported WSDL documents will be
   *         retrieved and processed.</td>
   *     <td><center>true</center></td>
   *   </tr>
   * </table>
   * <p>
   * All feature names must be fully-qualified, Java package style. All
   * names starting with javax.wsdl. are reserved for features defined
   * by the JWSDL specification. It is recommended that implementation-
   * specific features be fully-qualified to match the package name
   * of that implementation. For example: com.abc.featureName
   *
   * @param name the name of the feature to be set.
   * @param value the value to set the feature to.
   * @throws IllegalArgumentException if the feature name is not recognized.
   * @see #getFeature(String)
   */
  public void setFeature(String name, boolean value)
    throws IllegalArgumentException
  {
    if (name == null)
    {
      throw new IllegalArgumentException("Feature name must not be null.");
    }

    if (name.equals(Constants.FEATURE_VERBOSE))
    {
      verbose = value;
    }
    else if (name.equals(Constants.FEATURE_IMPORT_DOCUMENTS))
    {
      importDocuments = value;
    }
    else
    {
      throw new IllegalArgumentException("Feature name '" + name +
                                         "' not recognized.");
    }
  }

  /**
   * Gets the value of the specified feature.
   *
   * @param name the name of the feature to get the value of.
   * @throws IllegalArgumentException if the feature name is not recognized.
   * @see #setFeature(String, boolean)
   */
  public boolean getFeature(String name) throws IllegalArgumentException
  {
    if (name == null)
    {
      throw new IllegalArgumentException("Feature name must not be null.");
    }

    if (name.equals(Constants.FEATURE_VERBOSE))
    {
      return verbose;
    }
    else if (name.equals(Constants.FEATURE_IMPORT_DOCUMENTS))
    {
      return importDocuments;
    }
    else
    {
      throw new IllegalArgumentException("Feature name '" + name +
                                         "' not recognized.");
    }
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

  protected Definition parseDefinitions(String documentBaseURI,
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
      if (QNameUtils.matches(Constants.Q_ELEM_IMPORT, tempEl))
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
      else if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        def.setDocumentationElement(tempEl);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_TYPES, tempEl))
      {
        def.setTypes(parseTypes(tempEl, def));
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_MESSAGE, tempEl))
      {
        def.addMessage(parseMessage(tempEl, def));
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_PORT_TYPE, tempEl))
      {
        def.addPortType(parsePortType(tempEl, def));
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_BINDING, tempEl))
      {
        def.addBinding(parseBinding(tempEl, def));
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_SERVICE, tempEl))
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

  protected Import parseImport(Element importEl,
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
          Definition importedDef = null;
          InputStream inputStream = null;
          InputSource inputSource = null;
          URL url = null;

          if (loc != null)
          {
             inputSource = loc.getImportInputSource(contextURI, locationURI);

             /*
               We now have available the latest import URI. This might
               differ from the locationURI so check the importedDefs for it
               since it is this that we pass as the documentBaseURI later.
             */
             String liu = loc.getLatestImportURI();

             importedDef = (Definition)importedDefs.get(liu);
          }
          else
          {
            URL contextURL = (contextURI != null)
                             ? StringUtils.getURL(null, contextURI)
                             : null;

            url = StringUtils.getURL(contextURL, locationURI);
            importedDef = (Definition)importedDefs.get(url.toString());

            if (importedDef == null)
            {
              inputStream = StringUtils.getContentAsInputStream(url);

              if (inputStream != null)
              {
                inputSource = new InputSource(inputStream);
              }
            }
          }

          if (importedDef == null)
          {
            if (inputSource == null)
            {
              throw new WSDLException(WSDLException.OTHER_ERROR,
                                      "Unable to locate imported document " +
                                      "at '" + locationURI + "'" +
                                      (contextURI == null
                                       ? "."
                                       : ", relative to '" + contextURI +
                                         "'."));
            }

            Document doc = getDocument(inputSource, locationURI);

            if (inputStream != null)
            {
              inputStream.close();
            }

            Element documentElement = doc.getDocumentElement();

            /*
              Check if it's a wsdl document.
              If it's not, don't retrieve and process it.
              This should later be extended to allow other types of
              documents to be retrieved and processed, such as schema
              documents (".xsd"), etc...
            */
            if (QNameUtils.matches(Constants.Q_ELEM_DEFINITIONS,
                                   documentElement))
            {
              if (verbose)
              {
                System.out.println("Retrieving document at '" + locationURI +
                                   "'" +
                                   (contextURI == null
                                    ? "."
                                    : ", relative to '" + contextURI + "'."));
              }

              String urlString =
                (loc != null)
                ? loc.getLatestImportURI()
                : (url != null)
                  ? url.toString()
                  : locationURI;

              importedDef = readWSDL(urlString,
                                     documentElement,
                                     importedDefs);
            }
            else
            {
              QName docElementQName = QNameUtils.newQName(documentElement);

              if (Constants.XSD_QNAME_LIST.contains(docElementQName))
              {
                WSDLFactory factory =
                  (factoryImplName != null)
                  ? WSDLFactory.newInstance(factoryImplName)
                  : WSDLFactory.newInstance();

                importedDef = factory.newDefinition();

                if (extReg != null)
                {
                  importedDef.setExtensionRegistry(extReg);
                }

                String urlString =
                  (loc != null)
                  ? loc.getLatestImportURI()
                  : (url != null)
                    ? url.toString()
                    : locationURI;

                importedDef.setDocumentBaseURI(urlString);

                Types types = importedDef.createTypes();
                UnknownExtensibilityElement unknownExt =
                  new UnknownExtensibilityElement();

                unknownExt.setElement(documentElement);
                types.addExtensibilityElement(unknownExt);
                importedDef.setTypes(types);
              }
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected Types parseTypes(Element typesEl, Definition def)
    throws WSDLException
  {
    Types types = def.createTypes();
    Element tempEl = DOMUtils.getFirstChildElement(typesEl);

    while (tempEl != null)
    {
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected Binding parseBinding(Element bindingEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        binding.setDocumentationElement(tempEl);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_OPERATION, tempEl))
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

  protected BindingOperation parseBindingOperation(
    Element bindingOperationEl,
    PortType portType,
    Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        bindingOperation.setDocumentationElement(tempEl);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_INPUT, tempEl))
      {
        bindingOperation.setBindingInput(parseBindingInput(tempEl, def));
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_OUTPUT, tempEl))
      {
        bindingOperation.setBindingOutput(parseBindingOutput(tempEl, def));
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_FAULT, tempEl))
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
      BindingInput bindingInput = bindingOperation.getBindingInput();
      BindingOutput bindingOutput = bindingOperation.getBindingOutput();
      String inputName =
        (bindingInput != null ? bindingInput.getName() : null);
      String outputName =
        (bindingOutput != null ? bindingOutput.getName() : null);
      Operation op = portType.getOperation(name, inputName, outputName);

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

  protected BindingInput parseBindingInput(Element bindingInputEl,
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected BindingOutput parseBindingOutput(Element bindingOutputEl,
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected BindingFault parseBindingFault(Element bindingFaultEl,
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected Message parseMessage(Element msgEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        msg.setDocumentationElement(tempEl);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_PART, tempEl))
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

  protected Part parsePart(Element partEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        part.setDocumentationElement(tempEl);
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    Map extensionAttributes = part.getExtensionAttributes();

    extensionAttributes.putAll(getPartAttributes(partEl, def));

    // Need to do something here to locate part definition.

    return part;
  }

  protected Map getPartAttributes(Element el,
                                  Definition def) throws WSDLException
  {
    Map attributes = new HashMap();
    NamedNodeMap nodeMap = el.getAttributes();
    int atts = nodeMap.getLength();

    for (int a = 0; a < atts; a++)
    {
      Attr attribute = (Attr)nodeMap.item(a);
      String lName = attribute.getLocalName();
      String nSpace = attribute.getNamespaceURI();
      String prefix = attribute.getPrefix();
      QName name = new QName(nSpace, lName);

      if (nSpace != null && !nSpace.equals(Constants.NS_URI_WSDL))
      {
        if (!nSpace.equals(Constants.NS_URI_XMLNS))
        {
          String strValue = attribute.getValue();
          QName qValue = null;

          try
          {
            qValue = DOMUtils.getQName(strValue, el);
          }
          catch (WSDLException e)
          {
            qValue = new QName(strValue);
          }

          attributes.put(name, qValue);

          String tempNSUri = def.getNamespace(prefix);

          while (tempNSUri != null && !tempNSUri.equals(nSpace))
          {
            prefix += "_";
            tempNSUri = def.getNamespace(prefix);
          }

          def.addNamespace(prefix, nSpace);
        }
      }
      else if (!lName.equals(Constants.ATTR_NAME)
               && !lName.equals(Constants.ATTR_ELEMENT)
               && !lName.equals(Constants.ATTR_TYPE))

      {
        WSDLException wsdlExc = new WSDLException(WSDLException.INVALID_WSDL,
                                                  "Encountered illegal " +
                                                  "part extension " +
                                                  "attribute '" +
                                                  name + "'. Extension " +
                                                  "attributes must be in " +
                                                  "a namespace other than " +
                                                  "WSDL's.");

        wsdlExc.setLocation(XPathUtils.getXPathExprFromNode(el));

        throw wsdlExc;
      }
    }

    return attributes;
  }

  protected PortType parsePortType(Element portTypeEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        portType.setDocumentationElement(tempEl);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_OPERATION, tempEl))
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

  protected Operation parseOperation(Element opEl,
                                     PortType portType,
                                     Definition def)
                                       throws WSDLException
  {
    Operation op = null;
    String name = DOMUtils.getAttribute(opEl, Constants.ATTR_NAME);
    String parameterOrderStr = DOMUtils.getAttribute(opEl,
                                              Constants.ATTR_PARAMETER_ORDER);
    Element tempEl = DOMUtils.getFirstChildElement(opEl);
    List messageOrder = new Vector();
    Element docEl = null;
    Input input = null;
    Output output = null;
    List faults = new Vector();

    while (tempEl != null)
    {
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        docEl = tempEl;
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_INPUT, tempEl))
      {
        input = parseInput(tempEl, def);
        messageOrder.add(Constants.ELEM_INPUT);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_OUTPUT, tempEl))
      {
        output = parseOutput(tempEl, def);
        messageOrder.add(Constants.ELEM_OUTPUT);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_FAULT, tempEl))
      {
        faults.add(parseFault(tempEl, def));
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    if (name != null)
    {
      String inputName = (input != null ? input.getName() : null);
      String outputName = (output != null ? output.getName() : null);

      op = portType.getOperation(name, inputName, outputName);

      if (op != null && !op.isUndefined())
      {
        op = null;
      }

      if (op != null)
      {
        if (inputName == null)
        {
          Input tempIn = op.getInput();

          if (tempIn != null)
          {
            if (tempIn.getName() != null)
            {
              op = null;
            }
          }
        }
      }

      if (op != null)
      {
        if (outputName == null)
        {
          Output tempOut = op.getOutput();

          if (tempOut != null)
          {
            if (tempOut.getName() != null)
            {
              op = null;
            }
          }
        }
      }

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

    if (docEl != null)
    {
      op.setDocumentationElement(docEl);
    }

    if (input != null)
    {
      op.setInput(input);
    }

    if (output != null)
    {
      op.setOutput(output);
    }

    if (faults.size() > 0)
    {
      Iterator faultIterator = faults.iterator();

      while (faultIterator.hasNext())
      {
        op.addFault((Fault)faultIterator.next());
      }
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

  protected Service parseService(Element serviceEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
      {
        service.setDocumentationElement(tempEl);
      }
      else if (QNameUtils.matches(Constants.Q_ELEM_PORT, tempEl))
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

  protected Port parsePort(Element portEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected ExtensibilityElement parseExtensibilityElement(
    Class parentType,
    Element el,
    Definition def)
      throws WSDLException
  {
    QName elementType = QNameUtils.newQName(el);

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

  protected Input parseInput(Element inputEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected Output parseOutput(Element outputEl, Definition def)
    throws WSDLException
  {
    Output output = def.createOutput();
    String name = DOMUtils.getAttribute(outputEl, Constants.ATTR_NAME);
    QName messageName =
      DOMUtils.getQualifiedAttributeValue(outputEl,
                                          Constants.ATTR_MESSAGE,
                                          Constants.ELEM_OUTPUT,
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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

  protected Fault parseFault(Element faultEl, Definition def)
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
      if (QNameUtils.matches(Constants.Q_ELEM_DOCUMENTATION, tempEl))
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
    if (!QNameUtils.matches(qname, el))
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
   * @param wsdlURI a URI (can be a filename or URL) pointing to a
   * WSDL XML definition.
   * @return the definition.
   */
  public Definition readWSDL(String wsdlURI) throws WSDLException
  {
    return readWSDL(null, wsdlURI);
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
      InputStream inputStream = StringUtils.getContentAsInputStream(url);
      InputSource inputSource = new InputSource(inputStream);
      Document doc = getDocument(inputSource, wsdlURI);

      inputStream.close();

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

  protected Definition readWSDL(String documentBaseURI,
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

  /**
   * Read a WSDL document into a WSDL definition.
   *
   * @param locator A WSDLLocator object used to provide InputSources
   * pointing to the wsdl file.
   * @return the definition described in the document
   */
  public Definition readWSDL(WSDLLocator locator) throws WSDLException
  {
    InputSource is = locator.getBaseInputSource();
    String base = locator.getBaseURI();

    if (is == null)
    {
      throw new WSDLException(WSDLException.OTHER_ERROR,
                              "Unable to locate document at '" + base + "'.");
    }

    this.loc = locator;

    if (verbose)
    {
      System.out.println("Retrieving document at '" + base + "'.");
    }

    return readWSDL(base, is);
  }
}