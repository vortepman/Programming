public enum Place {
    
/**
Enum for list with aims of traveling
*/
    EREBUS("к Эребусу"),
    TERROR("к Террору"),
    WATER("в открытые воды");

    private final String position;

    // Constructor
    Place(String place) {
        this.position = place;
    }

    // Method for return current position
    public String getPlace() {
        return position;
    }
}

