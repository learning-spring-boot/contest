package polaromatic

import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

import static groovyx.gpars.GParsPool.withPool

@Slf4j
@EnableScheduling
@Grab('org.jsoup:jsoup:1.8.1')
@Grab('commons-io:commons-io:2.4')
@Grab('org.codehaus.gpars:gpars:1.2.1')
class FlickrDownloader {

    static final String FLICKER_INTERESTING_URL = "https://www.flickr.com/explore/interesting/7days"
    static final String WORK_DIR = "./work"
    File workDir = new File(WORK_DIR)

    @Scheduled(fixedRate = 30000L)
    void downloadFlickrInteresting() {
        def photos = extractPhotosFromFlickr()

        withPool {
            photos.eachParallel { photo ->
                log.info "Downloading photo ${photo}"
                def tempFile = download(photo)

                FileUtils.moveFileToDirectory(tempFile, workDir, true)
            }
        }
    }

    private List extractPhotosFromFlickr() {
        Document doc = Jsoup.connect(FLICKER_INTERESTING_URL).get()
        Elements images = doc.select("img.pc_img")

        def photos = images
            .listIterator()
            .collect { it.attr('src').replace('_m.jpg', '_b.jpg') }

        photos
    }

    private File download(String url) {
        def tempFile = File.createTempFile('flickr_downloader', '')
        tempFile.withOutputStream { out ->
            out << url.toURL().openStream()
        }

        tempFile
    }
}