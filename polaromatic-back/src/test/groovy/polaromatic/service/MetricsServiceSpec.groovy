package polaromatic.service

import org.springframework.boot.actuate.metrics.CounterService
import polaromatic.domain.Photo
import spock.lang.Specification
import spock.lang.Unroll

class MetricsServiceSpec extends Specification {

    def counterService = Mock(CounterService)
    def metricsService = new MetricsService(counterService: counterService)

    @Unroll
    void 'update the metrics for #type photo'() {
        given: 'an Android photo'
            def photo = new Photo(text: text)

        when: 'updating the metrics'
            metricsService.updateMetrics(photo)

        then: 'the Android metrics are updated'
            numExecutionsFlickr * counterService.increment(MetricsService.PHOTO_COUNTER_METRICS_FLICKR)
            numExecutionsAndroid * counterService.increment(MetricsService.PHOTO_COUNTER_METRICS_ANDROID)

        where:
            type         | text      | numExecutionsFlickr | numExecutionsAndroid
            "a Flickr"   | ""        | 1                   | 0
            "an Android" | "my text" | 0                   | 1
    }
}