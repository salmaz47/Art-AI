import android.content.Context
import android.content.SharedPreferences
import com.salmee.artai.model.Image

object SharedPreferencesHelper {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_GENDER = "gender" // Keep existing keys if needed
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_IS_GUEST = "is_guest"
    private const val KEY_FAVORITE_IMAGE_IDS = "favorite_image_ids" // Key for storing favorite IDs

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Auth & Guest Mode --- 
    fun saveAuthToken(context: Context, token: String) {
        getPrefs(context).edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(context: Context): String? {
        return getPrefs(context).getString(KEY_AUTH_TOKEN, null)
    }

    fun clearAuthToken(context: Context) {
        getPrefs(context).edit().remove(KEY_AUTH_TOKEN).apply()
    }

    fun saveGender(context: Context, gender: String) {
        getPrefs(context).edit().putString(KEY_GENDER, gender).apply()
    }

    fun getGender(context: Context): String {
        return getPrefs(context).getString(KEY_GENDER, "") ?: ""
    }

    fun setGuestMode(context: Context, isGuest: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_IS_GUEST, isGuest).apply()
        if (isGuest) {
            clearAuthToken(context) // Ensure no token in guest mode
            // Optionally clear favorites when entering guest mode?
            // clearFavorites(context)
        }
    }

    fun isGuestMode(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_GUEST, false)
    }

    fun clearAll(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    // --- Favorites (Local Storage) --- 

    fun getFavoriteImageIds(context: Context): MutableSet<String> {
        // Retrieve the set of favorite IDs, default to an empty set
        return getPrefs(context).getStringSet(KEY_FAVORITE_IMAGE_IDS, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
    }

    fun isFavorite(context: Context, imageId: String): Boolean {
        return getFavoriteImageIds(context).contains(imageId)
    }

    fun addFavorite(context: Context, imageId: String) {
        val favorites = getFavoriteImageIds(context)
        if (favorites.add(imageId)) { // Add returns true if the set was changed
            getPrefs(context).edit().putStringSet(KEY_FAVORITE_IMAGE_IDS, favorites).apply()
        }
    }

    fun removeFavorite(context: Context, imageId: String) {
        val favorites = getFavoriteImageIds(context)
        if (favorites.remove(imageId)) { // Remove returns true if the set was changed
            getPrefs(context).edit().putStringSet(KEY_FAVORITE_IMAGE_IDS, favorites).apply()
        }
    }

    fun toggleFavorite(context: Context, imageId: String): Boolean {
        val favorites = getFavoriteImageIds(context)
        val isCurrentlyFavorite = favorites.contains(imageId)
        if (isCurrentlyFavorite) {
            favorites.remove(imageId)
        } else {
            favorites.add(imageId)
        }
        getPrefs(context).edit().putStringSet(KEY_FAVORITE_IMAGE_IDS, favorites).apply()
        return !isCurrentlyFavorite // Return the new favorite status
    }

    fun clearFavorites(context: Context) {
        getPrefs(context).edit().remove(KEY_FAVORITE_IMAGE_IDS).apply()
    }

    // Function to sync local favorites based on fetched data (for logged-in users)
    fun syncFavoritesFromData(context: Context, images: List<Image>) {
        if (isGuestMode(context)) return // Don't sync for guests

        val currentLocalFavorites = getFavoriteImageIds(context)
        val serverFavoriteIds = images.filter { it.isLoved }.map { it.id }.toSet()
        val serverNotFavoriteIds = images.filterNot { it.isLoved }.map { it.id }.toSet()

        var changed = false
        // Add server favorites that are not local
        serverFavoriteIds.forEach {
            if (currentLocalFavorites.add(it)) changed = true
        }
        // Remove local favorites that are not server favorites (within the fetched list)
        serverNotFavoriteIds.forEach {
             if (currentLocalFavorites.remove(it)) changed = true
        }

        if (changed) {
            getPrefs(context).edit().putStringSet(KEY_FAVORITE_IMAGE_IDS, currentLocalFavorites).apply()
        }
    }
}

