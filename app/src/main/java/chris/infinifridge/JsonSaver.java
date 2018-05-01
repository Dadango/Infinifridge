package chris.infinifridge;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonSaver extends JSONObject {
    public JsonSaver(Entries Entry) {                                                               //Constructor to save information of Entries
        try {                                                                                       //Exception handling for JSONException
            this.put("Name", Entry.name);                                                     //Puts values for name, amount etc. in the JsonSaver object using the Entries object's variables
            this.put("Amount", Entry.amount);                                                 //
            this.put("AmountType", Entry.amountType);                                         //
            this.put("ImageID", Entry.imageId);                                               //
            this.put("Storage", Entry.storage);                                               //
            this.put("ExpD", Entry.expirationDate[0]);                                        //
            this.put("ExpM", Entry.expirationDate[1]);                                        //
            this.put("ExpY", Entry.expirationDate[2]);                                        //

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
