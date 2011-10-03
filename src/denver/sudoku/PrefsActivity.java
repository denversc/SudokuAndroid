package denver.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefsActivity extends PreferenceActivity {

    private static final String KEY_HINTS = "hints";
    private static final String KEY_MUSIC = "music";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.settings);
    }

    private static boolean getBooleanPreference(Context context, String key) {
        final SharedPreferences prefs =
            PreferenceManager.getDefaultSharedPreferences(context);
        final boolean result = prefs.getBoolean(key, true);
        return result;
    }

    public static boolean isHintsEnabled(Context context) {
        return getBooleanPreference(context, KEY_HINTS);
    }

    public static boolean isMusicEnabled(Context context) {
        return getBooleanPreference(context, KEY_MUSIC);
    }
}
