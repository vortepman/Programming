public class Boats extends Transport implements InterBoats {
    
/**
Class for implementing the functionality of ships
*/

    // fields
    protected boolean isMoor; // is the ship moored now
    private static int maxPassengersCount = 75;

    // Constructor
    public Boats(String name, boolean onTheRun, boolean isMoor) {
        this.setName(name);
        this.onTheRun = onTheRun;
        this.isMoor = isMoor;
    }

    // Meethod for moor of boat
    public void moor(Vulcan vulcan) { // method of mooring ships
        if(this.alive) {
            if(!vulcan.eruption && vulcan.getHasLand()) {
                if(!this.isMoor) {
                    this.isMoor = true;
                    System.out.println("> " + this.name + " пришвартовались к " + vulcan.getName() + "у");
                    this.stopToMove();
                }
                else {
                    System.out.println("> " + this.name + " не отшвартованы");
                }
            }
            else {
                System.out.println("> К " + vulcan.getName() + "у нельзя пришвартоваться");
            }
        }
        else {
            System.out.println("> " + this.name + " не в состоянии пришвартоваться");
        }
    }

    // Method for unmooring ships
    public void sailOff(Vulcan vulcan) { 
        if(this.alive) {
            if(this.isMoor) {
                this.isMoor = false;
                System.out.println("> " + this.name + " отшвартовались от " + vulcan.getName() + "а");
                this.startToMove();
            }
            else {
                System.out.println("> " + this.name + " не пришвартованы");
            }
        }
        else {
            System.out.println("> " + this.name + " не в состоянии отшвартоваться");
        }
    }

    @Override
    // Method for start the move
    public void startToMove() {
        if(this.alive) {
            if(!this.onTheRun){
                this.onTheRun = true;
                System.out.println("> " + this.getName() + " подняли якори и отправились в плавание");
            }
            else {
                System.out.println("> " + this.getName() + " уже в плавании");
            }
        }
        else {
            System.out.println("> " + this.getName() + " не могут начать плыть");
        }
    }

    @Override
    // Method for stop the move
    public void stopToMove() {
        if(this.onTheRun){
            this.onTheRun = false;
            System.out.println("> " + this.getName() + " остановились и опустили якори");
        }
        else {
            System.out.println("> " + this.getName() + " уже стоят");
        }
    }

    @Override
    // This method can crash boats
    public void crash() {
        if(this.alive) {
            this.alive = false;
            this.onTheRun = false;
            System.out.println("> " + this.getName() + " уничтожены");
        }
        else {
            System.out.println("> " + this.getName() + " уже уничтожены");
        }
    }

    // getters ans setters for field name
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    // Inner class for return value of field maxPassengeersCount
    public static class BoatsPlans {

        // To return value of field maxPassengeersCount
        public static int getMaxPassengersCount() {

            return maxPassengersCount;

        }
    }
}
