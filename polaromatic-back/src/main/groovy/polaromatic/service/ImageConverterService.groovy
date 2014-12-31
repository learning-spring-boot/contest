package polaromatic.service

import groovy.util.logging.Slf4j
import org.im4java.core.ConvertCmd
import org.im4java.core.IMOperation
import org.springframework.stereotype.Service
import polaromatic.domain.Photo

@Slf4j
@Service
class ImageConverterService {

    def rnd = new Random()
    private static final String DEFAULT_CAPTION = "#LearningSpringBoot with Polaromatic\\n"

    Photo applyEffect(Photo photo) {
        log.debug "Applying effect to file: ${photo.input}..."

        def inputFile = photo.input
        def outputFile = photo.output

        int polaroidRotation = rnd.nextInt(6)

        def op = new IMOperation()
        op.addImage(inputFile)
        op.thumbnail(300, 300)
            .set("caption", DEFAULT_CAPTION)
            .gravity("center")
            .pointsize(20)
            .background("black")
            .polaroid(rnd.nextBoolean() ? polaroidRotation : -polaroidRotation)
            .addImage(outputFile)

        def command = new ConvertCmd()
        command.run(op)

        photo
    }
}




