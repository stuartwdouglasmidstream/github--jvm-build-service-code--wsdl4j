package javax.wsdl.xml;

import java.io.Reader;

public interface WSDLLocator {
	public Reader getBaseReader();

	public Reader getImportReader(String parentLocation, String relativeLocation);
	
	public String getBaseURI();
	
	public String getLatestImportURI();
}

