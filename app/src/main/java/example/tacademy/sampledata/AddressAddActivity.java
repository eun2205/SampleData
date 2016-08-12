package example.tacademy.sampledata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressAddActivity extends AppCompatActivity {

    EditText nameView, ageView, phoneView, addressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        nameView = (EditText)findViewById(R.id.edit_name);
        ageView = (EditText)findViewById(R.id.edit_age);
        phoneView = (EditText)findViewById(R.id.edit_phone);
        addressView = (EditText)findViewById(R.id.edit_address);

        Button btn = (Button)findViewById(R.id.btn_address_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameView.getText().toString();
                int age = Integer.parseInt(ageView.getText().toString());
                String phone = phoneView.getText().toString();
                String address = addressView.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    Person p = new Person();
                    p.name = name;
                    p.age = age;
                    p.phone = phone;
                    p.address = address;
                    DBManager.getInstance().insert(p);

                    nameView.setText("");
                    ageView.setText("0");
                    phoneView.setText("");
                    addressView.setText("");
                }
            }
        });
    }
}
