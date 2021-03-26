package com.e.handmade_patterns.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.e.handmade_patterns.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Help {
    private Context context;
    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private Bitmap bitmap;

    public Help(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.appCompatActivity = (AppCompatActivity) activity;
    }

    public void saveAndTakeScreenShot() {
        bitmap = ScreenshotUtil.getInstance().takeScreenshotForScreen(activity);
        requestPermissionAndSave();
    }

    private void requestPermissionAndSave() {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (bitmap != null) {
                            FileUtil.getInstance().storeBitmap(bitmap,context);
                            Toast.makeText(context,R.string.save_btn_toast_done, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, R.string.save_btn_toast_failed, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(context,R.string.storage_permission_toast, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public boolean showBackDialog(final Fragment fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(R.string.dialog_back_pressed_message);
        builder.setPositiveButton(R.string.yes_dialog_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_framelayout, fragment)
                        .commit();
            }
        });
        builder.setNegativeButton(R.string.no_dialog_button,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Constants.BLACK_COLOR);
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        return true;
    }

    public boolean showReloadDialog(final Fragment fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(R.string.dialog_reload_message);
        builder.setPositiveButton(R.string.yes_dialog_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appCompatActivity.getSupportFragmentManager().popBackStack();
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_framelayout, fragment)
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(context, R.string.dialog_reload_toast_message, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.no_dialog_button,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Constants.BLACK_COLOR);
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        return true;
    }
}
