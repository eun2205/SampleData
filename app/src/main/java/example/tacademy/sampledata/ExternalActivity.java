package example.tacademy.sampledata;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExternalActivity extends AppCompatActivity {

    View layout;
    ImageView captureView;
    private static final String FILE_NAME = "my_image.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);
        layout = (View) findViewById(R.id.layout_capture);
        captureView = (ImageView) findViewById(R.id.image_capture);

        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bm = getViewBitmap(layout);
                File file = getImageFile();
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn = (Button) findViewById(R.id.btn_load);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = getImageFile();
                try {
                    FileInputStream fis = new FileInputStream(file);
                    Bitmap bm = BitmapFactory.decodeStream(fis);
                    fis.close();
                    captureView.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        checkPermission();
    }

    private static final int RC_PERMISSION = 100;

    private void checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

//둘 중하나라도 획득되지않았으면(둘 다 획득되어있으면 size가 0이다.)
        if (permissions.size() > 0) {
            boolean isShowUI = false;
            for (String perm : permissions) {
                //사용자에게 퍼미션 획득이유를 설명하는 창을 띄워라
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                    isShowUI = true;
                    break;
                }
            }

            final String[] perms = permissions.toArray(new String[permissions.size()]);

            if (isShowUI) {
                //획득이유를 설명하는 내용
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission");
                builder.setMessage("External Storage");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(ExternalActivity.this, perms, RC_PERMISSION);
                    }
                });
                builder.create().show();
                return;
            }
            ActivityCompat.requestPermissions(this, perms, RC_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions != null) {
            boolean granted = true;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                }
            }
            if (!granted) {
                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private File getImageFile() {
        File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File parent = new File(picture, "my_image");
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return new File(parent, FILE_NAME);
    }

    private Bitmap getViewBitmap(View layout) {
        layout.clearFocus();
        layout.setPressed(false);
        boolean willNotCache = layout.willNotCacheDrawing();
        layout.setWillNotCacheDrawing(false);
        int color = layout.getDrawingCacheBackgroundColor();
        layout.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            layout.destroyDrawingCache();
        }
        layout.buildDrawingCache();
        Bitmap cacheBitmap = layout.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        layout.destroyDrawingCache();
        layout.setWillNotCacheDrawing(willNotCache);
        layout.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
}
