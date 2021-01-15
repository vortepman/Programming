public class IsSmokerError extends RuntimeException{
   
/**
An error generated when an erupting volcano is assigned the wrong field
*/
    public IsSmokerError() {}
    public IsSmokerError(String message){
        super(message);
    }
}

