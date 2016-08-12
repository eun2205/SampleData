package example.tacademy.sampledata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SharedActivity extends AppCompatActivity {
    EditText emailView, passwordView;
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private static final String PREF_NAME = "my_settings";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        emailView = (EditText) findViewById(R.id.edit_email);
        passwordView = (EditText) findViewById(R.id.edit_password);

        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();
//                mEditor.putString(KEY_EMAIL, email);
//                mEditor.putString(KEY_PASSWORD, password);
//                mEditor.commit();
                PropertyManager.getInstance().setEmail(email);
                PropertyManager.getInstance().setPassword(password);
            }
        });

//        mPrefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPrefs.edit();

//        String email = mPrefs.getString(KEY_EMAIL,"");
//        String password = mPrefs.getString(KEY_PASSWORD, "");
        String email = PropertyManager.getInstance().getEmail();
        String password = PropertyManager.getInstance().getPassword();
        emailView.setText(email);
        passwordView.setText(password);
    }
}
