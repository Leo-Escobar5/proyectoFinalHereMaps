package com.leo_escobar.pruebafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.GeoCoordinate;
import java.io.File;


import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.databinding.ActivityCrearClienteBinding;
import com.leo_escobar.pruebafinal.databinding.ActivityMainBinding;
import com.leo_escobar.pruebafinal.ui.ViewModel;

public class CrearCliente extends AppCompatActivity {
    private Map map = null;
    private AndroidXMapFragment mapFragment = null;


    private @NonNull ActivityCrearClienteBinding viewDataBinding;
    private ViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = ActivityCrearClienteBinding.inflate(getLayoutInflater());
        setContentView(viewDataBinding.getRoot());

        myViewModel = new ViewModelProvider(this).get(ViewModel.class);

        //llamar el boton btnGuardar para guardar el cliente
        viewDataBinding.btnGuardar.setOnClickListener(v -> {

            //obtener los datos del formulario
            String nombre = viewDataBinding.editTextNombre.getText().toString();
            String apellido = viewDataBinding.editTextApellidos.getText().toString();
            String telefono = viewDataBinding.editTextTelefono.getText().toString();
            String email = viewDataBinding.editTextCorreo.getText().toString();
            String fechaNacimiento = viewDataBinding.editTextFechaCreacion.getText().toString();
            String estado = viewDataBinding.editTextEstado.getText().toString();
            String municipio = viewDataBinding.editTextMunicipio.getText().toString();
            String codigoPostal = viewDataBinding.editTextCodigoPostal.getText().toString();
            String calle = viewDataBinding.editTextCalle.getText().toString();
            String colonia = viewDataBinding.editTextColonia.getText().toString();
            String referencia = viewDataBinding.editTextReferencia.getText().toString();
            double latitud = Double.parseDouble(viewDataBinding.editTextCoordenadaX.getText().toString());
            double longitud = Double.parseDouble(viewDataBinding.editTextCoordenadaY.getText().toString());

            //obtener instancia del ViewModel
            ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);

            //llamar al método insertarCliente del ViewModel
            myViewModel.crearClienteDePrueba(this,nombre, apellido, telefono, email, fechaNacimiento, estado, municipio, codigoPostal, calle, colonia, referencia, latitud, longitud);

            //configurar el resultado que se devolverá al activity principal
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);

            //startActivity a MainActivity
            startActivity(new Intent(this, MainActivity.class));

        });
//        initialize();
    }
//    private AndroidXMapFragment getMapFragment() {
//        return (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
//    }

//    private void initialize() {
//        setContentView(R.layout.activity_crear_cliente);
//// Search for the map fragment to finish setup by calling init().
//        mapFragment = getMapFragment();
//// Set up disk map cache path for this application
//// Use path under your application folder for storing the disk cache
//        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(
//                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");
//        mapFragment.init(new OnEngineInitListener() {
//            @Override
//            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
//                if (error == OnEngineInitListener.Error.NONE) {
//// retrieve a reference of the map from the map fragment
//                    map = mapFragment.getMap();
//// Set the map center to the Vancouver region (no animation)
//                    map.setCenter(new GeoCoordinate(49.196261, -123.004773, 0.0),
//                            Map.Animation.NONE);
//// Set the zoom level to the average between min and max
//                    map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
//                } else {
//                    System.out.println("ERROR: Cannot initialize Map Fragment");
//                    //log de error en caso de que no se pueda inicializar el mapa
//                    Log.e("ERROR: ", "Cannot initialize Map Fragment");
//                }
//            }
//        });
//    }
}