package javax.wsdl.xml;

import java.io.*;

public interface WSDLLocator
{
  /**
   * Returns a reader "pointed at" the base document.
   */
	public Reader getBaseReader();

  /**
   * Returns a reader pointed at an imported wsdl document whose
   * parent document was located at parentLocation and whose
   * relative location to the parent document is specified by
   * relativeLocation.
   *
   * @param parentLocation a URI specifying the location of the
   * document doing the importing.
   * @param relativeLocation a URI specifying the location of the
   * document to import, relative to the parent document's location.
   */
	public Reader getImportReader(String parentLocation, String relativeLocation);

  /**
   * Returns a URI representing the location of the base document.
   */
	public String getBaseURI();

  /**
   * Returns a URI representing the location of the last import document
   * to be resolved. This is useful when resolving nested imports.
   */
	public String getLatestImportURI();
}

