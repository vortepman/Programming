package com.company;

import static java.lang.Math.abs;

public class Main {

    public static void main(String[] args) {

        Vulcan erebus = new Vulcan("Эребус", 12700, true, true);
        Vulcan terror = new Vulcan("Террор", 10900, false, false);
        Vulcan fujiyama = new Vulcan("Фудзияма", 12390, true, true);
        Boats boats = new Boats("Корабли", false, false);
        Travel travel = new Travel();

        travel.start();

        if(travel.now() == Place.WATER) {
            boats.startToMove();
        }

        travel.changePlace(Place.TERROR, boats);
        System.out.println(terror.toString());

        if(travel.now() == Place.TERROR) {
            boats.moor(terror);
            terror.startOfEruption();
            if(terror.eruption && boats.isMoor) {
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

        if(erebus.equals(fujiyama)) {
            System.out.println("> " + boats.getName() + " неожиданно оказались около вулкана " + fujiyama.getName() + ",и всех интересует как?");
        }
        else {
            if(!erebus.equals(fujiyama) && (abs(erebus.getHeight() - fujiyama.getHeight()) < 1000)) {
                System.out.println("> " + erebus.getName() + " отдаленно напоминал гравюру священного вулкана " + fujiyama.getName());
            }
            else if(!erebus.equals(fujiyama) && (abs(erebus.getHeight() - fujiyama.getHeight()) > 1000)) {
                System.out.println("> " + erebus.getName() + " не похож на вулкан " + fujiyama.getName());
            }

            if(travel.now() == Place.EREBUS) {
                boats.moor(erebus);
                erebus.startOfEruption();
                if (erebus.eruption && boats.isMoor) {
                    boats.crash();
                    erebus.endOfEruption();
                }
                else {
                    boats.sailOff(erebus);
                    travel.changePlace(Place.WATER, boats);
                }
            }

        }

    }
}
