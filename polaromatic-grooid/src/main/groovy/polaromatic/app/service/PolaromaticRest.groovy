package polaromatic.app.service

import retrofit.http.Multipart
import retrofit.http.POST
import retrofit.http.Part
import retrofit.mime.TypedFile
import retrofit.mime.TypedString

interface PolaromaticRest {

    @Multipart
    @POST("/upload-photo")
    Map uploadPhoto(@Part("photo") TypedFile photo, @Part("text") TypedString text)
}
