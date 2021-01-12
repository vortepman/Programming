package com.company;

public class Boats extends Transport implements InterBoats {

    protected boolean isMoor; // is the ship moored now

    public Boats(String name, boolean onTheRun, boolean isMoor) {
        this.setName(name);
        this.onTheRun = onTheRun;
        this.isMoor = isMoor;
    }

    public void moor(Vulcan vulcan) { // method of mooring ships
        if(this.alive) {
            if(!vulcan.eruption && vulcan.getHasLand()) {
                if(!this.isMoor) {
                    this.isMoor = true;
                    System.out.println("> " + this.name + " пришвартовался(-ись) к " + vulcan.getName() + "у");
                    this.stopToMove();
                }
                else {
                    System.out.println("> " + this.name + " не отшвартован(ы)");
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

    public void sailOff(Vulcan vulcan) { // method for unmooring ships
        if(this.alive) {
            if(this.isMoor) {
                this.isMoor = false;
                System.out.println("> " + this.name + " отшвартовался(-ись) от " + vulcan.getName() + "а");
                this.startToMove();
            }
            else {
                System.out.println("> " + this.name + " не пришвартован(ы)");
            }
        }
        else {
            System.out.println("> " + this.name + " не в состоянии отшвартоваться");
        }
    }

    @Override
    public void startToMove() {
        if(this.alive) {
            if(!this.onTheRun){
                this.onTheRun = true;
                System.out.println("> " + this.getName() + " поднял(и) якорь(-и) и отправились(-ся) в плавание");
            }
            else {
                System.out.println("> " + this.getName() + " уже в плавании");
            }
        }
        else {
            System.out.println("> " + this.getName() + " не может(-гут) начать плыть");
        }
    }

    @Override
    public void stopToMove() {
        if(this.onTheRun){
            this.onTheRun = false;
            System.out.println("> " + this.getName() + " остановился(-ись) и опустил(и) якорь(-и)");
        }
        else {
            System.out.println("> " + this.getName() + " уже стоит(-ят)");
        }
    }

    @Override
    public void crash() {
        if(this.alive) {
            this.alive = false;
            this.onTheRun = false;
            System.out.println("> " + this.getName() + " уничтожен(ы)");
        }
        else {
            System.out.println("> " + this.getName() + " уже уничтожен(ы)");
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
