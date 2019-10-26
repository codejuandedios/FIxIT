package com.example.fixit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Spinner;
import android.widget.Toast;

import com.mysql.fabric.Response;
import com.mysql.jdbc.Statement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.security.AccessController.getContext;

public class Reporte_Usuario extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "MainActivity";
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private ResultSet rs = null;

    Spinner spinner;
    Spinner spinneredifi;
    Spinner spinnerSal;
    Button btnEnviar,btnFoto;
    ImageView imgFoto;
    ProgressDialog progreso;
    EditText descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte__usuario);

        getSupportActionBar().hide();

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterTipoProblema = ArrayAdapter.createFromResource(this, R.array.TipoProblema, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterTipoProblema);

        spinneredifi = (Spinner) findViewById(R.id.spinnerEdificio);
        spinnerSal = (Spinner) findViewById(R.id.spinnerSalon);
        descripcion = findViewById(R.id.txtdescripcion);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Modulos, android.R.layout.simple_spinner_item);
        spinneredifi.setAdapter(adapter);
        spinneredifi.setOnItemSelectedListener(this);


        btnFoto = findViewById(R.id.btnTomaFoto);
        btnEnviar = findViewById(R.id.btn_Enviar);
        imgFoto = findViewById(R.id.foto);

        if(solicitaPermisosVersionesSuperiores()){
            btnFoto.setEnabled(true);
        }else{
            btnFoto.setEnabled(false);
        }


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarRegistro();
                descripcion.setText("");
                imgFoto.setImageResource(R.drawable.camarita);
                spinner.setSelection(0);
                spinneredifi.setSelection(0);
                spinnerSal.setSelection(0);


            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        int[] Salones = {R.array.Modulo, R.array.F, R.array.E,R.array.M};

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, Salones[position],
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSal.setAdapter(adapter);

    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(Reporte_Usuario.this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abriCamara();
                }else{
                    if (opciones[i].equals("Elegir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    public void AgregarRegistro(){
        try {
            progreso=new ProgressDialog(Reporte_Usuario.this);
            progreso.setMessage("Cargando...");
            progreso.show();

            String imagen =convertirImgString(bitmap);
            PreparedStatement pst = conexionBD().prepareStatement("insert into reporte(carne, TipoProblema, Descripcion, Imagen, Modulo, Salon ) " +
                            "values( "+UsuarioPerfil.getCarne()+"," +
                            "'"+spinner.getSelectedItem().toString()+"'," +
                            "'"+descripcion.getText().toString()+"'," +
                            "'"+imagen+"'," +
                            "'"+spinneredifi.getSelectedItem().toString()+"'," +
                            "'"+spinnerSal.getSelectedItem().toString()+"')");

            pst.executeUpdate();
            progreso.hide();
            Toast.makeText(Reporte_Usuario.this,"REGISTRO EXITOSO",Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            progreso.hide();
            Toast.makeText(Reporte_Usuario.this,e.getMessage(),Toast.LENGTH_SHORT)
                    .show();
        }
    }
    private void abriCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if (isCreada == false) {
            isCreada = miFile.mkdirs();
        }

        if (isCreada == true) {
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN + File.separator + nombre;//indicamos la ruta de almacenamiento

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            ////
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authorities = Reporte_Usuario.this.getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(Reporte_Usuario.this, authorities, fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent, COD_FOTO);

            ////

        }
    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imgFoto.setImageURI(miPath);

                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(Reporte_Usuario.this.getContentResolver(),miPath);
                        imgFoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case COD_FOTO:
                    MediaScannerConnection.scanFile(Reporte_Usuario.this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Path",""+path);
                                }
                            });

                    bitmap= BitmapFactory.decodeFile(path);
                    imgFoto.setImageBitmap(bitmap);

                    break;
            }
            bitmap=redimensionarImagen(bitmap,600,800);
        }

        private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

            int ancho=bitmap.getWidth();
            int alto=bitmap.getHeight();

            if(ancho>anchoNuevo || alto>altoNuevo){
                float escalaAncho=anchoNuevo/ancho;
                float escalaAlto= altoNuevo/alto;

                Matrix matrix=new Matrix();
                matrix.postScale(escalaAncho,escalaAlto);

                return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

            }else{
                return bitmap;
            }


        }


        //permisos
        ////////////////


        private boolean solicitaPermisosVersionesSuperiores(){
            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
                return true;
            }

            //validamos si los permisos ya fueron aceptados
            if((Reporte_Usuario.this.checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&Reporte_Usuario.this.checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
                return true;
            }


            if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
                cargarDialogoRecomendacion();
            }else{
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
            }

            return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode==MIS_PERMISOS){
                if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                    Toast.makeText(Reporte_Usuario.this,"Permisos aceptados",Toast.LENGTH_SHORT);
                    btnFoto.setEnabled(true);
                }
            }else{
                solicitarPermisosManual();
            }
        }


        private void solicitarPermisosManual() {
            final CharSequence[] opciones={"si","no"};
            final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(Reporte_Usuario.this);//estamos en fragment
            alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
            alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i].equals("si")){
                        Intent intent=new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri=Uri.fromParts("package",Reporte_Usuario.this.getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Reporte_Usuario.this,"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                }
            });
            alertOpciones.show();
        }


        private void cargarDialogoRecomendacion() {
            AlertDialog.Builder dialogo=new AlertDialog.Builder(Reporte_Usuario.this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                }
            });
            dialogo.show();
        }
    public Connection conexionBD(){
        Connection conexion = null;
        String host = "192.168.1.13";
        String port = "3306";
        String dbName = "fixit";
        String userName = "root";
        String password = "admon";
        try{

            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, userName, password);

        }catch (Exception e) {
            Toast.makeText(Reporte_Usuario.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }





    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
