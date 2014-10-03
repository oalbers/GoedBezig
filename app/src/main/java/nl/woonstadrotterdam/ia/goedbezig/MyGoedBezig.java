package nl.woonstadrotterdam.ia.goedbezig;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MyGoedBezig extends Activity {
    final int ACTIVITY_CHOOSE_FILE = 1;
    Button but;
    Button but2;
    EditText idee;
    EditText toelichting;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goed_bezig);

        but = (Button) findViewById(R.id.button);
        idee = (EditText) findViewById(R.id.idee);
        toelichting =  (EditText) findViewById(R.id.toelichting);
        final StringBuffer buffer = new StringBuffer();

        but2 = (Button) findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            }
        });



        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + "goedbezig@woonstadrotterdam.nl"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "GoedBezig idee");

                buffer.delete(0,buffer.length());
                buffer.append("Idee:\n");
                buffer.append(idee.getText());
                buffer.append("\n");
                buffer.append("Toelichting:\n");
                buffer.append(toelichting.getText());
                buffer.append("\n");

                emailIntent.putExtra(Intent.EXTRA_TEXT, buffer.toString());
                //Uri uri = Uri.fromFile(new File(xmlFilename));
                if (uri != null) {
                    emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
                }
                try {
                    startActivity(Intent.createChooser(emailIntent, "Verstuur verbeter email met..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MyGoedBezig.this, "Geen email clients geïnstalleerd.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ACTIVITY_CHOOSE_FILE: {
                if (resultCode == RESULT_OK){
                    uri = data.getData();
                    Toast.makeText(MyGoedBezig.this, uri.getPath(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
