package com.ibm.wsdl.util.xml;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;

public class DOMUtils {
  /**
   * The namespaceURI represented by the prefix <code>xmlns</code>.
   */
  private static String NS_URI_XMLNS = "http://www.w3.org/2000/xmlns/";

  /**
   * Returns the value of an attribute of an element. Returns null
   * if the attribute is not found (whereas Element.getAttribute
   * returns "" if an attrib is not found).
   *
   * @param el       Element whose attrib is looked for
   * @param attrName name of attribute to look for 
   * @return the attribute value
   */
  static public String getAttribute (Element el, String attrName) {
    String sRet = null;
    Attr   attr = el.getAttributeNode(attrName);

    if (attr != null) {
      sRet = attr.getValue();
    }
    return sRet;
  }

  /**
   * Returns the value of an attribute of an element. Returns null
   * if the attribute is not found (whereas Element.getAttributeNS
   * returns "" if an attrib is not found).
   *
   * @param el       Element whose attrib is looked for
   * @param namespaceURI namespace URI of attribute to look for 
   * @param localPart local part of attribute to look for 
   * @return the attribute value
   */
  static public String getAttributeNS (Element el,
                                       String namespaceURI,
                                       String localPart) {
    String sRet = null;
    Attr   attr = el.getAttributeNodeNS (namespaceURI, localPart);

    if (attr != null) {
      sRet = attr.getValue ();
    }

    return sRet;
  }

  /**
   * Concat all the text and cdata node children of this elem and return
   * the resulting text.
   *
   * @param parentEl the element whose cdata/text node values are to
   *                 be combined.
   * @return the concatanated string.
   */
  static public String getChildCharacterData (Element parentEl) {
    if (parentEl == null) {
      return null;
    } 
    Node          tempNode = parentEl.getFirstChild();
    StringBuffer  strBuf   = new StringBuffer();
    CharacterData charData;

    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.TEXT_NODE :
        case Node.CDATA_SECTION_NODE : charData = (CharacterData)tempNode;
                                       strBuf.append(charData.getData());
                                       break;
      }
      tempNode = tempNode.getNextSibling();
    }
    return strBuf.toString();
  }

  /**
   * Return the first child element of the given element. Null if no
   * children are found.
   *
   * @param elem Element whose child is to be returned
   * @return the first child element.
   */
  public static Element getFirstChildElement (Element elem) {
    for (Node n = elem.getFirstChild (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == Node.ELEMENT_NODE) {
        return (Element) n;
      }
    }
    return null;
  }

  /**
   * Return the next sibling element of the given element. Null if no
   * more sibling elements are found.
   *
   * @param elem Element whose sibling element is to be returned
   * @return the next sibling element.
   */
  public static Element getNextSiblingElement (Element elem) {
    for (Node n = elem.getNextSibling (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == Node.ELEMENT_NODE) {
        return (Element) n;
      }
    }
    return null;
  }

  /**
   * Return the first child element of the given element which has the
   * given attribute with the given value.
   *
   * @param elem      the element whose children are to be searched
   * @param attrName  the attrib that must be present
   * @param attrValue the desired value of the attribute
   *
   * @return the first matching child element.
   */
  public static Element findChildElementWithAttribute (Element elem, 
                   String attrName,
                   String attrValue) {
    for (Node n = elem.getFirstChild (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == Node.ELEMENT_NODE) {
        if (attrValue.equals (DOMUtils.getAttribute ((Element) n, attrName))) {
          return (Element) n;
        }
      }
    }
    return  null;
  }

  /** 
   * Count number of children of a certain type of the given element.
   *
   * @param elem the element whose kids are to be counted
   *
   * @return the number of matching kids.
   */
  public static int countKids (Element elem, short nodeType) {
    int nkids = 0;
    for (Node n = elem.getFirstChild (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == nodeType) {
        nkids++;
      }
    }
    return nkids;
  }

  /**
   * Given a prefix and a node, return the namespace URI that the prefix
   * has been associated with. This method is useful in resolving the
   * namespace URI of attribute values which are being interpreted as
   * QNames. If prefix is null, this method will return the default
   * namespace.
   *
   * @param context the starting node (looks up recursively from here)
   * @param prefix the prefix to find an xmlns:prefix=uri for
   *
   * @return the namespace URI or null if not found
   */
  public static String getNamespaceURIFromPrefix (Node context,
                                                  String prefix) {
    short nodeType = context.getNodeType ();
    Node tempNode = null;

    switch (nodeType)
    {
      case Node.ATTRIBUTE_NODE :
      {
        tempNode = ((Attr) context).getOwnerElement ();
        break;
      }
      case Node.ELEMENT_NODE :
      {
        tempNode = context;
        break;
      }
      default :
      {
        tempNode = context.getParentNode ();
        break;
      }
    }

    while (tempNode != null && tempNode.getNodeType () == Node.ELEMENT_NODE)
    {
      Element tempEl = (Element) tempNode;
      String namespaceURI = (prefix == null)
                            ? getAttribute (tempEl, "xmlns")
                            : getAttributeNS (tempEl, NS_URI_XMLNS, prefix);

      if (namespaceURI != null)
      {
        return namespaceURI;
      }
      else
      {
        tempNode = tempEl.getParentNode ();
      }
    }

    return null;
  }

  public static QName getQualifiedAttributeValue(Element el,
                                                 String attrName,
                                                 String elDesc,
                                                 boolean isRequired)
                                                   throws WSDLException
  {
    String attrValue = DOMUtils.getAttribute(el, attrName);

    if (attrValue != null)
    {
      int    index                 = attrValue.indexOf(':');
      String attrValuePrefix       = (index != -1)
                                     ? attrValue.substring(0, index)
                                     : null;
      String attrValueLocalPart    = attrValue.substring(index + 1);
      String attrValueNamespaceURI =
        DOMUtils.getNamespaceURIFromPrefix(el, attrValuePrefix);

      if (attrValueNamespaceURI != null)
      {
        return new QName(attrValueNamespaceURI, attrValueLocalPart);
      }
      else
      {
        WSDLException wsdlExc = new WSDLException(WSDLException.INVALID_WSDL,
                                                  "Unable to determine " +
                                                  "namespace of '" +
                                                  (attrValuePrefix != null
                                                   ? attrValuePrefix + ":"
                                                   : "") + attrValueLocalPart +
                                                  "'.");

        wsdlExc.setLocation(XPathUtils.getXPathExprFromNode(el));

        throw wsdlExc;
      }
    }
    else if (isRequired)
    {
      WSDLException wsdlExc = new WSDLException(WSDLException.INVALID_WSDL,
                                                "The '" + attrName +
                                                "' attribute must be " +
                                                "specified for every " +
                                                elDesc + " element.");

      wsdlExc.setLocation(XPathUtils.getXPathExprFromNode(el));

      throw wsdlExc;
    }
    else
    {
      return null;
    }
  }

  public static void throwWSDLException(Element location) throws WSDLException
  {
    String elName = new QName(location).toString();

    WSDLException wsdlExc = new WSDLException(WSDLException.INVALID_WSDL,
                                              "Encountered unexpected '" +
                                              elName + "'.");

    wsdlExc.setLocation(XPathUtils.getXPathExprFromNode(location));

    throw wsdlExc;
  }

  public static void printAttribute(String name,
                                    String value,
                                    PrintWriter pw)
  {
    if (value != null)
    {
      pw.print(' ' + name + "=\"" + cleanString(value) + '\"');
    }
  }

  /**
   * Prints attributes with qualified names.
   */
  public static void printQualifiedAttribute(QName name,
                                             String value,
                                             Definition def,
                                             PrintWriter pw)
                                               throws WSDLException
  {
    if (name != null)
    {
      printAttribute(getQualifiedValue(name, def), value, pw);
    }
  }

  /**
   * Prints attributes with qualified values.
   */
  public static void printQualifiedAttribute(String name,
                                             QName value,
                                             Definition def,
                                             PrintWriter pw)
                                               throws WSDLException
  {
    if (value != null)
    {
      printAttribute(name, getQualifiedValue(value, def), pw);
    }
  }

  private static String getQualifiedValue(QName value,
                                          Definition def)
                                            throws WSDLException
  {
    String namespaceURI = value.getNamespaceURI();
    String localPart = value.getLocalPart();
    String prefix = null;

    if (namespaceURI != null && !namespaceURI.equals(""))
    {
      prefix = def.getPrefix(namespaceURI);

      if (prefix == null)
      {
        throw new WSDLException(WSDLException.OTHER_ERROR,
                                "Can't find prefix for '" + namespaceURI +
                                "'. Namespace prefixes must be set on the" +
                                " Definition object using the " +
                                "addNamespace(...) method.");
      }
    }

    return ((prefix != null && !prefix.equals(""))
            ? prefix + ":"
            : "") + localPart;
  }

  public static String cleanString(String orig)
  {
    if (orig == null)
    {
      return "";
    }

    StringBuffer strBuf = new StringBuffer();
    char[] chars = orig.toCharArray();
    boolean inCDATA = false;

    for (int i = 0; i < chars.length; i++)
    {
      if (!inCDATA)
      {
        switch (chars[i])
        {
          case '&'  : strBuf.append("&amp;");
                      break;
          case '\"' : strBuf.append("&quot;");
                      break;
          case '\'' : strBuf.append("&apos;");
                      break;
          case '<'  :
                      {
                        if (chars.length >= i + 9)
                        {
                          String tempStr = new String(chars, i, 9);

                          if (tempStr.equals("<![CDATA["))
                          {
                            strBuf.append(tempStr);
                            i += 8;
                            inCDATA = true;
                          }
                          else
                          {
                            strBuf.append("&lt;");
                          }
                        }
                        else
                        {
                          strBuf.append("&lt;");
                        }
                      }
                      break;
          case '>'  : strBuf.append("&gt;");
                      break;
          default   : strBuf.append(chars[i]);
                      break;
        }
      }
      else
      {
        strBuf.append(chars[i]);

        if (chars[i] == '>'
            && chars[i - 1] == ']'
            && chars[i - 2] == ']')
        {
          inCDATA = false;
        }
      }
    }

    return strBuf.toString();
  }
}
