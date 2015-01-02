package polaromatic.app.activity

import android.app.Activity
import android.os.Bundle
import groovy.transform.CompileStatic
import polaromatic.app.R

@CompileStatic
public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
    }
}
