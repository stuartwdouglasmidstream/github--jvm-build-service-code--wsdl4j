package javax.wsdl.factory;

import java.io.*;
import java.util.*;
import javax.wsdl.*;

/**
 * This abstract class defines a factory API that enables applications
 * to obtain a DefinitionFactory capable of producing new Definition
 * instances. The WSDLReader and WSDLWriter interfaces should also
 * probably be abstracted in a similar manner.
 * 
 * Some ideas used here have been shamelessly copied from the
 * wonderful JAXP and Xerces work.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public abstract class DefinitionFactory
{
  private static final String PROPERTY_NAME =
    "javax.wsdl.factory.DefinitionFactory";
  private static final String PROPERTY_FILE_NAME =
    "wsdl.properties";
  private static final String DEFAULT_FACTORY_IMPL_NAME =
    "com.ibm.wsdl.factory.DefinitionFactoryImpl";

  private static String fullPropertyFileName = null;

  /**
   * Get a new instance of a DefinitionFactory. This method
   * follows (almost) the same basic sequence of steps that JAXP
   * follows to determine the fully-qualified class name of the
   * class which implements DefinitionFactory. The steps (in order)
   * are:
   *<pre>
   *  Check the javax.wsdl.factory.DefinitionProperty system property.
   *  Check the lib/wsdl.properties file in the JRE directory. The key
   * will have the same name as the above system property.
   *  Use the default value.
   *</pre>
   * Once an instance of a DefinitionFactory is obtained, invoke
   * newDefinition() on it to programmatically create a new instance
   * of a Defintion.
   */
  public static DefinitionFactory newInstance() throws WSDLException
  {
    String factoryImplName = findFactoryImplName();

    return newInstance(factoryImplName);
  }

  /**
   * Get a new instance of a DefinitionFactory. This method
   * returns an instance of the class factoryImplName.
   * Once an instance of a DefinitionFactory is obtained, invoke
   * newDefinition() on it to programmatically create a new instance
   * of a Defintion.
   *
   * @param factoryImplName the fully-qualified class name of the
   * class which provides a concrete implementation of the abstract
   * class DefinitionFactory.
   */
  public static DefinitionFactory newInstance(String factoryImplName)
    throws WSDLException
  {
    if (factoryImplName != null)
    {
      try
      {
        Class cl = Class.forName(factoryImplName);

        return (DefinitionFactory)cl.newInstance();
      }
      catch (Exception e)
      {
        /*
          Catches:
                   ClassNotFoundException
                   InstantiationException
                   IllegalAccessException
        */
        throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                                "Problem instantiating factory " +
                                "implementation.",
                                e);
      }
    }
    else
    {
      throw new WSDLException(WSDLException.CONFIGURATION_ERROR,
                              "Unable to find name of factory " +
                              "implementation.");
    }
  }

  /**
   * Create a new instance of a Definition.
   */
  public abstract Definition newDefinition();

  private static String findFactoryImplName()
  {
    String factoryImplName = null;

    // First, check the system property.
    try
    {
      factoryImplName = System.getProperty(PROPERTY_NAME);

      if (factoryImplName != null)
      {
        return factoryImplName;
      }
    }
    catch (SecurityException e)
    {
    }

    // Second, check the properties file.
    String propFileName = getFullPropertyFileName();

    if (propFileName != null)
    {
      try
      {
        Properties properties = new Properties();
        File propFile = new File(propFileName);
        FileInputStream fis = new FileInputStream(propFile);

        properties.load(fis);
        fis.close();

        factoryImplName = properties.getProperty(PROPERTY_NAME);

        if (factoryImplName != null)
        {
          return factoryImplName;
        }
      }
      catch (IOException e)
      {
      }
    }

    // Third, return the default.
    return DEFAULT_FACTORY_IMPL_NAME;
  }

  private static String getFullPropertyFileName()
  {
    if (fullPropertyFileName == null)
    {
      try
      {
        String javaHome = System.getProperty("java.home");

        fullPropertyFileName = javaHome + File.separator + "lib" +
                               File.separator + PROPERTY_FILE_NAME;
      }
      catch (SecurityException e)
      {
      }
    }

    return fullPropertyFileName;
  }
}