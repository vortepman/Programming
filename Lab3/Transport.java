public abstract class Transport {

    protected boolean onTheRun; // is transport moving now
    protected boolean alive = true; // whole/on the go transport
    protected String name;

    public abstract void startToMove(); // method for starting traffic
    public abstract void stopToMove(); // method for stopping transport
    public abstract void crash(); // transport destruction method
    public abstract void setName(String name);
    public abstract String getName();

}
