package polaromatic.service

import polaromatic.domain.Photo
import polaromatic.domain.PolaroidRequest
import spock.lang.Specification

class FileServiceSpec extends Specification {

    def fileService = new FileService()

    void 'preprocess a polaroid request'() {
        given: 'a polaroid request'
            def pr = new PolaroidRequest(new File(filePath), text)

        when: 'preprocessing the file'
            def photo = fileService.preprocessFile(pr)

        then: 'the photo object is created correctly'
            photo.input.contains filePath
            photo.output != null
            photo.text == text

        where:
            filePath = 'src/test/resources/photo.jpg'
            text = 'Polaromatic #FTW'
    }

    void 'preprocess a file without a text'() {
        given: 'a file'
            def file = new File('src/test/resources/photo.jpg')

        when: 'preprocessing the file'
            def photo = fileService.preprocessFile(file)

        then: 'the photo object is created with an empty text'
            photo.text == ""
    }

    void 'delete photo temporary file'() {
        given: 'an input and an output file'
            def input = File.createTempFile("input", "")
            def output = File.createTempFile("output", "")

        and: 'a photo object'
            def photo = new Photo(input: input.path, output: output.path)

        when: 'cleaning the photo'
            fileService.deleteTempFiles(photo)

        then: 'the files have been deleted'
            !input.exists()
            !output.exists()
    }
}