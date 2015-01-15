package polaromatic.service

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.stereotype.Service
import polaromatic.domain.Photo

@Service
@CompileStatic
class MetricsService {

    static final String PHOTO_COUNTER_METRICS_FLICKR = "polaromatized.photos.flickr"
    static final String PHOTO_COUNTER_METRICS_ANDROID = "polaromatized.photos.android"

    @Autowired
    CounterService counterService

    Photo updateMetrics(Photo photo) {

        if (photo.text) {
            counterService.increment(PHOTO_COUNTER_METRICS_ANDROID)
        } else {
            counterService.increment(PHOTO_COUNTER_METRICS_FLICKR)
        }

        photo
    }
}
