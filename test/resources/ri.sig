1537 javax.wsdl.AttributeExtensible
{
  1025 void setExtensionAttribute (javax.xml.namespace.QName,javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getExtensionAttribute (javax.xml.namespace.QName)
  1025 java.util.Map getExtensionAttributes ()
  1025 java.util.List getNativeAttributeNames ()
}
1537 javax.wsdl.Binding implements java.io.Serializable
{
  1025 void setQName (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getQName ()
  1025 void setPortType (javax.wsdl.PortType)
  1025 javax.wsdl.PortType getPortType ()
  1025 void addBindingOperation (javax.wsdl.BindingOperation)
  1025 javax.wsdl.BindingOperation getBindingOperation (java.lang.String,java.lang.String,java.lang.String)
  1025 java.util.List getBindingOperations ()
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
  1025 void setUndefined (boolean)
  1025 boolean isUndefined ()
}
1537 javax.wsdl.BindingFault implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
}
1537 javax.wsdl.BindingInput implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
}
1537 javax.wsdl.BindingOperation implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
  1025 javax.wsdl.Operation getOperation ()
  1025 void setOperation (javax.wsdl.Operation)
  1025 void setBindingInput (javax.wsdl.BindingInput)
  1025 javax.wsdl.BindingInput getBindingInput ()
  1025 void setBindingOutput (javax.wsdl.BindingOutput)
  1025 javax.wsdl.BindingOutput getBindingOutput ()
  1025 void addBindingFault (javax.wsdl.BindingFault)
  1025 javax.wsdl.BindingFault getBindingFault (java.lang.String)
  1025 java.util.Map getBindingFaults ()
}
1537 javax.wsdl.BindingOutput implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
}
1537 javax.wsdl.Definition implements java.io.Serializable
{
  1025 javax.wsdl.Message getMessage (javax.xml.namespace.QName)
  1025 void setQName (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getQName ()
  1025 javax.wsdl.PortType getPortType (javax.xml.namespace.QName)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
  1025 java.lang.String getPrefix (java.lang.String)
  1025 void setDocumentBaseURI (java.lang.String)
  1025 java.lang.String getDocumentBaseURI ()
  1025 void setTargetNamespace (java.lang.String)
  1025 java.lang.String getTargetNamespace ()
  1025 void addNamespace (java.lang.String,java.lang.String)
  1025 java.lang.String getNamespace (java.lang.String)
  1025 java.util.Map getNamespaces ()
  1025 void setTypes (javax.wsdl.Types)
  1025 javax.wsdl.Types getTypes ()
  1025 void addImport (javax.wsdl.Import)
  1025 java.util.List getImports (java.lang.String)
  1025 java.util.Map getImports ()
  1025 void addMessage (javax.wsdl.Message)
  1025 javax.wsdl.Message removeMessage (javax.xml.namespace.QName)
  1025 java.util.Map getMessages ()
  1025 void addBinding (javax.wsdl.Binding)
  1025 javax.wsdl.Binding getBinding (javax.xml.namespace.QName)
  1025 javax.wsdl.Binding removeBinding (javax.xml.namespace.QName)
  1025 java.util.Map getBindings ()
  1025 void addPortType (javax.wsdl.PortType)
  1025 javax.wsdl.PortType removePortType (javax.xml.namespace.QName)
  1025 java.util.Map getPortTypes ()
  1025 void addService (javax.wsdl.Service)
  1025 javax.wsdl.Service getService (javax.xml.namespace.QName)
  1025 javax.wsdl.Service removeService (javax.xml.namespace.QName)
  1025 java.util.Map getServices ()
  1025 javax.wsdl.Binding createBinding ()
  1025 javax.wsdl.BindingFault createBindingFault ()
  1025 javax.wsdl.BindingInput createBindingInput ()
  1025 javax.wsdl.BindingOperation createBindingOperation ()
  1025 javax.wsdl.BindingOutput createBindingOutput ()
  1025 javax.wsdl.Fault createFault ()
  1025 javax.wsdl.Import createImport ()
  1025 javax.wsdl.Input createInput ()
  1025 javax.wsdl.Message createMessage ()
  1025 javax.wsdl.Operation createOperation ()
  1025 javax.wsdl.Output createOutput ()
  1025 javax.wsdl.Part createPart ()
  1025 javax.wsdl.Port createPort ()
  1025 javax.wsdl.PortType createPortType ()
  1025 javax.wsdl.Service createService ()
  1025 javax.wsdl.Types createTypes ()
  1025 javax.wsdl.extensions.ExtensionRegistry getExtensionRegistry ()
  1025 void setExtensionRegistry (javax.wsdl.extensions.ExtensionRegistry)
}
1537 javax.wsdl.Fault implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 javax.wsdl.Message getMessage ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setMessage (javax.wsdl.Message)
}
1537 javax.wsdl.Import implements java.io.Serializable
{
  1025 java.lang.String getNamespaceURI ()
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setNamespaceURI (java.lang.String)
  1025 void setLocationURI (java.lang.String)
  1025 java.lang.String getLocationURI ()
  1025 void setDefinition (javax.wsdl.Definition)
  1025 javax.wsdl.Definition getDefinition ()
}
1537 javax.wsdl.Input implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 javax.wsdl.Message getMessage ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setMessage (javax.wsdl.Message)
}
1537 javax.wsdl.Message implements java.io.Serializable
{
  1025 void setQName (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getQName ()
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setUndefined (boolean)
  1025 boolean isUndefined ()
  1025 void addPart (javax.wsdl.Part)
  1025 javax.wsdl.Part getPart (java.lang.String)
  1025 java.util.Map getParts ()
  1025 java.util.List getOrderedParts (java.util.List)
}
1537 javax.wsdl.Operation implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 void setInput (javax.wsdl.Input)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setUndefined (boolean)
  1025 boolean isUndefined ()
  1025 javax.wsdl.Input getInput ()
  1025 void setOutput (javax.wsdl.Output)
  1025 javax.wsdl.Output getOutput ()
  1025 void addFault (javax.wsdl.Fault)
  1025 javax.wsdl.Fault getFault (java.lang.String)
  1025 java.util.Map getFaults ()
  1025 void setStyle (javax.wsdl.OperationType)
  1025 javax.wsdl.OperationType getStyle ()
  1025 void setParameterOrdering (java.util.List)
  1025 java.util.List getParameterOrdering ()
}
1 javax.wsdl.OperationType extends java.lang.Object implements java.io.Serializable
{
  2 javax.wsdl.OperationType (java.lang.String)
  1 boolean equals (javax.wsdl.OperationType)
  1 java.lang.String toString ()
  2 java.lang.String getId ()
}
1537 javax.wsdl.Output implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 javax.wsdl.Message getMessage ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setMessage (javax.wsdl.Message)
}
1537 javax.wsdl.Part implements java.io.Serializable,javax.wsdl.AttributeExtensible
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 javax.xml.namespace.QName getTypeName ()
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setElementName (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getElementName ()
  1025 void setTypeName (javax.xml.namespace.QName)
}
1537 javax.wsdl.Port implements java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
  1025 javax.wsdl.Binding getBinding ()
  1025 void setBinding (javax.wsdl.Binding)
}
1537 javax.wsdl.PortType implements java.io.Serializable
{
  1025 void setQName (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getQName ()
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void setUndefined (boolean)
  1025 boolean isUndefined ()
  1025 void addOperation (javax.wsdl.Operation)
  1025 javax.wsdl.Operation getOperation (java.lang.String,java.lang.String,java.lang.String)
  1025 java.util.List getOperations ()
}
1537 javax.wsdl.Service implements java.io.Serializable
{
  1025 javax.wsdl.Port getPort (java.lang.String)
  1025 void setQName (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getQName ()
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
  1025 void addPort (javax.wsdl.Port)
  1025 java.util.Map getPorts ()
}
1537 javax.wsdl.Types implements java.io.Serializable
{
  1025 void setDocumentationElement (org.w3c.dom.Element)
  1025 org.w3c.dom.Element getDocumentationElement ()
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
}
1 javax.wsdl.WSDLException extends java.lang.Exception
{
  1 javax.wsdl.WSDLException (java.lang.String,java.lang.String)
  1 javax.wsdl.WSDLException (java.lang.String,java.lang.String,java.lang.Throwable)
  1 java.lang.String toString ()
  1 java.lang.String getMessage ()
  1 java.lang.Throwable getTargetException ()
  1 java.lang.String getLocation ()
  1 void setFaultCode (java.lang.String)
  1 java.lang.String getFaultCode ()
  1 void setTargetException (java.lang.Throwable)
  1 void setLocation (java.lang.String)
}
1537 javax.wsdl.extensions.ExtensibilityElement
{
  1025 void setElementType (javax.xml.namespace.QName)
  1025 javax.xml.namespace.QName getElementType ()
  1025 void setRequired (java.lang.Boolean)
  1025 java.lang.Boolean getRequired ()
}
1537 javax.wsdl.extensions.ExtensionDeserializer
{
  1025 javax.wsdl.extensions.ExtensibilityElement unmarshall (java.lang.Class,javax.xml.namespace.QName,org.w3c.dom.Element,javax.wsdl.Definition,javax.wsdl.extensions.ExtensionRegistry) throws javax.wsdl.WSDLException
}
1 javax.wsdl.extensions.ExtensionRegistry extends java.lang.Object implements java.io.Serializable
{
  1 javax.wsdl.extensions.ExtensionRegistry ()
  8 java.lang.Class class$ (java.lang.String)
  1 void setDefaultSerializer (javax.wsdl.extensions.ExtensionSerializer)
  1 javax.wsdl.extensions.ExtensionSerializer getDefaultSerializer ()
  1 void setDefaultDeserializer (javax.wsdl.extensions.ExtensionDeserializer)
  1 javax.wsdl.extensions.ExtensionDeserializer getDefaultDeserializer ()
  1 void registerSerializer (java.lang.Class,javax.xml.namespace.QName,javax.wsdl.extensions.ExtensionSerializer)
  1 void registerDeserializer (java.lang.Class,javax.xml.namespace.QName,javax.wsdl.extensions.ExtensionDeserializer)
  1 javax.wsdl.extensions.ExtensionSerializer querySerializer (java.lang.Class,javax.xml.namespace.QName) throws javax.wsdl.WSDLException
  1 javax.wsdl.extensions.ExtensionDeserializer queryDeserializer (java.lang.Class,javax.xml.namespace.QName) throws javax.wsdl.WSDLException
  1 java.util.Set getAllowableExtensions (java.lang.Class)
  1 void mapExtensionTypes (java.lang.Class,javax.xml.namespace.QName,java.lang.Class)
  1 javax.wsdl.extensions.ExtensibilityElement createExtension (java.lang.Class,javax.xml.namespace.QName) throws javax.wsdl.WSDLException
}
1537 javax.wsdl.extensions.ExtensionSerializer
{
  1025 void marshall (java.lang.Class,javax.xml.namespace.QName,javax.wsdl.extensions.ExtensibilityElement,java.io.PrintWriter,javax.wsdl.Definition,javax.wsdl.extensions.ExtensionRegistry) throws javax.wsdl.WSDLException
}
1 javax.wsdl.extensions.UnknownExtensibilityElement extends java.lang.Object implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1 javax.wsdl.extensions.UnknownExtensibilityElement ()
  1 java.lang.String toString ()
  1 void setElementType (javax.xml.namespace.QName)
  1 javax.xml.namespace.QName getElementType ()
  1 void setRequired (java.lang.Boolean)
  1 java.lang.Boolean getRequired ()
  1 void setElement (org.w3c.dom.Element)
  1 org.w3c.dom.Element getElement ()
}
1 javax.wsdl.extensions.UnknownExtensionDeserializer extends java.lang.Object implements javax.wsdl.extensions.ExtensionDeserializer,java.io.Serializable
{
  1 javax.wsdl.extensions.UnknownExtensionDeserializer ()
  1 javax.wsdl.extensions.ExtensibilityElement unmarshall (java.lang.Class,javax.xml.namespace.QName,org.w3c.dom.Element,javax.wsdl.Definition,javax.wsdl.extensions.ExtensionRegistry) throws javax.wsdl.WSDLException
}
1 javax.wsdl.extensions.UnknownExtensionSerializer extends java.lang.Object implements javax.wsdl.extensions.ExtensionSerializer,java.io.Serializable
{
  1 javax.wsdl.extensions.UnknownExtensionSerializer ()
  1 void marshall (java.lang.Class,javax.xml.namespace.QName,javax.wsdl.extensions.ExtensibilityElement,java.io.PrintWriter,javax.wsdl.Definition,javax.wsdl.extensions.ExtensionRegistry) throws javax.wsdl.WSDLException
}
1537 javax.wsdl.extensions.http.HTTPAddress implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void setLocationURI (java.lang.String)
  1025 java.lang.String getLocationURI ()
}
1537 javax.wsdl.extensions.http.HTTPBinding implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void setVerb (java.lang.String)
  1025 java.lang.String getVerb ()
}
1537 javax.wsdl.extensions.http.HTTPOperation implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void setLocationURI (java.lang.String)
  1025 java.lang.String getLocationURI ()
}
1537 javax.wsdl.extensions.http.HTTPUrlEncoded implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
}
1537 javax.wsdl.extensions.http.HTTPUrlReplacement implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
}
1537 javax.wsdl.extensions.mime.MIMEContent implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 java.lang.String getType ()
  1025 java.lang.String getPart ()
  1025 void setPart (java.lang.String)
  1025 void setType (java.lang.String)
}
1537 javax.wsdl.extensions.mime.MIMEMimeXml implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 java.lang.String getPart ()
  1025 void setPart (java.lang.String)
}
1537 javax.wsdl.extensions.mime.MIMEMultipartRelated implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void addMIMEPart (javax.wsdl.extensions.mime.MIMEPart)
  1025 java.util.List getMIMEParts ()
}
1537 javax.wsdl.extensions.mime.MIMEPart implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void addExtensibilityElement (javax.wsdl.extensions.ExtensibilityElement)
  1025 java.util.List getExtensibilityElements ()
}
1537 javax.wsdl.extensions.soap.SOAPAddress implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void setLocationURI (java.lang.String)
  1025 java.lang.String getLocationURI ()
}
1537 javax.wsdl.extensions.soap.SOAPBinding implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void setStyle (java.lang.String)
  1025 java.lang.String getStyle ()
  1025 void setTransportURI (java.lang.String)
  1025 java.lang.String getTransportURI ()
}
1537 javax.wsdl.extensions.soap.SOAPBody implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 java.lang.String getNamespaceURI ()
  1025 java.util.List getParts ()
  1025 void setNamespaceURI (java.lang.String)
  1025 void setParts (java.util.List)
  1025 void setUse (java.lang.String)
  1025 java.lang.String getUse ()
  1025 void setEncodingStyles (java.util.List)
  1025 java.util.List getEncodingStyles ()
}
1537 javax.wsdl.extensions.soap.SOAPFault implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 java.lang.String getName ()
  1025 void setName (java.lang.String)
  1025 java.lang.String getNamespaceURI ()
  1025 void setNamespaceURI (java.lang.String)
  1025 void setUse (java.lang.String)
  1025 java.lang.String getUse ()
  1025 void setEncodingStyles (java.util.List)
  1025 java.util.List getEncodingStyles ()
}
1537 javax.wsdl.extensions.soap.SOAPHeader implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 javax.xml.namespace.QName getMessage ()
  1025 java.lang.String getNamespaceURI ()
  1025 java.lang.String getPart ()
  1025 void setNamespaceURI (java.lang.String)
  1025 void setMessage (javax.xml.namespace.QName)
  1025 void setPart (java.lang.String)
  1025 void setUse (java.lang.String)
  1025 java.lang.String getUse ()
  1025 void setEncodingStyles (java.util.List)
  1025 java.util.List getEncodingStyles ()
  1025 void addSOAPHeaderFault (javax.wsdl.extensions.soap.SOAPHeaderFault)
  1025 java.util.List getSOAPHeaderFaults ()
}
1537 javax.wsdl.extensions.soap.SOAPHeaderFault implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 javax.xml.namespace.QName getMessage ()
  1025 java.lang.String getNamespaceURI ()
  1025 java.lang.String getPart ()
  1025 void setNamespaceURI (java.lang.String)
  1025 void setMessage (javax.xml.namespace.QName)
  1025 void setPart (java.lang.String)
  1025 void setUse (java.lang.String)
  1025 java.lang.String getUse ()
  1025 void setEncodingStyles (java.util.List)
  1025 java.util.List getEncodingStyles ()
}
1537 javax.wsdl.extensions.soap.SOAPOperation implements javax.wsdl.extensions.ExtensibilityElement,java.io.Serializable
{
  1025 void setStyle (java.lang.String)
  1025 java.lang.String getStyle ()
  1025 void setSoapActionURI (java.lang.String)
  1025 java.lang.String getSoapActionURI ()
}
1025 javax.wsdl.factory.WSDLFactory extends java.lang.Object
{
  1 javax.wsdl.factory.WSDLFactory ()
  9 javax.wsdl.factory.WSDLFactory newInstance (java.lang.String) throws javax.wsdl.WSDLException
  9 javax.wsdl.factory.WSDLFactory newInstance () throws javax.wsdl.WSDLException
  1025 javax.wsdl.Definition newDefinition ()
  1025 javax.wsdl.xml.WSDLReader newWSDLReader ()
  1025 javax.wsdl.xml.WSDLWriter newWSDLWriter ()
  1025 javax.wsdl.extensions.ExtensionRegistry newPopulatedExtensionRegistry ()
  10 java.lang.String findFactoryImplName ()
  10 java.lang.String getFullPropertyFileName ()
}
1537 javax.wsdl.xml.WSDLLocator
{
  1025 org.xml.sax.InputSource getBaseInputSource ()
  1025 org.xml.sax.InputSource getImportInputSource (java.lang.String,java.lang.String)
  1025 java.lang.String getBaseURI ()
  1025 java.lang.String getLatestImportURI ()
}
1537 javax.wsdl.xml.WSDLReader
{
  1025 javax.wsdl.extensions.ExtensionRegistry getExtensionRegistry ()
  1025 void setExtensionRegistry (javax.wsdl.extensions.ExtensionRegistry)
  1025 void setFeature (java.lang.String,boolean) throws java.lang.IllegalArgumentException
  1025 boolean getFeature (java.lang.String) throws java.lang.IllegalArgumentException
  1025 void setFactoryImplName (java.lang.String) throws java.lang.UnsupportedOperationException
  1025 java.lang.String getFactoryImplName ()
  1025 javax.wsdl.Definition readWSDL (java.lang.String) throws javax.wsdl.WSDLException
  1025 javax.wsdl.Definition readWSDL (java.lang.String,java.lang.String) throws javax.wsdl.WSDLException
  1025 javax.wsdl.Definition readWSDL (java.lang.String,org.w3c.dom.Element) throws javax.wsdl.WSDLException
  1025 javax.wsdl.Definition readWSDL (java.lang.String,org.w3c.dom.Document) throws javax.wsdl.WSDLException
  1025 javax.wsdl.Definition readWSDL (java.lang.String,org.xml.sax.InputSource) throws javax.wsdl.WSDLException
  1025 javax.wsdl.Definition readWSDL (javax.wsdl.xml.WSDLLocator) throws javax.wsdl.WSDLException
}
1537 javax.wsdl.xml.WSDLWriter
{
  1025 void setFeature (java.lang.String,boolean) throws java.lang.IllegalArgumentException
  1025 boolean getFeature (java.lang.String) throws java.lang.IllegalArgumentException
  1025 org.w3c.dom.Document getDocument (javax.wsdl.Definition) throws javax.wsdl.WSDLException
  1025 void writeWSDL (javax.wsdl.Definition,java.io.OutputStream) throws javax.wsdl.WSDLException
  1025 void writeWSDL (javax.wsdl.Definition,java.io.Writer) throws javax.wsdl.WSDLException
}
1 javax.xml.namespace.QName extends java.lang.Object implements java.io.Serializable
{
  1 javax.xml.namespace.QName (java.lang.String,java.lang.String)
  1 javax.xml.namespace.QName (java.lang.String)
  17 int hashCode ()
  17 boolean equals (java.lang.Object)
  1 java.lang.String toString ()
  9 javax.xml.namespace.QName valueOf (java.lang.String)
  2 void readObject (java.io.ObjectInputStream) throws java.io.IOException,java.lang.ClassNotFoundException
  1 java.lang.String getNamespaceURI ()
  1 java.lang.String getLocalPart ()
}
