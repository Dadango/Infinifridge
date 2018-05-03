package chris.infinifridge;

import java.util.Date;

public class Entries {                                                                              //The entries class, with it's variables and the default values of the variables are declared and initialized
    String name = "Custom entry";
    int imageId = 0;
    int amount = 1;
    int amountType = 0;
    int storage = 0;
    int[] expirationDate = new int[3];

    public Entries() {                                                                              //Why do we have a default constructor? because fuck you that's why
    }

    public Entries(String name, int imageId, int amount, int amountType, int storage) {             //A non-default constructor, with every variable, except the expiration date, assigned to the arguments
        this.name = name;
        this.imageId = imageId;
        this.amount = amount;
        this.amountType = amountType;
        this.storage = storage;
    }

    public Entries(String name, int imageId, int amount, int amountType, int storage, int[] expirationDate) {   //A non-default constructor, with every variable assigned to the argument values
        this.name = name;
        this.imageId = imageId;
        this.amount = amount;
        this.amountType = amountType;
        this.storage = storage;
        this.expirationDate = expirationDate;
    }


    public boolean amIExpiringSoon() {                                                              //Method to check if the entry's expiration date is close to the current date
        Date currentDate = new Date();                                                              //Declare and initialize a Date variable named "currentDate" with the current date (default constructor "Date" create a Date variable of the Date when it was called)
        currentDate.setTime(currentDate.getTime() - (60L * 60L * 100L));                            //Sets the "currentDate" to 1 hour earlier (don't remember why we did this, but it's probably really important so just schys)
        long dayT = (1000L * 60L * 60L * 24L);                                                      //Declare and initialize a variable named "dayT" of type long, to hold 1 day in milliseconds (for easier calculations later)
        if (this.expirationDate[0] != 0 && this.expirationDate[1] != 0 && this.expirationDate[2] != 0) {    //if the expirationDate array does not consist of purely zeroes (the default if none were input by the user), run the block
            return getExpirationDate(this.expirationDate[0], this.expirationDate[1], this.expirationDate[2]) - 2 * dayT <= currentDate.getTime();   //translate the expirationDate array into a long and compare it to the currentDate (if the expiration date is within 2 days of the current date, return true
        }
        return false;                                                                               //if not then false
    }

    public long getExpirationDate(int dayN, int monthN, int yearN) {                                //The method that returns a long for a date when given the day,month and year
        long dayT = (1000L * 60L * 60L * 24L);                                                      //Declare and initialize a variable named "dayT" of type long, to hold 1 day in milliseconds (for easier calculations later)
        long thisYear = (48L * 365L * dayT) + (12L * dayT); //Jan 01 2018                           //Declare and initialize a variable named "thisYear" of type long, to hold the current year in milliseconds (aoy unused)

        int mFixN = 31;                                                                             //Declares and initializes an integer variable named mFixN with value 31
        int mFix2 = 0;                                                                              //Declares and initializes an integer variable named mFix2 with value 0

        if (monthN < 5 && monthN > 2) {                                                             //The following if statements check the month, and sets the mFix2 variable accordingly, to make the algorithm work with the varying days of the months
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
        long dateN = ((dayN - 1) * dayT) + ((monthN - 1) * dayT * mFixN - (mFix2 * dayT)) + ((yearN - 1970) * dayT * 365l) + (12l * dayT); //Declares and initializes the variable "dateN", with the algorithm that SHOULD give the correct date translation into a long
        //currentDate.setTime(dateN); //dateN
        // currentDate.toString().substring(4).replace("00:00:00 GMT+00:00 ", "")
        return dateN;                                                                               //returns the calculated long (dateN)
    }
}
