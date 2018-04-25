package chris.infinifridge;

import android.util.Log;

import java.util.Date;

public class Entries {
    String name = "Custom entry";
    int imageId = 0;
    int amount = 1;
    int amountType = 0;
    int storage = 0;
    int[] expirationDate = new int[3];

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


    public boolean amIExpiringSoon(){
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime()-(60L*60L*100L));
        long dayT = (1000L * 60L * 60L * 24L);
        getExpirationDate(expirationDate[0],expirationDate[1],expirationDate[2]);

        if (this.expirationDate[0] != 0 && this.expirationDate[1] != 0 && this.expirationDate[2] != 0){
            Log.i("WHAT",getExpirationDate(this.expirationDate[0],this.expirationDate[1],this.expirationDate[2])+"");
            return getExpirationDate(this.expirationDate[0], this.expirationDate[1], this.expirationDate[2]) - 2 * dayT <= currentDate.getTime();
        }
        return false;
    }
    public long getExpirationDate(int dayN, int monthN, int yearN) {
        long dayT = (1000L * 60L * 60L * 24L);
        long thisYear = (48L * 365L * dayT) + (12L * dayT); //Jan 01 2018

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
        //currentDate.setTime(dateN); //dateN
       // currentDate.toString().substring(4).replace("00:00:00 GMT+00:00 ", "")
        return dateN;
    }
}
