public class Seagulls extends Animal {
    
/**
Class for working with seagulls - characters of this program
*/
    // Constructor
    public Seagulls(String name, boolean inWater, boolean hungry) {
        this.name = name;
        this.inWater = inWater;
        this.hungry = hungry;
    }

    @Override
    // to scream
    public void voice() {
        System.out.println("> " + this.name + " кричат на языке чаек");
    }

}

