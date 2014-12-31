package polaromatic.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import polaromatic.domain.Photo

@Slf4j
@Service
class BrowserPushService {

    @Autowired
    SimpMessagingTemplate template

    Photo pushToBrowser(Photo photo) {
        log.debug "Pushing file to browser: ${photo.output}"

        String imageB64 = new File(photo.output).bytes.encodeBase64().toString()

        template.convertAndSend "/notifications/photo", imageB64

        return photo
    }
}