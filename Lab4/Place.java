public enum Place {
    EREBUS("к Эребусу"),
    TERROR("к Террору"),
    WATER("в открытые воды");

    private final String position;

    Place(String place) {
        this.position = place;
    }

    public String getPlace() {
        return position;
    }
}

