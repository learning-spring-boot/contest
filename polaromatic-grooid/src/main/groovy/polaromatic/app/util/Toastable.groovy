package polaromatic.app.util

import android.widget.Toast
import com.arasthel.swissknife.annotations.OnUIThread

trait Toastable {
    @OnUIThread
    void showToastMessage(String message) {
        Toast toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
