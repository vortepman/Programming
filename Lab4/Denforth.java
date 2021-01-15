public class Denforth {

    private boolean inspiredBy = false;
    private final String name;

    public Denforth(String name) {
        this.name = name;
    }

    public void speak(Vulcan vulcan) {
        System.out.println("> " + this.name + " обратил внимание на стекающую по склону вулкана " + vulcan.getName() + " лаву");
        System.out.println("> Так же " + this.name + " прибавил, что именно " + vulcan.getName() + " послужил источником его былого вдохновения");
    }

    public void setInspiredBy(boolean inspiredBy) {
        this.inspiredBy = inspiredBy;
    }

    public void read(Vulcan vulcan) {
        this.setInspiredBy(true);
        if(this.inspiredBy) {
            System.out.println("> " + this.name + " вдохновлен и хочет прочитать стихи:");
            System.out.println("     Было сердце мое горячее," + "\n" +
                    "     Чем серы поток огневой," + "\n" +
                    "     Чем лавы поток огневой," + "\n" +
                    "     Бегущий с горы " + vulcan.getName() + "\n" +
                    "     Под ветра полярного вой," + "\n" +
                    "     Свергающийся с горы " + vulcan.getName() + "\n" +
                    "     Под бури арктической вой'");
        }
        else {
            System.out.println("> У " + this.name + "а сейчас нет вдохновения, чтобы читать стихотворения");
        }
    }
}

