package javax.wsdl.xml;

import java.net.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.wsdl.*;
import javax.wsdl.extensions.*;

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
   * Set the extension registry to be used when reading
   * WSDL documents into a WSDL definition. If an
   * extension registry is set, that is the extension
   * registry that will be set as the extensionRegistry
   * property of the definitions resulting from invoking
   * readWSDL(...). Default is null.
   *
   * @param extReg the extension registry to use for new
   * definitions
   */
  public void setExtensionRegistry(ExtensionRegistry extReg);

  /**
   * Get the extension registry, if one was set. Default is
   * null.
   */
  public ExtensionRegistry getExtensionRegistry();

  /**
   * Set a different factory implementation to use for
   * creating definitions when reading WSDL documents.
   * As some WSDLReader implementations may only be
   * capable of creating definitions using the same
   * factory implementation from which the reader was
   * obtained, this method is optional. Default is null.
   *
   * @param factoryImplName the fully-qualified class name of the
   * class which provides a concrete implementation of the abstract
   * class WSDLFactory.
   * @throws UnsupportedOperationException if this method
   * is invoked on an implementation which does not
   * support it.
   */
  public void setFactoryImplName(String factoryImplName)
    throws UnsupportedOperationException;

  /**
   * Get the factoryImplName, if one was set. Default is null.
   */
  public String getFactoryImplName();

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
   * Read the specified &lt;wsdl:definitions&gt; element into a WSDL
   * definition.
   *
   * @param documentBase the document base of the WSDL definition
   * described by the element. Will be set as the documentBase
   * of the returned Definition. Can be null, in which case it
   * will be ignored.
   * @param definitionsElement the &lt;wsdl:definitions&gt; element
   * @return the definition described by the element.
   */
  public Definition readWSDL(URL documentBase, Element definitionsElement)
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