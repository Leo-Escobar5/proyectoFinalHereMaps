package com.leo_escobar.pruebafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.GeoCoordinate;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


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

        // obtener el token desde el MainActivity
        //validación de que el token no sea nulo o vacío
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        Date fechaActual = new Date();

        HashMap<String, Integer> estados = new HashMap<>();
        estados.put("AGUASCALIENTES", 1);
        estados.put("BAJA CALIFORNIA", 2);
        estados.put("BAJA CALIFORNIA SUR", 3);
        estados.put("CAMPECHE", 4);
        estados.put("COAHUILA DE ZARAGOZA", 5);
        estados.put("COLIMA", 6);
        estados.put("CHIAPAS", 7);
        estados.put("CHIHUAHUA", 8);
        estados.put("CIUDAD DE MÉXICO", 9);
        estados.put("DURANGO", 10);
        estados.put("GUANAJUATO", 11);
        estados.put("GUERRERO", 12);
        estados.put("HIDALGO", 13);
        estados.put("JALISCO", 14);
        estados.put("ESTADO DE MÉXICO", 15);
        estados.put("MICHOACÁN DE OCAMPO", 16);
        estados.put("MORELOS", 17);
        estados.put("NAYARIT", 18);
        estados.put("NUEVO LEÓN", 19);
        estados.put("OAXACA DE JUÁREZ", 20);
        estados.put("PUEBLA", 21);
        estados.put("QUERÉTARO", 22);
        estados.put("QUINTANA ROO", 23);
        estados.put("SAN LUIS POTOSÍ", 24);
        estados.put("SINALOA", 25);
        estados.put("SONORA", 26);
        estados.put("TABASCO", 27);
        estados.put("TAMAULIPAS", 28);
        estados.put("TLAXCALA", 29);
        estados.put("VERACRUZ DE IGNACIO DE LA LLAVE", 30);
        estados.put("YUCATÁN", 31);
        estados.put("ZACATECAS", 32);

        // Crear un ArrayList con los nombres de los estados
        ArrayList<String> listaEstados = new ArrayList<>(Arrays.asList(
                "AGUASCALIENTES", "BAJA CALIFORNIA", "BAJA CALIFORNIA SUR", "CAMPECHE",
                "COAHUILA DE ZARAGOZA", "COLIMA", "CHIAPAS", "CHIHUAHUA", "CIUDAD DE MÉXICO",
                "DURANGO", "GUANAJUATO", "GUERRERO", "HIDALGO", "JALISCO", "ESTADO DE MÉXICO",
                "MICHOACÁN DE OCAMPO", "MORELOS", "NAYARIT", "NUEVO LEÓN", "OAXACA DE JUÁREZ",
                "PUEBLA", "QUERÉTARO", "QUINTANA ROO", "SAN LUIS POTOSÍ", "SINALOA", "SONORA",
                "TABASCO", "TAMAULIPAS", "TLAXCALA", "VERACRUZ DE IGNACIO DE LA LLAVE", "YUCATÁN",
                "ZACATECAS"));

// Crear un ArrayAdapter con la lista de estados
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEstados);

// Establecer el layout a usar cuando se despliega la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Establecer el adaptador para el Spinner
        viewDataBinding.editTextEstado.setAdapter(adapter);


        //llamar el boton btnGuardar para guardar el cliente
        viewDataBinding.btnGuardar.setOnClickListener(v -> {

            //obtener los datos del formulario
            String nombre = viewDataBinding.editTextNombre.getText().toString();
            String telefono = viewDataBinding.editTextTelefono.getText().toString();
            String email = viewDataBinding.editTextCorreo.getText().toString();
            String fechaNacimiento = sdf.format(fechaActual);
            String estadoSeleccionado = (String) viewDataBinding.editTextEstado.getSelectedItem();
            int estado = estados.get(estadoSeleccionado);

            int municipio = Integer.parseInt(viewDataBinding.editTextMunicipio.getText().toString());
//            String estado = viewDataBinding.editTextEstado.getText().toString();
//            String municipio = viewDataBinding.editTextMunicipio.getText().toString();
            String codigoPostal = viewDataBinding.editTextCodigoPostal.getText().toString();
            String calle = viewDataBinding.editTextCalle.getText().toString();
            String colonia = viewDataBinding.editTextColonia.getText().toString();
            String referencia = viewDataBinding.editTextReferencia.getText().toString();
            double latitud = Double.parseDouble(viewDataBinding.editTextCoordenadaX.getText().toString());
            double longitud = Double.parseDouble(viewDataBinding.editTextCoordenadaY.getText().toString());

            // Validación de número de teléfono y código postal
            if (telefono.length() != 10) {
                Toast.makeText(this, "El número de teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (codigoPostal.length() != 5) {
                Toast.makeText(this, "El código postal debe tener 5 dígitos", Toast.LENGTH_SHORT).show();
                return;
            }

            //obtener instancia del ViewModel
            ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);

            //llamar al método insertarCliente del ViewModel
//            myViewModel.crearClienteDePrueba(this,nombre, apellido, telefono, email, fechaNacimiento, estado, municipio, codigoPostal, calle, colonia, referencia, latitud, longitud);

            //log en un json los datos del cliente
            Log.i("Cliente: ", "Nombre: " + nombre  + " Telefono: " + telefono + " Email: " + email + " Fecha Nacimiento: " + fechaNacimiento + " Estado: " + estado + " Municipio: " + municipio + " Codigo Postal: " + codigoPostal + " Calle: " + calle + " Colonia: " + colonia + " Referencia: " + referencia + " Latitud: " + latitud + " Longitud: " + longitud);

            //llamar el método de crearClienteCall5 del ViewModel
            myViewModel.crearClienteCall5(this, nombre, telefono, email, referencia, calle, colonia, codigoPostal, municipio, estado, latitud, longitud, fechaNacimiento, 1);

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