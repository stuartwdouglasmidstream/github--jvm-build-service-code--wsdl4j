package com.ibm.wsdl.factory;

import javax.wsdl.*;
import javax.wsdl.extensions.*;
import javax.wsdl.factory.*;
import com.ibm.wsdl.*;
import com.ibm.wsdl.extensions.*;

/**
 * This class is a concrete implementation of the abstract class
 * DefinitionFactory. Some ideas used here have been shamelessly
 * copied from the wonderful JAXP and Xerces work.
 *
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public class DefinitionFactoryImpl extends DefinitionFactory
{
  /**
   * Create a new instance of a Definition, with an instance
   * of a PopulatedExtensionRegistry as its ExtensionRegistry.
   *
   * @see com.ibm.wsdl.extensions.PopulatedExtensionRegistry
   */
  public Definition newDefinition()
  {
    Definition def = new DefinitionImpl();
    ExtensionRegistry extReg = new PopulatedExtensionRegistry();

    def.setExtensionRegistry(extReg);

    return def;
  }
}