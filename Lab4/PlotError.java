public class PlotError extends Exception {

/**
An error that occurs when the ship is not in the water when it should be there.
*/
    
    public PlotError (String message) {
        super(message);
    }

}
