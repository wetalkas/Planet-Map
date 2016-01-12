package info.fandroid.mindmap.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Vitaly on 11.01.2016.
 */
public class SupportingDialog {
    public static void longClickDialog(Context context, DialogInterface.OnClickListener onItemClickListener) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        String[] dialogElements = new String[]{"Edit", "Delete"};
        adb.setItems(dialogElements, onItemClickListener);
        adb.show();
    }

    public static void removeDialog(Context context, DialogInterface.OnClickListener positiveButtonListener) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setTitle("Delete this?");

        adb.setPositiveButton("Yes", positiveButtonListener);
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        adb.show();
    }
}
