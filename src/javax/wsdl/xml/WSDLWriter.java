package javax.wsdl.xml;

import java.io.*;
import org.w3c.dom.*;
import javax.wsdl.*;

/**
 * This interface describes a collection of methods
 * that allow a WSDL model to be written to a writer
 * in an XML format that follows the WSDL schema.
 *
 * @author Matthew J. Duftler
 */
public interface WSDLWriter
{
  /**
   * Return a document generated from the specified WSDL model.
   */
  public Document getDocument(Definition wsdlDef) throws WSDLException;

  /**
   * Write the specified WSDL definition to the specified Writer.
   *
   * @param wsdlDef the WSDL definition to be written.
   * @param sink the Writer to write the xml to.
   */
  public void writeWSDL(Definition wsdlDef, Writer sink)
    throws WSDLException;

  /**
   * Write the specified WSDL definition to the specified OutputStream.
   *
   * @param wsdlDef the WSDL definition to be written.
   * @param sink the OutputStream to write the xml to.
   */
  public void writeWSDL(Definition wsdlDef, OutputStream sink)
    throws WSDLException;
}