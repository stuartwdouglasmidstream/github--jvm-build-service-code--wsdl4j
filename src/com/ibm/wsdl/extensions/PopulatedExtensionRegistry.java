package com.ibm.wsdl.extensions;

import javax.wsdl.*;
import javax.wsdl.extensions.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.extensions.soap.*;
import com.ibm.wsdl.extensions.http.*;
import com.ibm.wsdl.extensions.mime.*;

/**
 * This class extends ExtensionRegistry and pre-registers
 * serializers/deserializers for the SOAP, HTTP and MIME
 * extensions. The defaultSerializer property is set to a
 * new instance of UnknownExtensionSerializer. The
 * defaultDeserializer property is set to a new instance of
 * UnknownExtensionDeserializer.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class PopulatedExtensionRegistry extends ExtensionRegistry
{
  public PopulatedExtensionRegistry()
  {
    setDefaultSerializer(new UnknownExtensionSerializer());
    setDefaultDeserializer(new UnknownExtensionDeserializer());

    SOAPAddressSerializer soapAddressSer = new SOAPAddressSerializer();

    registerSerializer(Port.class,
                       SOAPAddress.class,
                       soapAddressSer);
    registerDeserializer(Port.class,
                         SOAPConstants.Q_ELEM_SOAP_ADDRESS,
                         soapAddressSer);

    SOAPBindingSerializer soapBindingSer = new SOAPBindingSerializer();

    registerSerializer(Binding.class,
                       SOAPBinding.class,
                       soapBindingSer);
    registerDeserializer(Binding.class,
                         SOAPConstants.Q_ELEM_SOAP_BINDING,
                         soapBindingSer);

    SOAPHeaderSerializer soapHeaderSer = new SOAPHeaderSerializer();

    registerSerializer(BindingInput.class,
                       SOAPHeader.class,
                       soapHeaderSer);
    registerDeserializer(BindingInput.class,
                         SOAPConstants.Q_ELEM_SOAP_HEADER,
                         soapHeaderSer);
    registerSerializer(BindingOutput.class,
                       SOAPHeader.class,
                       soapHeaderSer);
    registerDeserializer(BindingOutput.class,
                         SOAPConstants.Q_ELEM_SOAP_HEADER,
                         soapHeaderSer);

    SOAPBodySerializer soapBodySer = new SOAPBodySerializer();

    registerSerializer(BindingInput.class,
                       SOAPBody.class,
                       soapBodySer);
    registerDeserializer(BindingInput.class,
                         SOAPConstants.Q_ELEM_SOAP_BODY,
                         soapBodySer);
    registerSerializer(BindingOutput.class,
                       SOAPBody.class,
                       soapBodySer);
    registerDeserializer(BindingOutput.class,
                         SOAPConstants.Q_ELEM_SOAP_BODY,
                         soapBodySer);
    registerSerializer(MIMEPart.class,
                       SOAPBody.class,
                       soapBodySer);
    registerDeserializer(MIMEPart.class,
                         SOAPConstants.Q_ELEM_SOAP_BODY,
                         soapBodySer);

    SOAPFaultSerializer soapFaultSer = new SOAPFaultSerializer();

    registerSerializer(BindingFault.class,
                       SOAPFault.class,
                       soapFaultSer);
    registerDeserializer(BindingFault.class,
                         SOAPConstants.Q_ELEM_SOAP_FAULT,
                         soapFaultSer);

    SOAPOperationSerializer soapOperationSer = new SOAPOperationSerializer();

    registerSerializer(BindingOperation.class,
                       SOAPOperation.class,
                       soapOperationSer);
    registerDeserializer(BindingOperation.class,
                         SOAPConstants.Q_ELEM_SOAP_OPERATION,
                         soapOperationSer);

    HTTPAddressSerializer httpAddressSer = new HTTPAddressSerializer();

    registerSerializer(Port.class,
                       HTTPAddress.class,
                       httpAddressSer);
    registerDeserializer(Port.class,
                         HTTPConstants.Q_ELEM_HTTP_ADDRESS,
                         httpAddressSer);

    HTTPOperationSerializer httpOperationSer = new HTTPOperationSerializer();

    registerSerializer(BindingOperation.class,
                       HTTPOperation.class,
                       httpOperationSer);
    registerDeserializer(BindingOperation.class,
                         HTTPConstants.Q_ELEM_HTTP_OPERATION,
                         httpOperationSer);

    HTTPBindingSerializer httpBindingSer = new HTTPBindingSerializer();

    registerSerializer(Binding.class,
                       HTTPBinding.class,
                       httpBindingSer);
    registerDeserializer(Binding.class,
                         HTTPConstants.Q_ELEM_HTTP_BINDING,
                         httpBindingSer);

    HTTPUrlEncodedSerializer httpUrlEncodedSer =
      new HTTPUrlEncodedSerializer();

    registerSerializer(BindingInput.class,
                       HTTPUrlEncoded.class,
                       httpUrlEncodedSer);
    registerDeserializer(BindingInput.class,
                         HTTPConstants.Q_ELEM_HTTP_URL_ENCODED,
                         httpUrlEncodedSer);

    HTTPUrlReplacementSerializer httpUrlReplacementSer =
      new HTTPUrlReplacementSerializer();

    registerSerializer(BindingInput.class,
                       HTTPUrlReplacement.class,
                       httpUrlReplacementSer);
    registerDeserializer(BindingInput.class,
                         HTTPConstants.Q_ELEM_HTTP_URL_REPLACEMENT,
                         httpUrlReplacementSer);

    MIMEContentSerializer mimeContentSer = new MIMEContentSerializer();

    registerSerializer(BindingInput.class,
                       MIMEContent.class,
                       mimeContentSer);
    registerDeserializer(BindingInput.class,
                         MIMEConstants.Q_ELEM_MIME_CONTENT,
                         mimeContentSer);
    registerSerializer(BindingOutput.class,
                       MIMEContent.class,
                       mimeContentSer);
    registerDeserializer(BindingOutput.class,
                         MIMEConstants.Q_ELEM_MIME_CONTENT,
                         mimeContentSer);
    registerSerializer(MIMEPart.class,
                       MIMEContent.class,
                       mimeContentSer);
    registerDeserializer(MIMEPart.class,
                         MIMEConstants.Q_ELEM_MIME_CONTENT,
                         mimeContentSer);

    MIMEMultipartRelatedSerializer mimeMultipartRelatedSer =
      new MIMEMultipartRelatedSerializer();

    registerSerializer(BindingInput.class,
                       MIMEMultipartRelated.class,
                       mimeMultipartRelatedSer);
    registerDeserializer(BindingInput.class,
                         MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                         mimeMultipartRelatedSer);
    registerSerializer(BindingOutput.class,
                       MIMEMultipartRelated.class,
                       mimeMultipartRelatedSer);
    registerDeserializer(BindingOutput.class,
                         MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                         mimeMultipartRelatedSer);
    registerSerializer(MIMEPart.class,
                       MIMEMultipartRelated.class,
                       mimeMultipartRelatedSer);
    registerDeserializer(MIMEPart.class,
                         MIMEConstants.Q_ELEM_MIME_MULTIPART_RELATED,
                         mimeMultipartRelatedSer);

    MIMEMimeXmlSerializer mimeMimeXmlSer = new MIMEMimeXmlSerializer();

    registerSerializer(BindingInput.class,
                       MIMEMimeXml.class,
                       mimeMimeXmlSer);
    registerDeserializer(BindingInput.class,
                         MIMEConstants.Q_ELEM_MIME_MIME_XML,
                         mimeMimeXmlSer);
    registerSerializer(BindingOutput.class,
                       MIMEMimeXml.class,
                       mimeMimeXmlSer);
    registerDeserializer(BindingOutput.class,
                         MIMEConstants.Q_ELEM_MIME_MIME_XML,
                         mimeMimeXmlSer);
    registerSerializer(MIMEPart.class,
                       MIMEMimeXml.class,
                       mimeMimeXmlSer);
    registerDeserializer(MIMEPart.class,
                         MIMEConstants.Q_ELEM_MIME_MIME_XML,
                         mimeMimeXmlSer);
  }
}