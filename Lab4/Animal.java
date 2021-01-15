public abstract class Animal {

    protected String name;
    protected boolean inWater;
    protected boolean hungry;

    public abstract void voice();

    public void swim() {
        if(!this.inWater) {
            System.out.println("> " + this.name + " ныряют в воду");
            this.inWater = true;
        }
        else {
            System.out.println("> " + this.name + " плавают в воде");
        }
    }

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
