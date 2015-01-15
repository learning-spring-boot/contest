package polaromatic.app.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import bolts.Task
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnUIThread
import com.squareup.picasso.Picasso
import de.greenrobot.event.EventBus
import groovy.transform.CompileStatic
import polaromatic.app.R
import polaromatic.app.service.PolaromaticRest
import polaromatic.app.service.RestServiceFactory
import polaromatic.app.service.events.BackendEvent
import polaromatic.app.util.Toastable
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
        EventBus.default.register(this)

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

    @Override
    boolean onCreateOptionsMenu(Menu menu) {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    @Override
    boolean onOptionsItemSelected(MenuItem item) {
        int id = item.itemId

        if (id == R.id.action_about) {
            showAbout()
            return true
        } else if (id == R.id.action_settings) {
            showSettings()
        }

        return super.onOptionsItemSelected(item)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy()
        EventBus.default.unregister(this)
    }

    @OnClick(R.id.share_polaromatize_button)
    void polaromatize() {
        ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.share_waiting_spinner))

        Task.callInBackground {
            String textToShare = shareEditText.text.toString()
            File imageToShare = extractImage(shareImageView)

            TypedFile typedPhoto = new TypedFile("image/*", imageToShare)
            TypedString typedText = new TypedString(textToShare)

            polaromaticRest.uploadPhoto(typedPhoto, typedText)

            imageToShare.delete()
            showToastMessage(getString(R.string.share_ok_msg))
            dialog.dismiss()
            finish()
        }
    }

    PolaromaticRest getPolaromaticRest() {
        SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREFS_NAME, 0)
        RestServiceFactory.getService(preferences.getString(SettingsActivity.BACKEND_URL_KEY, ""), PolaromaticRest)
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

    void onEventBackgroundThread(BackendEvent backendError) {
        showToastMessage(getString(R.string.share_backend_error))
        finish()
    }

    @OnUIThread
    void showAbout() {
        new AlertDialog.Builder(this)
            .setIcon(R.drawable.polaromatic_logo)
            .setTitle(R.string.app_name)
            .setView(layoutInflater.inflate(R.layout.about, null, false))
            .create()
            .show()
    }

    @OnUIThread
    void showSettings() {
        startActivity(new Intent(applicationContext, SettingsActivity))
    }
}