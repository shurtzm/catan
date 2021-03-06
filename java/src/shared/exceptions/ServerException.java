package shared.exceptions;

/**
 * This exception is thrown whenever a request from the Server Proxy to the server fails in an unexpected way (connection issues, timeout, etc.)
 * @author Alex
 *
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {
    
    public ServerException() {
        super();
    }
    
    public ServerException(String message) {
        super( message );
    }
    
    public ServerException( Exception cause ){
        super( cause );
    }
    
}
