package polaromatic.service

import polaromatic.domain.PolaroidRequest

interface PhotoToPolaromatize {

    void process(PolaroidRequest polaroidRequest)

}