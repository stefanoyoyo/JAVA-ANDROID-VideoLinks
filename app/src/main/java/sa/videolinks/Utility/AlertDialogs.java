package sa.videolinks.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import sa.videolinks.VideoLinksActivity;

/**
 * Created by ament on 04/04/2020.
 */

public class AlertDialogs extends Activity {

    public static Context context;
    public static VideoLinksActivity videoLinksActivity = new VideoLinksActivity();

    public void createAlertDialogToChooseViewType(Context contesto, String telefono, String tv, String message, String title) {
        try {
            context = contesto;
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, telefono,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {

                                dialog.dismiss();
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, tv,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent(context, VideoPlayer.class);
                                context.startActivity(intent);
                                dialog.dismiss();
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void createAlertDialogToShowDBDataMiss(Context contesto, String yes, String no, String message, String title) {
        try {
            context = contesto;
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, yes,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                // Ricerca del file JSON da cui estrare i dati
                                if (videoLinksActivity.jsonFileExists())
                                {
                                    // Recupero testo da json e riempio il database
                                    videoLinksActivity.getJsonDataAndFillDatabase();
                                    Toast.makeText(context,
                                            "Riavvia l'app per vedere la lista dei film qualora tutto sia andato a buon fine",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else
                                {
                                    Toast.makeText(context,
                                            "Inserisci in VideoLnks/Config" +
                                                    " il file di configurazione JsonDatabase.json" +
                                                    " contenente i dati da usare per popolare il database",
                                            Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
