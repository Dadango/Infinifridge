package chris.infinifridge;

import java.util.Date;

public class Entries {
    String name = "Custom entry";
    int imageId = 0;
    int amount = 1;
    int amountType = 0;
    int storage = 0;
    int[] expirationDate = new int[3];
    Date currentDate = new Date();

    public Entries(){
        this.name = name;
    }
    public Entries(String name, int imageId, int amount, int amountType, int storage){
        this.name = name;
        this.imageId = imageId;
        this.amount = amount;
        this.amountType = amountType;
        this.storage = storage;
        }

    public Entries(String name, int imageId, int amount, int amountType, int storage, int[] expirationDate){
        this.name = name;
        this.imageId = imageId;
        this.amount = amount;
        this.amountType = amountType;
        this.storage = storage;
        this.expirationDate = expirationDate;
    }

/*
    public String setExpirationDate(int dayN, int monthN, int yearN) { // we didn't need this anyway.
        long dayT = (1000l * 60l * 60l * 24l);
        long thisYear = (48l * 365l * dayT) + (12l * dayT); //Jan 01 2018
        currentDate.setTime(thisYear);

        int mFixN = 31;
        int mFix2 = 0;

        if (monthN < 5 && monthN > 2) {
            mFix2 = 3;
        } else if (monthN >= 5 && monthN < 7) {
            mFix2 = 4;
        } else if (monthN >= 7 && monthN < 10) {
            mFix2 = 5;
        } else if (monthN >= 10 && monthN < 12) {
            mFix2 = 6;
        } else if (monthN == 12) {
            mFix2 = 7;
        }
        long dateN = ((dayN - 1) * dayT) + ((monthN - 1) * dayT * mFixN - (mFix2 * dayT)) + ((yearN - 1970) * dayT * 365l) + (12l * dayT);
        currentDate.setTime(dateN); //dateN

        return currentDate.toString().substring(4).replace("00:00:00 GMT+00:00 ", "");
    }*/
}
