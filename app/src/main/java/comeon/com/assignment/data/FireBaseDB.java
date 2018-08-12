package comeon.com.assignment.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comeon.com.assignment.model.User;

/**
 * Created by amir on 8/12/18.
 */

public class FireBaseDB {







    public static void saveinFirebaseDB( User user){

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference db = database.getReference();
            user.setUid(db.push().getKey());
            db.child("user").child(user.getUid()).setValue(user);
        }catch(Exception e){
            e.printStackTrace();

        }







    }
}
