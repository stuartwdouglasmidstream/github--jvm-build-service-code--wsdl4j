package javax.wsdl.xml;

import java.net.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.wsdl.*;

/**
 * This interface describes a collection of methods
 * that enable conversion of a WSDL document (in XML,
 * following the WSDL schema described in the WSDL
 * specification) into a WSDL model.
 *
 * @author Matthew J. Duftler
 */
public interface WSDLReader
{
  public void setVerbose(boolean verbose);

  public boolean getVerbose();

  public void setImportDocuments(boolean importDocuments);

  public boolean getImportDocuments();

  /**
   * Read the WSDL document accessible via the specified
   * URI into a WSDL definition.
   *
   * @param contextURL the context in which to resolve the
   * wsdlURI, if the wsdlURI is relative. Can be null, in which
   * case it will be ignored.
   * @param wsdlURI a URI (can be a filename or URL) pointing to a
   * WSDL XML definition.
   * @return the definition.
   */
  public Definition readWSDL(URL contextURL, String wsdlURI)
    throws WSDLException;

  /**
   * Read the specified WSDL document into a WSDL definition.
   *
   * @param documentBase the document base of the WSDL definition
   * described by the document. Will be set as the documentBase
   * of the returned Definition. Can be null, in which case it
   * will be ignored.
   * @param wsdlDocument the WSDL document, an XML 
   * document obeying the WSDL schema.
   * @return the definition described in the document.
   */
  public Definition readWSDL(URL documentBase, Document wsdlDocument)
    throws WSDLException;

  /**
   * Read a WSDL document into a WSDL definition.
   *
   * @param documentBase the document base of the WSDL definition
   * described by the document. Will be set as the documentBase
   * of the returned Definition. Can be null, in which case it
   * will be ignored.
   * @param inputSource an InputSource pointing to the
   * WSDL document, an XML document obeying the WSDL schema.
   * @return the definition described in the document pointed to
   * by the InputSource.
   */
  public Definition readWSDL(URL documentBase, InputSource inputSource)
    throws WSDLException;
}