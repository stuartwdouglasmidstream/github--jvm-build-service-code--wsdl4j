package javax.wsdl;

import java.io.*;
import org.w3c.dom.Node;

/**
 * A <code>QName</code> represents a fully-qualified name.
 * The class-dependency on org.w3c.dom.Node should be
 * removed by moving the Node-dependent logic into an
 * external utility class.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 * @author Sanjiva Weerawarana (sanjiva@watson.ibm.com)
 */
public class QName implements Serializable, Cloneable
{
  private String namespaceURI = null;
  private String localPart = null;

  public QName()
  {
    // No-args constructor for use as bean.
    this(null, null);
  }

  public QName(Node node)
  {
    if (node != null)
    {
      setNamespaceURI(node.getNamespaceURI());
      setLocalPart(node.getLocalName());
    }
    else
    {
      setNamespaceURI(null);
      setLocalPart(null);
    }
  }

  public QName(String namespaceURI, String localPart)
  {
    setNamespaceURI(namespaceURI);
    setLocalPart(localPart);
  }

  public void setNamespaceURI(String namespaceURI)
  {
    this.namespaceURI = (namespaceURI == null ? "" : namespaceURI).intern();
  }

  public String getNamespaceURI()
  {
    return namespaceURI;
  }

  public void setLocalPart(String localPart)
  {
    this.localPart = (localPart == null ? "" : localPart).intern();
  }

  public String getLocalPart()
  {
    return localPart;
  }

  public int hashCode()
  {
    String hash1 = namespaceURI.hashCode() + "";
    String hash2 = localPart.hashCode() + "";
    String hash3 = hash1 + '_' + hash2;

    return hash3.hashCode();
  }

  public boolean equals(Object obj)
  {
    return (obj != null
            && namespaceURI == ((QName)obj).getNamespaceURI()
            && localPart == ((QName)obj).getLocalPart());
  }

  public boolean matches(Node node)
  {
    return (node != null && this.equals(new QName(node)));
  }

  private void readObject(java.io.ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();

    setNamespaceURI(namespaceURI);
    setLocalPart(localPart);
  }

  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  public String toString()
  {
    return (namespaceURI.length() > 0
            ? namespaceURI + ':'
            : "") + localPart;
  }
}
