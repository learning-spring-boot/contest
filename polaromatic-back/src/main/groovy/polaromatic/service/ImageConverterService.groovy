package polaromatic.service

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.im4java.core.ConvertCmd
import org.im4java.core.IMOperation
import org.springframework.stereotype.Service
import polaromatic.domain.Photo

@Slf4j
@Service
@CompileStatic
class ImageConverterService {

    private static final String DEFAULT_CAPTION = "#LearningSpringBoot with Polaromatic\\n"

    Random rnd = new Random()

    Photo applyEffect(Photo photo) {
        log.debug "Applying effect to file: ${photo.input}..."

        def inputFile = photo.input
        def outputFile = photo.output

        double polaroidRotation = rnd.nextInt(6).toDouble()
        String caption = photo.text ?: DEFAULT_CAPTION

        def op = new IMOperation()
        op.addImage(inputFile)
        op.thumbnail(300, 300)
            .set("caption", caption)
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


