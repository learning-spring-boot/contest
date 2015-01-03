package polaromatic.service

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.apache.commons.lang.RandomStringUtils
import org.springframework.stereotype.Service
import polaromatic.domain.Photo
import polaromatic.domain.PolaroidRequest

import java.nio.file.Paths

@Slf4j
@Service
@CompileStatic
class FileService {

    Photo preprocessFile(File file) {
        def pr = new PolaroidRequest(file, "")
        this.preprocessFile(pr)
    }

    Photo preprocessFile(PolaroidRequest polaroidRequest) {
        String filename = RandomStringUtils.randomAlphanumeric(10)

        String outputPath = File.createTempDir()
        String outputFilename = "${new Date().time}_${filename}.png"
        def outputFile = Paths.get(outputPath, outputFilename)

        return new Photo(input: polaroidRequest.inputFile.absolutePath, output: outputFile, text: polaroidRequest.text)
    }

    void deleteTempFiles(Photo photo) {
        [photo.input, photo.output].each { file ->
            log.debug "Deleting file: ${file}"
            new File(file).delete()
        }
    }
}