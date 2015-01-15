package polaromatic.domain

import groovy.transform.TupleConstructor

@TupleConstructor
class PolaroidRequest {
    File inputFile
    String text
}
