package polaromatic.app.service

import de.greenrobot.event.EventBus
import polaromatic.app.activity.SettingsActivity
import polaromatic.app.service.events.BackendEvent
import retrofit.ErrorHandler
import retrofit.RestAdapter
import retrofit.RetrofitError

class RestServiceFactory {

    static <T> T getService(String baseUrl, Class<T> clazz) {
        ErrorHandler eh = { RetrofitError cause ->
            EventBus.default.post(new BackendEvent())
            cause.response
        }

        return new RestAdapter.Builder()
            .setEndpoint(baseUrl ?: SettingsActivity.DEFAULT_BACKEND_URL)
            .setErrorHandler(eh)
            .build()
            .create(clazz)
    }
}