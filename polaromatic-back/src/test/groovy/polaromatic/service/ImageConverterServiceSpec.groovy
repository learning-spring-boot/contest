package polaromatic.service

import polaromatic.domain.Photo
import spock.lang.Specification

class ImageConverterServiceSpec extends Specification {

    def imageConverterService = new ImageConverterService()

    void 'apply polaroid effect to an image'() {
        given: 'a photo'
            def input = new File('src/test/resources/photo.jpg')
            def output = File.createTempFile("output", "")

            def photo = new Photo(input: input.path, output: output.path)

        when: 'applying the efect'
            imageConverterService.applyEffect(photo)

        then: 'the output file size is greater that zero'
            def file = new File(photo.output)
            file.size() > 0
    }
}
