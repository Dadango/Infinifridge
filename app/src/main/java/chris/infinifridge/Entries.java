package chris.infinifridge;

import java.util.Date;

public class Entries {
    int[] border_c = new int[3];
    String name = "Custom entry";
    String location;
    Date currentDate = new Date();

    public void Entries(String name){
        this.name = name;
        }

    public String setExpirationDate(int dayN, int monthN, int yearN) {
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
    }
}
