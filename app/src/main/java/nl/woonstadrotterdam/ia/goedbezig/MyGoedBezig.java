package nl.woonstadrotterdam.ia.goedbezig;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MyGoedBezig extends Activity {
    final int ACTIVITY_CHOOSE_FILE = 1;
    Button but;
    Button but2;
    Button but3;
    EditText idee;
    EditText toelichting;
    Uri uri;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goed_bezig);

        but = (Button) findViewById(R.id.button);
        idee = (EditText) findViewById(R.id.idee);
        toelichting =  (EditText) findViewById(R.id.toelichting);

        iv = (ImageView) findViewById(R.id.imageView4);
        final StringBuffer buffer = new StringBuffer();
        but3 = (Button) findViewById(R.id.button3);

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

        but3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               uri = null;
               iv.setImageDrawable(null);
            }
        });


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:" + "verbetermee@woonstadrotterdam.nl"));
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "verbetermee@woonstadrotterdam.nl"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "GoedBezig idee");
                emailIntent.setType("message/rfc822");
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                buffer.append("Idee:\n");
                buffer.append(idee.getText());
                buffer.append("\n");
                buffer.append("Toelichting:\n");
                buffer.append(toelichting.getText());
                buffer.append("\n");

                emailIntent.putExtra(Intent.EXTRA_TEXT, buffer.toString());

                if (uri != null) {
                    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                   emailIntent.setType("application/image");
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
                    Toast.makeText(MyGoedBezig.this, "Bijlage toegevoegd", Toast.LENGTH_SHORT).show();
                    iv.setImageURI(uri);
                }
            }
        }
    }

}
