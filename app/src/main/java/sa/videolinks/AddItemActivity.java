package sa.videolinks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import sa.videolinks.DatabaseFiles.FilmDatabase;
import sa.videolinks.Utility.DataEora;

public class AddItemActivity extends AppCompatActivity {

    ImageButton addItem;
    ImageButton abort;

    EditText nomeFilm;
    EditText URL;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        abort = (ImageButton) findViewById(R.id.imageButton3);
        addItem = (ImageButton) findViewById(R.id.imageButton5);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NomeFilm = nomeFilm.getText().toString();
                String _URL = URL.getText().toString();
                String Description = description.toString().toString();
                String DataOra = new DataEora().getData() + " " + new DataEora().getOra();

                FilmDatabase filmDatabase = new FilmDatabase(AddItemActivity.this);
                boolean insert = filmDatabase.insertData(NomeFilm, _URL, Description, DataOra);
                Toast.makeText(getApplicationContext(),"***TODO***\nInserimento eseguito",Toast.LENGTH_SHORT).show();
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideoLinksActivity.class);
                startActivity(intent);
            }
        });

        nomeFilm = (EditText) findViewById(R.id.editText3);
        URL = (EditText) findViewById(R.id.editText4);
        description = (EditText) findViewById(R.id.editText2);




    }
}
