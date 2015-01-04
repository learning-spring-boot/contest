package polaromatic.controller

import org.springframework.mock.web.MockMultipartFile
import polaromatic.service.PhotoToPolaromatize
import spock.lang.Specification

class PolaromaticControllerSpec extends Specification {

    def polaromatizeService = Mock(PhotoToPolaromatize)

    void 'upload a photo'() {
        given: 'a photo to upload'
            def file = new File('src/test/resources/photo.jpg')
            def multipart = new MockMultipartFile('photo', file.bytes)

        and: 'the controller'
            def polaromaticController = new PolaromaticController(polaromatizeService: polaromatizeService)

        when: 'uploading the file'
            polaromaticController.handleFileUpload(multipart, "text")

        then: 'the file processed'
            1 * polaromatizeService.process(_)
    }

    void 'try to upload an empty photo'() {
        given: 'a photo to upload'
            def multipart = new MockMultipartFile('photo', "".bytes)

        and: 'the controller'
            def polaromaticController = new PolaromaticController(polaromatizeService: polaromatizeService)

        when: 'uploading the file'
            polaromaticController.handleFileUpload(multipart, "text")

        then: 'the file is not processed'
            0 * polaromatizeService.process(_)
    }
}