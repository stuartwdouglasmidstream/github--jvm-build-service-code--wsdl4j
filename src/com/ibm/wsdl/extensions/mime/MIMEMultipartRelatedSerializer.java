package com.ibm.wsdl.extensions.mime;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.util.xml.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class MIMEMultipartRelatedSerializer implements ExtensionSerializer,
                                                       ExtensionDeserializer,
                                                       Serializable
{
  public void marshall(Class parentType,
                       Class extensionType,
                       ExtensibilityElement extension,
                       PrintWriter pw,
                       Definition def,
                       ExtensionRegistry extReg)
                         throws WSDLException
  {
    MIMEMultipartRelated mimeMultipartRelated =
      (MIMEMultipartRelated)extension;

    if (mimeMultipartRelated != null)
    {
      if (parentType != null
          && MIMEPart.class.isAssignableFrom(parentType))
      {
        pw.print("    ");
      }

      pw.print("        <mime:multipartRelated");

      Boolean required = mimeMultipartRelated.getRequired();

      if (required != null)
      {
        DOMUtils.printQualifiedAttribute(Constants.Q_ATTR_REQUIRED,
                                         required.toString(),
                                         def,
                                         pw);
      }

      pw.println('>');

      printMIMEParts(mimeMultipartRelated.getMIMEParts(), pw, def, extReg);

      if (parentType != null
          && MIMEPart.class.isAssignableFrom(parentType))
      {
        pw.print("    ");
      }

      pw.println("        </mime:multipartRelated>");
    }
  }

  private void printMIMEParts(List mimeParts,
                              PrintWriter pw,
                              Definition def,
                              ExtensionRegistry extReg)
                                throws WSDLException
  {
    if (mimeParts != null)
    {
      Iterator mimePartIterator = mimeParts.iterator();

      while (mimePartIterator.hasNext())
      {
        MIMEPart mimePart = (MIMEPart)mimePartIterator.next();

        if (mimePart != null)
        {
          pw.print("          <mime:part");

          Boolean required = mimePart.getRequired();

          if (required != null)
          {
            DOMUtils.printQualifiedAttribute(Constants.Q_ATTR_REQUIRED,
                                             required.toString(),
                                             def,
                                             pw);
          }

          pw.println('>');

          List extensibilityElements = mimePart.getExtensibilityElements();

          if (extensibilityElements != null)
          {
            Iterator extensibilityElementIterator =
              extensibilityElements.iterator();

            while (extensibilityElementIterator.hasNext())
            {
              ExtensibilityElement ext =
                (ExtensibilityElement)extensibilityElementIterator.next();
              Class extensionType = ext.getClass();
              ExtensionSerializer extSer =
                extReg.querySerializer(MIMEPart.class, extensionType);

              extSer.marshall(MIMEPart.class,
                              extensionType,
                              ext,
                              pw,
                              def,
                              extReg);
            }
          }

          pw.println("          </mime:part>");
        }
      }
    }
  }

  public ExtensibilityElement unmarshall(Class parentType,
                                         QName elementType,
                                         Element el,
                                         Definition def,
                                         ExtensionRegistry extReg)
                                           throws WSDLException
	{
    MIMEMultipartRelated mimeMultipartRelated = new MIMEMultipartRelated();
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);
    Element tempEl = DOMUtils.getFirstChildElement(el);

    while (tempEl != null)
    {
      if (MIMEConstants.Q_ELEM_MIME_PART.matches(tempEl))
      {
        mimeMultipartRelated.addMIMEPart(parseMIMEPart(tempEl, def, extReg));
      }
      else
      {
        DOMUtils.throwWSDLException(tempEl);
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    if (requiredStr != null)
    {
      mimeMultipartRelated.setRequired(new Boolean(requiredStr));
    }

    return mimeMultipartRelated;
	}

  private MIMEPart parseMIMEPart(Element el,
                                 Definition def,
                                 ExtensionRegistry extReg)
                                   throws WSDLException
  {
    MIMEPart mimePart = new MIMEPart();
    String requiredStr = DOMUtils.getAttributeNS(el,
                                                 Constants.NS_URI_WSDL,
                                                 Constants.ATTR_REQUIRED);

    if (requiredStr != null)
    {
      mimePart.setRequired(new Boolean(requiredStr));
    }

    Element tempEl = DOMUtils.getFirstChildElement(el);

    while (tempEl != null)
    {
      try
      {
        QName elementType = new QName(tempEl);
        ExtensionDeserializer extDS = extReg.queryDeserializer(MIMEPart.class,
                                                               elementType);
        ExtensibilityElement ext =
          extDS.unmarshall(MIMEPart.class, elementType, tempEl, def, extReg);

        mimePart.addExtensibilityElement(ext);
      }
      catch (WSDLException e)
      {
        if (e.getLocation() == null)
        {
          e.setLocation(XPathUtils.getXPathExprFromNode(tempEl));
        }

        throw e;
      }

      tempEl = DOMUtils.getNextSiblingElement(tempEl);
    }

    return mimePart;
  }
}