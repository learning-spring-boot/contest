package polaromatic.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import polaromatic.domain.PolaroidRequest
import polaromatic.service.PhotoToPolaromatize

@Controller
class PolaromaticController {

    @Autowired
    PhotoToPolaromatize polaromatizeService

    @RequestMapping("/")
    def home() {
        new ModelAndView('home')
    }

    @ResponseBody
    @RequestMapping(value = "/upload-photo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    void handleFileUpload(@RequestParam("photo") MultipartFile photo,
                         @RequestParam("text") String text) {

        if (!photo.isEmpty()) {
            def tempFile = File.createTempFile("temp_", "")
            photo.transferTo(tempFile)

            def polaroidRequest = new PolaroidRequest(tempFile, text)

            polaromatizeService.process(polaroidRequest)
        }
    }
}