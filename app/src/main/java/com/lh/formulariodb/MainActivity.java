package com.lh.formulariodb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lh.formulariodb.Data.MyDbHelper;
import com.lh.formulariodb.Models.Ciudad;
import com.omarshehe.forminputkotlin.FormInputText;
import com.ib.custom.toast.CustomToastView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FormInputText txtId;
    private FormInputText txtNombre;
    private FormInputText txtPoblacion;
    private FormInputText txtLong;
    private FormInputText txtLat;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId= findViewById(R.id.txtId);
        txtNombre =findViewById(R.id.txtNombre);
        txtPoblacion= findViewById(R.id.txtPoblacion);
        txtLong= findViewById(R.id.txtLong);
        txtLat= findViewById(R.id.txtLat);
        btnAdd= findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);

        Idautomatico();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btnAdd) {
            int idCiudad = Integer.parseInt(txtId.getValue());
            String nombreCiudad = txtNombre.getValue();
            int poblacionCiudad = Integer.parseInt(txtPoblacion.getValue());
            double latitudCiudad = Double.parseDouble(txtLong.getValue());
            double longitudCiudad = Double.parseDouble(txtLat.getValue());


            ciudad(idCiudad,nombreCiudad,poblacionCiudad,latitudCiudad,longitudCiudad);

        }
    }

    public void ciudad(int idCiudad,String nombreCiudad, int poblacionCiudad,double latitudCiudad, double longitudCiudad){

        if(idCiudad==0){
            CustomToastView.makeInfoToast(this,"Error al validar el Id",R.layout.custom_toast).show();
            return;
        }
        if (nombreCiudad.equals("")) {
            CustomToastView.makeErrorToast(this, "Error al validar el nombre de la ciudad", R.layout.custom_toast).show();
            return;
        }
        if(poblacionCiudad==0){
            CustomToastView.makeInfoToast(this,"Error al validar la población",R.layout.custom_toast).show();
            return;
        }
        if(latitudCiudad==0){
            CustomToastView.makeInfoToast(this,"Error al validar la latitud",R.layout.custom_toast).show();
            return;
        }
        if(longitudCiudad==0){
            CustomToastView.makeInfoToast(this,"Error al validar la longitud",R.layout.custom_toast).show();
            return;
        }

        //instanciar clase
        Ciudad ciudad= new Ciudad();
        ciudad.setId(idCiudad);
        ciudad.setNombre(nombreCiudad);
        ciudad.setPoblacion(poblacionCiudad);
        ciudad.setLatitud(latitudCiudad);
        ciudad.setLongitud(longitudCiudad);

        //instanciar la base de datos
        MyDbHelper db=new MyDbHelper(this);
        db.InsertCiudad(db.getWritableDatabase(),ciudad);

        Idautomatico();
        txtId.setValue("");
        txtNombre.setValue("");
        txtLat.setValue("");
        txtPoblacion.setValue("");
        txtLong.setValue("");


        ArrayList<Ciudad> ciudades = db.seleccionCiudad(db.getWritableDatabase());
        int i = 1;
        for(Ciudad Seleccion : ciudades){
            System.out.println("Ciudad Id: " + Seleccion.getId() + " Nombre: " + Seleccion.getNombre() +
                    "Población: "+ Seleccion.getPoblacion() + "Latitud: " + Seleccion.getLatitud() +
                    "Longitud: "+ Seleccion.getLongitud());
        }

    }
    public void Idautomatico(){
        MyDbHelper db=new MyDbHelper(this);
        int idauto= db.SiguienteId(db.getWritableDatabase());
        txtId.setValue(String.valueOf(idauto));
    }
}