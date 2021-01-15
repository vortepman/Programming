public class Travel implements InterTravel {

    private Place currentPlace; // current position))

    public Place now() { // method to return the current position
        return this.currentPlace;
    }

    public void changePlace(Place place, Boats ships) { // method for changing position
        if (ships.alive) {
            if(ships.onTheRun) {
                this.currentPlace = place;
                System.out.println("> " + ships.getName() + " поплыли " + place.getPlace());
            }
            else {
                System.out.println("> " + ships.getName() + " пока не могут уплыть в другое место");
            }
        }
        else {
            System.out.println("> " + ships.getName() + " разрушены и не могут плыть");
        }
    }

    public void start() { // starting position method
        this.currentPlace = Place.WATER;
    }
}
