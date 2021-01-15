public class Vulcan implements InterVulcan {

    private String name;
    private int height;
    private boolean smoker; // is it a smoking vulcan
    private boolean hasLand; // does the vulcan have a splashdown
    protected boolean eruption = false; // is the vulcan erupting right now

    public Vulcan(String name, int height, boolean smoker, boolean hasLand) {
        this.setName(name);
        this.setHeight(height);
        this.setHasLand(hasLand);
        this.setSmoker(smoker);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHasLand(boolean hasLand) {
        this.hasLand = hasLand;
    }

    public boolean getHasLand() {
        return hasLand;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public boolean getSmoker() {
        return smoker;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void startOfEruption() { // method for starting a volcanic eruption
        if(this.getSmoker()) {
            if(!this.eruption) {
                this.eruption = true;
                System.out.println("> " + this.getName() + " начал извергаться!");
            }
            else {
                System.out.println("> " + this.getName() + " уже извергается!");
            }
        }
        else {
            System.out.println("> " + this.getName() + " может начать извергаться!");
            System.out.println("> " + this.getName() + " не может извергаться(");
        }
    }

    public void endOfEruption() { // method for ending a volcanic eruption
        if(this.eruption) {
            this.eruption = false;
            System.out.println("> " + this.getName() + " закончил извергаться");
        }
        else {
            System.out.println("> " + this.getName() + " не извергался");
        }
    }

    @Override
    public String toString() {
        return ">> Вулкан{"
                + "Название: '" + this.getName() + '\''
                + ", Высота: " + this.getHeight() + " футов"
                + '}';
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + 29 * this.height;
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) {
            return true;
        }
        if(otherObject == null) {
            return false;
        }
        if(getClass() != otherObject.getClass()) {
            return false;
        }
        Vulcan other = (Vulcan) otherObject;
        return this.hashCode() == otherObject.hashCode();
    }
}
