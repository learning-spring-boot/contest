package polaromatic.app.activity

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import groovy.transform.CompileStatic
import polaromatic.app.R
import polaromatic.app.util.Toastable

@CompileStatic
class SettingsActivity extends Activity implements Toastable {

    static final String PREFS_NAME = "settings_pref"
    static final String BACKEND_URL_KEY = "backend_url"
    static final String DEFAULT_BACKEND_URL = "http://polaromatic.noip.me"

    @InjectView(R.id.backendUrl)
    EditText backendUrlEditText

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        SwissKnife.inject(this)

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0)
        String backendUrl = settings.getString(BACKEND_URL_KEY, "")

        if (!backendUrl) {
            storeBackendUrl(DEFAULT_BACKEND_URL)
        }

        backendUrlEditText.text = backendUrl ?: DEFAULT_BACKEND_URL
    }

    @OnClick(R.id.settings_save_button)
    void saveSettings() {
        storeBackendUrl(backendUrlEditText.text.toString())

        showToastMessage(getString(R.string.settings_saved_ok))
        finish()
    }

    private void storeBackendUrl(String backendUrl) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0)
        SharedPreferences.Editor editor = settings.edit()

        editor.putString(BACKEND_URL_KEY, backendUrl)
        editor.commit()
    }
}