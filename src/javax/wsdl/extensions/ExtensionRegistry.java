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
    and the inner Maps are keyed by (QName)elementType.
  */
  protected Map serializerReg = new Hashtable();
  /*
    This is a Map of Maps. The top-level Map is keyed by (Class)parentType,
    and the inner Maps are keyed by (QName)elementType.
  */
  protected Map deserializerReg = new Hashtable();
  /*
    This is a Map of Maps. The top-level Map is keyed by (Class)parentType,
    and the inner Maps are keyed by (QName)elementType.
  */
  protected Map extensionTypeReg = new Hashtable();
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
                                 QName elementType,
                                 ExtensionSerializer es)
  {
    Map innerSerializerReg = (Map)serializerReg.get(parentType);

    if (innerSerializerReg == null)
    {
      innerSerializerReg = new Hashtable();

      serializerReg.put(parentType, innerSerializerReg);
    }

    innerSerializerReg.put(elementType, es);
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
                                             QName elementType)
                                               throws WSDLException
  {
    Map innerSerializerReg = (Map)serializerReg.get(parentType);
    ExtensionSerializer es = null;

    if (innerSerializerReg != null)
    {
      es = (ExtensionSerializer)innerSerializerReg.get(elementType);
    }

    if (es == null)
    {
      es = defaultSer;
    }

    if (es == null)
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "No ExtensionSerializer found " +
                              "to serialize a '" + elementType +
                              "' element in the context of a '" +
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

  public void mapExtensionTypes(Class parentType,
                                QName elementType,
                                Class extensionType)
  {
    Map innerExtensionTypeReg = (Map)extensionTypeReg.get(parentType);

    if (innerExtensionTypeReg == null)
    {
      innerExtensionTypeReg = new Hashtable();

      extensionTypeReg.put(parentType, innerExtensionTypeReg);
    }

    innerExtensionTypeReg.put(elementType, extensionType);
  }

  public ExtensibilityElement createExtension(Class parentType,
                                              QName elementType)
                                                throws WSDLException
  {
    Map innerExtensionTypeReg = (Map)extensionTypeReg.get(parentType);
    Class extensionType = null;

    if (innerExtensionTypeReg != null)
    {
      extensionType = (Class)innerExtensionTypeReg.get(elementType);
    }

    if (extensionType == null)
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "No Java extensionType found " +
                              "to represent a '" + elementType +
                              "' element in the context of a '" +
                              parentType.getName() + "'.");
    }
    else if (!(ExtensibilityElement.class.isAssignableFrom(extensionType)))
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "The Java extensionType '" +
                              extensionType.getName() + "' does " +
                              "not implement the ExtensibilityElement " +
                              "interface.");
    }

    try
    {
      return (ExtensibilityElement)extensionType.newInstance();
    }
    catch (Exception e)
    {
      /*
        Catches:
                 InstantiationException
                 IllegalAccessException
      */
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "Problem instantiating Java " +
                              "extensionType '" + extensionType.getName() +
                              "'.",
                              e);
    }
  }
}