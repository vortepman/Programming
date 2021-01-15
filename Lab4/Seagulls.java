public class Seagulls extends Animal {

    public Seagulls(String name, boolean inWater, boolean hungry) {
        this.name = name;
        this.inWater = inWater;
        this.hungry = hungry;
    }

    @Override
    public void voice() {
        System.out.println("> " + this.name + " кричат на языке чаек");
    }

}

