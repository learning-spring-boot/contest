package polaromatic.app.common

import android.content.Context
import android.widget.Toast
import com.arasthel.swissknife.annotations.OnUIThread
import groovy.transform.CompileStatic

@CompileStatic
trait Toastable {
    @OnUIThread
    void showToastMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
