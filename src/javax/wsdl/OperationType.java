package javax.wsdl;

/**
 * This class represents an operation type which can
 * be one of request-response, solicit response, one way or
 * notification. This represents a safe way to prevent usage
 * of invalid values since the only objects of this class available
 * are the public static instances declared within the class.
 * Need to figure out if this should be made into an interface.
 */
public class OperationType implements java.io.Serializable
{
  private String id;

  public static OperationType ONE_WAY =
    new OperationType("ONE_WAY");
  public static OperationType REQUEST_RESPONSE =
    new OperationType("REQUEST_RESPONSE");
  public static OperationType SOLICIT_RESPONSE =
    new OperationType("SOLICIT_RESPONSE");
  public static OperationType NOTIFICATION =
    new OperationType("NOTIFICATION");

  private OperationType(String id)
  {
	  this.id = id;
  }  

  private String getId()
  {
	  return id;
  }  

  public boolean equals(OperationType operationType)
  {
	  return id.equals(operationType.getId());
  }

  public String toString()
  {
    return id;
  }
}