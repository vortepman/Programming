public interface InterVulcan {
    
/**
Interface for work with Vulcan class
*/

    void setName(String name);
    void endOfEruption();
    String getName();
    void setHasLand(boolean hasLand);
    boolean getHasLand();
    void setSmoker(boolean smoker);
    boolean getSmoker();
    void setHeight(int height);
    int getHeight();
    void startOfEruption();
    void smokeBlowing();

}
