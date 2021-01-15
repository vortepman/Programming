public abstract class Animal {

/**
Abstract ancestor class for creating acting animal characters
*/
    
    protected String name;
    protected boolean inWater;
    protected boolean hungry;

    // to scream something
    public abstract void voice();

    // to check the ability to swim
    public void swim() {
        if(!this.inWater) {
            System.out.println("> " + this.name + " ныряют в воду");
            this.inWater = true;
        }
        else {
            System.out.println("> " + this.name + " плавают в воде");
        }
    }

    // to check a wish to eat and eat fish
    public void eat() {
        if(!this.hungry) {
            System.out.println("> " + this.name + " едят рыбу");
            this.hungry = true;
        }
        else {
            System.out.println("> " + this.name + " сыты");
        }
    }
}
