package polaromatic.service

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import polaromatic.domain.Photo
import polaromatic.domain.PolaroidRequest

@Slf4j
@Service
@CompileStatic
class FileService {

    Photo preprocessFile(File file) {
        def pr = new PolaroidRequest(file, "")
        this.preprocessFile(pr)
    }

    Photo preprocessFile(PolaroidRequest polaroidRequest) {
        String outputFile = File.createTempFile("output", ".png").path

        return new Photo(input: polaroidRequest.inputFile.absolutePath, output: outputFile, text: polaroidRequest.text)
    }

    void deleteTempFiles(Photo photo) {
        [photo.input, photo.output].each { file ->
            log.debug "Deleting file: ${file}"
            new File(file).delete()
        }
    }
}