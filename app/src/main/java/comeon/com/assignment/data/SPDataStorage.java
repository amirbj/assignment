package comeon.com.assignment.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amir on 8/9/18.
 */

public class SPDataStorage {

    public static void saveData(Context context,String key, String url){
        SharedPreferences sp = context.getSharedPreferences("SP_WEBVIEW", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, url);
        editor.commit();


    }

    public  static String loadData(Context context, String key, String defult){

        SharedPreferences sp = context.getSharedPreferences("SP_WEBVIEW", Context.MODE_PRIVATE);
        String url = sp.getString(key,defult);
        return url;
    }
}
