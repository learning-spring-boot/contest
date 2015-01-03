package polaromatic.app.service

import de.greenrobot.event.EventBus
import polaromatic.app.service.events.BackendEvent
import retrofit.ErrorHandler
import retrofit.RetrofitError

public class PolaromaticErrorHandler implements ErrorHandler {

    @Override
    Throwable handleError(RetrofitError cause) {
        EventBus.default.post(new BackendEvent())

        return cause.response
    }
}