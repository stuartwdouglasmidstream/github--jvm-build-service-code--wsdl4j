package javax.wsdl.extensions.http;

import javax.wsdl.*;
import javax.wsdl.extensions.*;

/**
 * @author Matthew J. Duftler (duftler@us.ibm.com)
 */
public interface HTTPBinding extends ExtensibilityElement, java.io.Serializable
{
  /**
   * Set the verb for this HTTP binding.
   *
   * @param verb the desired verb
   */
  public void setVerb(String verb);

  /**
   * Get the verb for this HTTP binding.
   */
  public String getVerb();
}