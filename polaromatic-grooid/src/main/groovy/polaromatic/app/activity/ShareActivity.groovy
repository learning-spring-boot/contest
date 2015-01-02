package polaromatic.app.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.squareup.picasso.Picasso
import groovy.transform.CompileStatic
import polaromatic.app.R

@CompileStatic
public class ShareActivity extends Activity {

    private static final int IMAGE_DIMENSION = 1000

    @InjectView(R.id.share_image)
    ImageView shareImageView

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
}