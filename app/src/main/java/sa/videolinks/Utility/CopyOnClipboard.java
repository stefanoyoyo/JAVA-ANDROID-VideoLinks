package sa.videolinks.Utility;

import android.content.Context;

/**
 * Created by ament on 01/08/2017.
 */

public class CopyOnClipboard {

    /**
     * This method allows the user to copy a text into the clip board
     *
     * @param context to set as " this "
     * @param text to copy into the clipboard
     */
    public void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
