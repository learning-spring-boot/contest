package polaromatic.app.service

import retrofit.RestAdapter

class RestServiceFactory {

    static <T> T getService(String baseUrl, Class<T> clazz) {

        new RestAdapter.Builder()
            .setEndpoint(baseUrl)
            .build()
            .create(clazz)
    }
}