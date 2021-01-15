public class Penguins extends Animal {
    
    // Constructor
    public Penguins(String name, boolean inWater, boolean hungry) {
        this.name = name;
        this.inWater = inWater;
        this.hungry = hungry;
    }

    @Override
    // to scream
    public void voice() {
        System.out.println("> " + this.name + " издают гогочущие звуки");
    }

    // to walk
    public void walk(Vulcan vulcan) {
        // to flee
        if(!this.inWater && vulcan.eruption) {
            System.out.println("> " + this.name + " легли на пузо и уехали к воде");
            this.swim();
        }
        else if(!this.inWater) {
            System.out.println("> " + this.name + " бродят по берегу вулкана " + vulcan.getName());
        }
        else {
            System.out.println("> " + this.name + " пока находятся в воде");
        }
    }

}
