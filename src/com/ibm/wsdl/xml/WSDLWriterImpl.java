package com.ibm.wsdl.xml;

import java.io.*;
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
 * This class describes a collection of methods
 * that allow a WSDL model to be written to a writer
 * in an XML format that follows the WSDL schema.
 *
 * @author Matthew J. Duftler
 * @author Nirmal Mukhi
 */
public class WSDLWriterImpl implements WSDLWriter
{
  /**
   * Sets the specified feature to the specified value.
   * <p>
   * There are no minimum features that must be supported.
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
    else
    {
      throw new IllegalArgumentException("Feature name '" + name +
                                         "' not recognized.");
    }
  }

  private static void printDefinition(Definition def, PrintWriter pw)
    throws WSDLException
  {
    if (def == null)
    {
      return;
    }

    String tagName =
      DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                 Constants.ELEM_DEFINITIONS,
                                 def);

    pw.print('<' + tagName);

    QName name = def.getQName();
    String targetNamespace = def.getTargetNamespace();
    Map namespaces = def.getNamespaces();

    if (name != null)
    {
      DOMUtils.printAttribute(Constants.ATTR_NAME, name.getLocalPart(), pw);
    }

    DOMUtils.printAttribute(Constants.ATTR_TARGET_NAMESPACE,
                            targetNamespace,
                            pw);

    printNamespaceDeclarations(namespaces, pw);

    pw.println('>');

    printImports(def.getImports(), def, pw);
    printDocumentation(def.getDocumentationElement(), pw);
    printTypes(def.getTypes(), def, pw);
    printMessages(def.getMessages(), def, pw);
    printPortTypes(def.getPortTypes(), def, pw);
    printBindings(def.getBindings(), def, pw);
    printServices(def.getServices(), def, pw);

    List extElements = def.getExtensibilityElements();

    printExtensibilityElements(Definition.class, extElements, def, pw);

    pw.println("</" + tagName + '>');

    pw.flush();
  }

  private static void printServices(Map services,
                                    Definition def,
                                    PrintWriter pw)
                                      throws WSDLException
  {
    if (services != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_SERVICE,
                                   def);
      Iterator serviceIterator = services.values().iterator();

      while (serviceIterator.hasNext())
      {
        Service service = (Service)serviceIterator.next();

        pw.print("  <" + tagName);

        QName name = service.getQName();

        if (name != null)
        {
          DOMUtils.printAttribute(Constants.ATTR_NAME,
                                  name.getLocalPart(),
                                  pw);
        }

        pw.println('>');

        printDocumentation(service.getDocumentationElement(), pw);
        printPorts(service.getPorts(), def, pw);

        List extElements = service.getExtensibilityElements();

        printExtensibilityElements(Service.class, extElements, def, pw);

        pw.println("  </" + tagName + '>');
      }
    }
  }

  private static void printPorts(Map ports, Definition def, PrintWriter pw)
    throws WSDLException
  {
    if (ports != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_PORT,
                                   def);
      Iterator portIterator = ports.values().iterator();

      while (portIterator.hasNext())
      {
        Port port = (Port)portIterator.next();

        pw.print("    <" + tagName);

        DOMUtils.printAttribute(Constants.ATTR_NAME, port.getName(), pw);

        Binding binding = port.getBinding();

        if (binding != null)
        {
          DOMUtils.printQualifiedAttribute(Constants.ATTR_BINDING,
                                           binding.getQName(),
                                           def,
                                           pw);
        }

        pw.println('>');

        printDocumentation(port.getDocumentationElement(), pw);

        List extElements = port.getExtensibilityElements();

        printExtensibilityElements(Port.class, extElements, def, pw);

        pw.println("    </" + tagName + '>');
      }
    }
  }

  private static void printBindings(Map bindings,
                                    Definition def,
                                    PrintWriter pw)
                                      throws WSDLException
  {
    if (bindings != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_BINDING,
                                   def);
      Iterator bindingIterator = bindings.values().iterator();

      while (bindingIterator.hasNext())
      {
        Binding binding = (Binding)bindingIterator.next();

        if (!binding.isUndefined())
        {
          pw.print("  <" + tagName);

          QName name = binding.getQName();

          if (name != null)
          {
            DOMUtils.printAttribute(Constants.ATTR_NAME,
                                    name.getLocalPart(),
                                    pw);
          }

          PortType portType = binding.getPortType();

          if (portType != null)
          {
            DOMUtils.printQualifiedAttribute(Constants.ATTR_TYPE,
                                             portType.getQName(),
                                             def,
                                             pw);
          }

          pw.println('>');

          printDocumentation(binding.getDocumentationElement(), pw);

          List extElements = binding.getExtensibilityElements();

          printExtensibilityElements(Binding.class, extElements, def, pw);

          printBindingOperations(binding.getBindingOperations(), def, pw);

          pw.println("  </" + tagName + '>');
        }
      }
    }
  }

  private static void printBindingOperations(List bindingOperations,
                                             Definition def,
                                             PrintWriter pw)
                                               throws WSDLException
  {
    if (bindingOperations != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_OPERATION,
                                   def);
      Iterator bindingOperationIterator = bindingOperations.iterator();

      while (bindingOperationIterator.hasNext())
      {
        BindingOperation bindingOperation =
          (BindingOperation)bindingOperationIterator.next();

        pw.print("    <" + tagName);

        DOMUtils.printAttribute(Constants.ATTR_NAME,
                                bindingOperation.getName(),
                                pw);

        pw.println('>');

        printDocumentation(bindingOperation.getDocumentationElement(), pw);

        List extElements = bindingOperation.getExtensibilityElements();

        printExtensibilityElements(BindingOperation.class,
                                   extElements,
                                   def,
                                   pw);

        printBindingInput(bindingOperation.getBindingInput(), def, pw);
        printBindingOutput(bindingOperation.getBindingOutput(), def, pw);
        printBindingFaults(bindingOperation.getBindingFaults(), def, pw);

        pw.println("    </" + tagName + '>');
      }
    }
  }

  private static void printBindingInput(BindingInput bindingInput,
                                        Definition def,
                                        PrintWriter pw)
                                          throws WSDLException
  {
    if (bindingInput != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_INPUT,
                                   def);

      pw.print("      <" + tagName);

      DOMUtils.printAttribute(Constants.ATTR_NAME,
                              bindingInput.getName(),
                              pw);

      pw.println('>');

      printDocumentation(bindingInput.getDocumentationElement(), pw);

      List extElements = bindingInput.getExtensibilityElements();

      printExtensibilityElements(BindingInput.class, extElements, def, pw);

      pw.println("      </" + tagName + '>');
    }
  }

  private static void printBindingOutput(BindingOutput bindingOutput,
                                         Definition def,
                                         PrintWriter pw)
                                           throws WSDLException
  {
    if (bindingOutput != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_OUTPUT,
                                   def);

      pw.print("      <" + tagName);

      DOMUtils.printAttribute(Constants.ATTR_NAME,
                              bindingOutput.getName(),
                              pw);

      pw.println('>');

      printDocumentation(bindingOutput.getDocumentationElement(), pw);

      List extElements = bindingOutput.getExtensibilityElements();

      printExtensibilityElements(BindingOutput.class, extElements, def, pw);

      pw.println("      </" + tagName + '>');
    }
  }

  private static void printBindingFaults(Map bindingFaults,
                                         Definition def,
                                         PrintWriter pw)
                                           throws WSDLException
  {
    if (bindingFaults != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_FAULT,
                                   def);
      Iterator bindingFaultIterator = bindingFaults.values().iterator();

      while (bindingFaultIterator.hasNext())
      {
        BindingFault bindingFault = (BindingFault)bindingFaultIterator.next();

        pw.print("      <" + tagName);

        DOMUtils.printAttribute(Constants.ATTR_NAME,
                                bindingFault.getName(),
                                pw);

        pw.println('>');

        printDocumentation(bindingFault.getDocumentationElement(), pw);

        List extElements = bindingFault.getExtensibilityElements();

        printExtensibilityElements(BindingFault.class, extElements, def, pw);

        pw.println("      </" + tagName + '>');
      }
    }
  }

  private static void printPortTypes(Map portTypes,
                                     Definition def,
                                     PrintWriter pw)
                                       throws WSDLException
  {
    if (portTypes != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_PORT_TYPE,
                                   def);
      Iterator portTypeIterator = portTypes.values().iterator();

      while (portTypeIterator.hasNext())
      {
        PortType portType = (PortType)portTypeIterator.next();

        if (!portType.isUndefined())
        {
          pw.print("  <" + tagName);

          QName name = portType.getQName();

          if (name != null)
          {
            DOMUtils.printAttribute(Constants.ATTR_NAME,
                                    name.getLocalPart(),
                                    pw);
          }

          pw.println('>');

          printDocumentation(portType.getDocumentationElement(), pw);
          printOperations(portType.getOperations(), def, pw);

          pw.println("  </" + tagName + '>');
        }
      }
    }
  }

  private static void printOperations(List operations,
                                      Definition def,
                                      PrintWriter pw)
                                        throws WSDLException
  {
    if (operations != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_OPERATION,
                                   def);
      Iterator operationIterator = operations.iterator();

      while (operationIterator.hasNext())
      {
        Operation operation = (Operation)operationIterator.next();

        if (!operation.isUndefined())
        {
          pw.print("    <" + tagName);

          DOMUtils.printAttribute(Constants.ATTR_NAME,
                                  operation.getName(),
                                  pw);
          DOMUtils.printAttribute(Constants.ATTR_PARAMETER_ORDER,
                   StringUtils.getNMTokens(operation.getParameterOrdering()),
                   pw);

          pw.println('>');

          printDocumentation(operation.getDocumentationElement(), pw);

          OperationType operationType = operation.getStyle();

          if (operationType == OperationType.ONE_WAY)
          {
            printInput(operation.getInput(), def, pw);
          }
          else if (operationType == OperationType.SOLICIT_RESPONSE)
          {
            printOutput(operation.getOutput(), def, pw);
            printInput(operation.getInput(), def, pw);
          }
          else if (operationType == OperationType.NOTIFICATION)
          {
            printOutput(operation.getOutput(), def, pw);
          }
          else
          {
            // Must be OperationType.REQUEST_RESPONSE.
            printInput(operation.getInput(), def, pw);
            printOutput(operation.getOutput(), def, pw);
          }

          printFaults(operation.getFaults(), def, pw);

          pw.println("    </" + tagName + '>');
        }
      }
    }
  }

  private static void printInput(Input input,
                                 Definition def,
                                 PrintWriter pw)
                                   throws WSDLException
  {
    if (input != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_INPUT,
                                   def);

      pw.print("      <" + tagName);

      DOMUtils.printAttribute(Constants.ATTR_NAME, input.getName(), pw);

      Message message = input.getMessage();

      if (message != null)
      {
        DOMUtils.printQualifiedAttribute(Constants.ATTR_MESSAGE,
                                         message.getQName(),
                                         def,
                                         pw);
      }

      Element docEl = input.getDocumentationElement();

      if (docEl == null)
      {
        pw.println("/>");
      }
      else
      {
        pw.println('>');

        printDocumentation(docEl, pw);

        pw.println("      </" + tagName + '>');
      }
    }
  }

  private static void printOutput(Output output,
                                  Definition def,
                                  PrintWriter pw)
                                    throws WSDLException
  {
    if (output != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_OUTPUT,
                                   def);

      pw.print("      <" + tagName);

      DOMUtils.printAttribute(Constants.ATTR_NAME, output.getName(), pw);

      Message message = output.getMessage();

      if (message != null)
      {
        DOMUtils.printQualifiedAttribute(Constants.ATTR_MESSAGE,
                                         message.getQName(),
                                         def,
                                         pw);
      }

      Element docEl = output.getDocumentationElement();

      if (docEl == null)
      {
        pw.println("/>");
      }
      else
      {
        pw.println('>');

        printDocumentation(docEl, pw);

        pw.println("      </" + tagName + '>');
      }
    }
  }

  private static void printFaults(Map faults,
                                  Definition def,
                                  PrintWriter pw)
                                    throws WSDLException
  {
    if (faults != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_FAULT,
                                   def);
      Iterator faultIterator = faults.values().iterator();

      while (faultIterator.hasNext())
      {
        Fault fault = (Fault)faultIterator.next();

        pw.print("      <" + tagName);

        DOMUtils.printAttribute(Constants.ATTR_NAME, fault.getName(), pw);

        Message message = fault.getMessage();

        if (message != null)
        {
          DOMUtils.printQualifiedAttribute(Constants.ATTR_MESSAGE,
                                           message.getQName(),
                                           def,
                                           pw);
        }

        Element docEl = fault.getDocumentationElement();

        if (docEl == null)
        {
          pw.println("/>");
        }
        else
        {
          pw.println('>');

          printDocumentation(docEl, pw);

          pw.println("      </" + tagName + '>');
        }
      }
    }
  }

  private static void printMessages(Map messages,
                                    Definition def,
                                    PrintWriter pw)
                                      throws WSDLException
  {
    if (messages != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_MESSAGE,
                                   def);
      Iterator messageIterator = messages.values().iterator();

      while (messageIterator.hasNext())
      {
        Message message = (Message)messageIterator.next();

        if (!message.isUndefined())
        {
          pw.print("  <" + tagName);

          QName name = message.getQName();

          if (name != null)
          {
            DOMUtils.printAttribute(Constants.ATTR_NAME,
                                    name.getLocalPart(),
                                    pw);
          }

          pw.println('>');

          printDocumentation(message.getDocumentationElement(), pw);
          printParts(message.getOrderedParts(null), def, pw);

          pw.println("  </" + tagName + '>');
        }
      }
    }
  }

  private static void printParts(List parts, Definition def, PrintWriter pw)
    throws WSDLException
  {
    if (parts != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_PART,
                                   def);
      Iterator partIterator = parts.iterator();

      while (partIterator.hasNext())
      {
        Part part = (Part)partIterator.next();

        pw.print("    <" + tagName);

        DOMUtils.printAttribute(Constants.ATTR_NAME, part.getName(), pw);
        DOMUtils.printQualifiedAttribute(Constants.ATTR_ELEMENT,
                                         part.getElementName(),
                                         def,
                                         pw);
        DOMUtils.printQualifiedAttribute(Constants.ATTR_TYPE,
                                         part.getTypeName(),
                                         def,
                                         pw);

        Element docEl = part.getDocumentationElement();

        if (docEl == null)
        {
          pw.println("/>");
        }
        else
        {
          pw.println('>');

          printDocumentation(docEl, pw);

          pw.println("      </" + tagName + '>');
        }
      }
    }
  }

  private static void printDocumentation(Element docElement,
                                         PrintWriter pw)
                                           throws WSDLException
  {
    if (docElement != null)
    {
      DOM2Writer.serializeAsXML(docElement, pw);

      pw.println();
    }
  }

  private static void printTypes(Types types, Definition def, PrintWriter pw)
    throws WSDLException
  {
    if (types != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_TYPES,
                                   def);
      pw.println("  <" + tagName + '>');

      printDocumentation(types.getDocumentationElement(), pw);

      List extElements = types.getExtensibilityElements();

      printExtensibilityElements(Types.class, extElements, def, pw);

      pw.println("  </" + tagName + '>');
    }
  }

  private static void printImports(Map imports, Definition def, PrintWriter pw)
    throws WSDLException
  {
    if (imports != null)
    {
      String tagName =
        DOMUtils.getQualifiedValue(Constants.NS_URI_WSDL,
                                   Constants.ELEM_IMPORT,
                                   def);
      Iterator importListIterator = imports.values().iterator();

      while (importListIterator.hasNext())
      {
        List importList = (List)importListIterator.next();
        Iterator importIterator = importList.iterator();

        while (importIterator.hasNext())
        {
          Import importDef = (Import)importIterator.next();

          pw.print("  <" + tagName);

          DOMUtils.printAttribute(Constants.ATTR_NAMESPACE,
                                  importDef.getNamespaceURI(),
                                  pw);
          DOMUtils.printAttribute(Constants.ATTR_LOCATION,
                                  importDef.getLocationURI(),
                                  pw);

          Element docEl = importDef.getDocumentationElement();

          if (docEl == null)
          {
            pw.println("/>");
          }
          else
          {
            pw.println('>');

            printDocumentation(docEl, pw);

            pw.println("      </" + tagName + '>');
          }
        }
      }
    }
  }

  private static void printNamespaceDeclarations(Map namespaces,
                                                 PrintWriter pw)
                                                   throws WSDLException
  {
    if (namespaces != null)
    {
      Set keys = namespaces.keySet();
      Iterator keyIterator = keys.iterator();

      while (keyIterator.hasNext())
      {
        String prefix = (String)keyIterator.next();

        if (prefix == null)
        {
          prefix = "";
        }

        DOMUtils.printAttribute(Constants.ATTR_XMLNS +
                                (!prefix.equals("") ? ":" + prefix : ""),
                                (String)namespaces.get(prefix),
                                pw);
      }
    }
  }

  private static void printExtensibilityElements(Class parentType,
                                                 List extensibilityElements,
                                                 Definition def,
                                                 PrintWriter pw)
                                                   throws WSDLException
  {
    if (extensibilityElements != null)
    {
      Iterator extensibilityElementIterator = extensibilityElements.iterator();

      while (extensibilityElementIterator.hasNext())
      {
        ExtensibilityElement ext =
          (ExtensibilityElement)extensibilityElementIterator.next();
        QName elementType = ext.getElementType();
        ExtensionRegistry extReg = def.getExtensionRegistry();

        if (extReg == null)
        {
          throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                                  "No ExtensionRegistry set for this " +
                                  "Definition, so unable to serialize a '" +
                                  elementType +
                                  "' element in the context of a '" +
                                  parentType.getName() + "'.");
        }

        ExtensionSerializer extSer = extReg.querySerializer(parentType,
                                                            elementType);

        extSer.marshall(parentType, elementType, ext, pw, def, extReg);
      }
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
   * Return a document generated from the specified WSDL model.
   */
  public Document getDocument(Definition wsdlDef) throws WSDLException
  {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    writeWSDL(wsdlDef, pw);

    StringReader sr = new StringReader(sw.toString());
    InputSource is = new InputSource(sr);

    return getDocument(is, "- WSDL Document -");
  }

  /**
   * Write the specified WSDL definition to the specified Writer.
   *
   * @param wsdlDef the WSDL definition to be written.
   * @param sink the Writer to write the xml to.
   */
  public void writeWSDL(Definition wsdlDef, Writer sink)
    throws WSDLException
  {
    PrintWriter pw = new PrintWriter(sink);

    pw.println(Constants.XML_DECL);

    printDefinition(wsdlDef, pw);
  }

  /**
   * Write the specified WSDL definition to the specified OutputStream.
   *
   * @param wsdlDef the WSDL definition to be written.
   * @param sink the OutputStream to write the xml to.
   */
  public void writeWSDL(Definition wsdlDef, OutputStream sink)
    throws WSDLException
  {
    Writer writer = null;

    try
    {
      writer = new OutputStreamWriter(sink, "UTF8");
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();

      writer = new OutputStreamWriter(sink);
    }

    writeWSDL(wsdlDef, writer);
  }

  /**
   * A test driver.
   *<code>
   *<pre>Usage:</pre>
   *<p>
   *<pre>  java com.ibm.wsdl.xml.WSDLWriterImpl filename|URL</pre>
   *<p>
   *<pre>    This test driver simply reads a WSDL document into a model
   *    (using a WSDLReader), and then serializes it back to
   *    standard out. In effect, it performs a round-trip test on
   *    the specified WSDL document.</pre>
   */
  public static void main(String[] argv) throws WSDLException
  {
    if (argv.length == 1)
    {
      WSDLFactory wsdlFactory = WSDLFactory.newInstance();
      WSDLReader  wsdlReader  = wsdlFactory.newWSDLReader();
      WSDLWriter  wsdlWriter  = wsdlFactory.newWSDLWriter();

      wsdlWriter.writeWSDL(wsdlReader.readWSDL(null, argv[0]), System.out);
    }
    else
    {
      System.err.println("Usage:");
      System.err.println();
      System.err.println("  java " + WSDLWriterImpl.class.getName() +
                         " filename|URL");
      System.err.println();
      System.err.println("This test driver simply reads a WSDL document " +
                         "into a model (using a WSDLReader), and then " +
                         "serializes it back to standard out. In effect, " +
                         "it performs a round-trip test on the specified " +
                         "WSDL document.");
    }
  }
}