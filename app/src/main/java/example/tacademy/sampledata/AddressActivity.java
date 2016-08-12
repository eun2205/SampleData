package example.tacademy.sampledata;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AddressActivity extends AppCompatActivity {

    ListView listView;
    //ArrayAdapter<Person> mAdapter;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
//        listView = (ListView)findViewById(R.id.listView);
//        mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1);
        listView = (ListView) findViewById(R.id.listView);
        String[] from = {AddressContract.Address.COLUMN_NAME,
                AddressContract.Address.COLUMN_AGE,
                AddressContract.Address.COLUMN_PHONE,
                AddressContract.Address.COLUMN_ADDRESS};
        int[] to = {R.id.text_name,
                R.id.text_age,
                R.id.text_phone,
                R.id.text_address};
        mAdapter = new SimpleCursorAdapter(this, R.layout.view_item, null, from, to, 0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == phoneIndex) {
                    String phone = cursor.getString(columnIndex);
                    TextView tv = (TextView) view;
                    StringBuilder sb = new StringBuilder();
                    if (phone.length() > 0) {
                        sb.append(phone.charAt(0));
                    }
                    for (int i = 1; i < phone.length(); i++) {
                        sb.append("*");
                    }
                    tv.setText(sb.toString());
                    return true;
                }
                return false;
            }
        });
        listView.setAdapter(mAdapter);
    }

    int phoneIndex = -1;

    @Override
    protected void onStart() {
        super.onStart();
//        mAdapter.clear();
//        List<Person> list = DBManager.getInstance().getPersonList(null);
//        mAdapter.addAll(list);
        Cursor c = DBManager.getInstance().getPersonCursor(null);
        phoneIndex = c.getColumnIndex(AddressContract.Address.COLUMN_PHONE);
        mAdapter.changeCursor(c);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.changeCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            startActivity(new Intent(this, AddressAddActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
