package polaromatic.app.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import bolts.Task
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.squareup.picasso.Picasso
import groovy.transform.CompileStatic
import polaromatic.app.R
import polaromatic.app.common.Toastable
import polaromatic.app.service.PolaromaticRest
import polaromatic.app.service.RestServiceFactory
import retrofit.mime.TypedFile
import retrofit.mime.TypedString

@CompileStatic
public class ShareActivity extends Activity implements Toastable {

    private static final int IMAGE_DIMENSION = 1000

    @InjectView(R.id.share_image)
    ImageView shareImageView

    @InjectView(R.id.share_text)
    EditText shareEditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        SwissKnife.inject(this)

        Intent intent = intent
        String action = intent.action
        String type = intent.type

        if (Intent.ACTION_SEND == action && type?.startsWith("image/")) {
            Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM)

            if (imageUri) {
                Picasso.with(applicationContext)
                    .load(imageUri)
                    .resize(IMAGE_DIMENSION, IMAGE_DIMENSION)
                    .centerInside()
                    .into(shareImageView)
            }
        }
    }

    @OnClick(R.id.share_polaromatize_button)
    void polaromatize() {
        Task.callInBackground {
            String textToShare = shareEditText.text.toString()
            File imageToShare = extractImage(shareImageView)

            TypedFile typedPhoto = new TypedFile("image/*", imageToShare)
            TypedString typedText = new TypedString(textToShare)

            polaromaticRest.uploadPhoto(typedPhoto, typedText)

            imageToShare.delete()
            showToastMessage(applicationContext, getString(R.string.share_ok_msg))
            finish()
        }
    }

    PolaromaticRest getPolaromaticRest() {
        SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREFS_NAME, 0)
        RestServiceFactory.getService(preferences.getString(SettingsActivity.BACKEND_URL, ""), PolaromaticRest)
    }

    private File extractImage(ImageView imageView) {
        File tempFile = File.createTempFile("temp_", "")
        FileOutputStream output = new FileOutputStream(tempFile)

        Bitmap bitmap = ((BitmapDrawable) imageView.drawable).bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, output)

        output?.flush()
        output?.close()

        return tempFile
    }
}