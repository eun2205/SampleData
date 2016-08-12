package example.tacademy.sampledata;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImageViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    String[] projection = {MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA};
    String sort = MediaStore.Images.Media.DATE_ADDED + " DESC";

    GridView gridView;
    SimpleCursorAdapter mAdapter;

    private int dataColumnIndex= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        gridView = (GridView) findViewById(R.id.gridView);
        String[] from = {MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA};
        int[] to = {R.id.text_name, R.id.image_icon};
        mAdapter = new SimpleCursorAdapter(this, R.layout.view_image_check, null, from, to, 0);
       mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
           @Override
           public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
               if (columnIndex == dataColumnIndex) {
                   String  path = cursor.getString(columnIndex);
                   ImageView iv = (ImageView)view;
                   Glide.with(ImageViewActivity.this)
                           .load(new File(path))
                           .into(iv);
                   return true;
               }
               return false;
           }
       });
        gridView.setAdapter(mAdapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sort);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_finish) {
            SparseBooleanArray array = gridView.getCheckedItemPositions();
            List<String > files = new ArrayList<>();
            for (int index =0 ; index < array.size(); index++) {
                int position = array.keyAt(index);
                if (array.get(position)){
                    Cursor cursor = (Cursor)gridView.getItemAtPosition(position);
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    files.add(path);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
