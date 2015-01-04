package polaromatic.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import polaromatic.domain.Photo
import spock.lang.Specification

class BrowserPushServiceSpec extends Specification {

    void 'push a converted photo to the browser'() {
        given: 'a photo'
            def input = new File('src/test/resources/photo.jpg')
            def output = File.createTempFile("output", "")

            def photo = new Photo(input: input.path, output: output.path)

        and: 'a mocked SimpMessagingTemplate'
            def simpMessagingTemplate = Mock(SimpMessagingTemplate)

        and: 'the push service'
            def browserPushService = new BrowserPushService(template: simpMessagingTemplate)

        when: 'pushing the photo to the browser'
            browserPushService.pushToBrowser(photo)

        then: 'the photo is pushed'
            1 * simpMessagingTemplate.convertAndSend('/notifications/photo', "")
    }
}