package polaromatic.app.service

import polaromatic.app.activity.SettingsActivity
import retrofit.RestAdapter

class RestServiceFactory {

    static <T> T getService(String baseUrl, Class<T> clazz) {

        new RestAdapter.Builder()
            .setEndpoint(baseUrl ?: SettingsActivity.DEFAULT_BACKEND_URL)
            .setErrorHandler(new PolaromaticErrorHandler())
            .build()
            .create(clazz)
    }
}