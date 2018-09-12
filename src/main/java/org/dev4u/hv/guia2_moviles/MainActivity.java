package org.dev4u.hv.guia2_moviles;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText txtURL;
    private TextView lblEstado;
    private Button btnDescargar;
    private RadioButton rbncambiar;
    private RadioButton rbnnocambiar;
    private EditText txtnombre;
    private ProgressBar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicializar
        this.txtURL       = (EditText) findViewById(R.id.txtURL);
        this.lblEstado    = (TextView) findViewById(R.id.lblEstado);
        this.btnDescargar = (Button)   findViewById(R.id.btnDescargar);
        this.rbncambiar = (RadioButton) findViewById(R.id.rbncambiar);
        this.rbnnocambiar = (RadioButton) findViewById(R.id.rbnnocambiar);
        this.txtnombre = (EditText) findViewById(R.id.txtnombre);
        this.barra = (ProgressBar) findViewById(R.id.Progressporcentaje);

        this.rbncambiar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtnombre.setEnabled(true);
                    txtnombre.setHint("Image.jpg ");
                    txtnombre.setText("");
                }
            }
        });
        this.rbnnocambiar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtnombre.setEnabled(false);
                    txtnombre.setText(txtURL.getText());
                }
            }
        });
        //evento onClick
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Descargar(
                        MainActivity.this,
                        lblEstado,
                        btnDescargar,
                        barra


                ).execute(txtURL.getText().toString());
            }
        });



        verifyStoragePermissions(this);
    }

    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
