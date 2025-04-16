import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_GENDER = "gender"

    fun saveGender(context: Context, gender: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_GENDER, gender).apply()
    }

    fun getGender(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_GENDER, "") ?: ""
    }
}
