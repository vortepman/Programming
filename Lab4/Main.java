import static java.lang.Math.abs;

public class Main {
    
//Main class of this laboratory work. To start a program.

    public static void main(String[] args) throws PlotError {

        try {
            Vulcan erebus = new Vulcan("Эребус", 12700, true, true);
            Vulcan terror = new Vulcan("Террор", 10900, false, false);
            Vulcan fujiyama = new Vulcan("Фудзияма", 12390, true, true);
            Boats boats = new Boats("Корабли", false, false);
            Denforth denforth = new Denforth("Денфорт");
            Penguins penguins = new Penguins("Пингвины", false, false);
            Seagulls seagulls = new Seagulls("Чайки", true, true);
            Travel travel = new Travel();

            try{
                erebus.isSmokerCorrect();
            }
            catch (IsSmokerError e){
                System.out.println(e.getMessage());
                erebus.replaceIsSmoker();
            }

            travel.start();

            LoadProvisions loadProvisions = new LoadProvisions() {
                @Override
                public void loadProvisions() {
                    System.out.println("> Корабли загрузили провизию");
                }
            };

            loadProvisions.loadProvisions();

            Boats.BoatsPlans boatsPlans = new Boats.BoatsPlans();
            System.out.println("> На " + boats.getName() + " зашло по " + boatsPlans.getMaxPassengersCount() + " человек");

            if (travel.now() == Place.WATER) {
                boats.startToMove();
            }

            if (travel.now() != Place.WATER) {
                throw new PlotError("> Ошибка, " + boats.getName() + " не находятся в открытых водах и плыть не могут");
            }

            travel.changePlace(Place.TERROR, boats);
            System.out.println(terror.toString());

            if (travel.now() == Place.TERROR) {
                boats.moor(terror);
                terror.startOfEruption();
                if (terror.eruption && boats.isMoor) {
                    boats.crash();
                    terror.endOfEruption();
                }
                else {
                    boats.sailOff(terror);
                    travel.changePlace(Place.WATER, boats);
                }
            }

            travel.changePlace(Place.EREBUS, boats);
            System.out.println(erebus.toString());

            if (erebus.equals(fujiyama)) {
                System.out.println("> " + boats.getName() + " неожиданно оказались около вулкана " + fujiyama.getName() + ",и всех интересует как?");
            }
            else {
                if (!erebus.equals(fujiyama) && (abs(erebus.getHeight() - fujiyama.getHeight()) < 1000)) {
                    System.out.println("> " + erebus.getName() + " отдаленно напоминал гравюру священного вулкана " + fujiyama.getName());
                }
                else if (!erebus.equals(fujiyama) && (abs(erebus.getHeight() - fujiyama.getHeight()) > 1000)) {
                    System.out.println("> " + erebus.getName() + " не похож на вулкан " + fujiyama.getName());
                }

                if (travel.now() == Place.EREBUS) {
                    boats.moor(erebus);
                    denforth.speak(erebus);
                    denforth.read(erebus);
                    System.out.println("> Рядом с вулканом веселятся " + penguins.name + " и " + seagulls.name);
                    penguins.voice();
                    seagulls.voice();
                    seagulls.swim();
                    penguins.eat();
                    seagulls.eat();
                    erebus.smokeBlowing();
                    erebus.startOfEruption();
                    if (erebus.eruption && boats.isMoor) {
                        boats.crash();
                        penguins.walk(erebus);
                        erebus.endOfEruption();
                    }
                    else {
                        boats.sailOff(erebus);
                        penguins.walk(erebus);
                        travel.changePlace(Place.WATER, boats);
                    }
                }
            }
        }
        catch (PlotError plotError) {
            System.out.println("> Коробли не поплыли.");
            System.out.println(plotError.getMessage());
        }
    }
}
