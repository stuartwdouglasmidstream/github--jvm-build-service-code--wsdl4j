package javax.wsdl.extensions;

import java.util.*;
import javax.wsdl.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class ExtensionRegistry implements java.io.Serializable
{
  /*
    This is a Map of Maps. The top-level Map is keyed by (Class)parentType,
    and the inner Maps are keyed by (Class)extensionType.
  */
  protected Map serializerReg = new Hashtable();
  /*
    This is a Map of Maps. The top-level Map is keyed by (Class)parentType,
    and the inner Maps are keyed by (QName)elementType.
  */
  protected Map deserializerReg = new Hashtable();
  protected ExtensionSerializer defaultSer = null;
  protected ExtensionDeserializer defaultDeser = null;

  /**
   * Set the serializer to be used when none is found for extensibility
   * elements. Set this to null to have an exception thrown when
   * unexpected extensibility elements are encountered.
   */
  public void setDefaultSerializer(ExtensionSerializer defaultSer)
  {
    this.defaultSer = defaultSer;
  }

  /**
   * Get the serializer to be used when none is found for extensibility
   * elements.
   */
  public ExtensionSerializer getDefaultSerializer()
  {
    return defaultSer;
  }

  /**
   * Set the deserializer to be used when none is found for encountered
   * elements. Set this to null to have an exception thrown when
   * unexpected extensibility elements are encountered.
   */
  public void setDefaultDeserializer(ExtensionDeserializer defaultDeser)
  {
    this.defaultDeser = defaultDeser;
  }

  /**
   * Get the deserializer to be used when none is found for encountered
   * elements.
   */
  public ExtensionDeserializer getDefaultDeserializer()
  {
    return defaultDeser;
  }

  public void registerSerializer(Class parentType,
                                 Class extensionType,
                                 ExtensionSerializer es)
  {
    Map innerSerializerReg = (Map)serializerReg.get(parentType);

    if (innerSerializerReg == null)
    {
      innerSerializerReg = new Hashtable();

      serializerReg.put(parentType, innerSerializerReg);
    }

    innerSerializerReg.put(extensionType, es);
  }

  public void registerDeserializer(Class parentType,
                                   QName elementType,
                                   ExtensionDeserializer ed)
  {
    Map innerDeserializerReg = (Map)deserializerReg.get(parentType);

    if (innerDeserializerReg == null)
    {
      innerDeserializerReg = new Hashtable();

      deserializerReg.put(parentType, innerDeserializerReg);
    }

    innerDeserializerReg.put(elementType, ed);
  }

  public ExtensionSerializer querySerializer(Class parentType,
                                             Class extensionType)
                                               throws WSDLException
  {
    Map innerSerializerReg = (Map)serializerReg.get(parentType);
    ExtensionSerializer es = null;

    if (innerSerializerReg != null)
    {
      es = (ExtensionSerializer)innerSerializerReg.get(extensionType);
    }

    if (es == null)
    {
      es = defaultSer;
    }

    if (es == null)
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "No ExtensionSerializer found " +
                              "to serialize a '" + extensionType.getName() +
                              "' in the context of a '" +
                              parentType.getName() + "'.");
    }

    return es;
  }

  public ExtensionDeserializer queryDeserializer(Class parentType,
                                                 QName elementType)
                                                   throws WSDLException
  {
    Map innerDeserializerReg = (Map)deserializerReg.get(parentType);
    ExtensionDeserializer ed = null;

    if (innerDeserializerReg != null)
    {
      ed = (ExtensionDeserializer)innerDeserializerReg.get(elementType);
    }

    if (ed == null)
    {
      ed = defaultDeser;
    }

    if (ed == null)
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "No ExtensionDeserializer found " +
                              "to deserialize a '" + elementType +
                              "' element in the context of a '" +
                              parentType.getName() + "'.");
    }

    return ed;
  }

  /**
   * Returns a set of QNames representing the extensibility elements
   * that are allowed as children of the specified parent type.
   * Basically, this method returns the keys associated with the set
   * of extension deserializers registered for this parent type.
   * Returns null if no extension deserializers are registered for
   * this parent type.
   */
  public Set getAllowableExtensions(Class parentType)
  {
    Map innerDeserializerReg = (Map)deserializerReg.get(parentType);

    return (innerDeserializerReg != null)
           ? innerDeserializerReg.keySet()
           : null;
  }
}