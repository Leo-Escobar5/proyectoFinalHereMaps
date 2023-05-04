package com.leo_escobar.pruebafinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.leo_escobar.pruebafinal.DAO.ClienteDao;
import com.leo_escobar.pruebafinal.DAO.ClienteDatabase;
import com.leo_escobar.pruebafinal.data.Client;
import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.data.Clients;
import com.leo_escobar.pruebafinal.databinding.ActivityEditarClienteBinding;
import com.leo_escobar.pruebafinal.ui.ViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editarCliente extends AppCompatActivity {

    private ActivityEditarClienteBinding binding;
    private ClienteDao clienteDao;
    private Cliente cliente;
    private Client client;
    private ServiceRetrofit api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditarClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api = RetrofitPrueba.getInstance().getService();


        // Crear una nueva instancia de ClienteDao
        ClienteDatabase clienteDatabase = ClienteDatabase.getDatabase(this);
        // Obtener una instancia del DAO a través de la base de datos
        clienteDao = clienteDatabase.clienteDao();

        // obtener el id del cliente que se quiere editar desde el intent
        int idCliente = getIntent().getIntExtra("idCliente", -1);

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
        binding.editTextEstado.setAdapter(adapter);

        //Toast con el id del cliente seleccionado
        //Toast.makeText(this, "ID del cliente seleccionado: " + idCliente, Toast.LENGTH_SHORT).show();
        //Toast con el nombre del cliente seleccionado
        //Toast.makeText(this, "Nombre del cliente seleccionado: " + nombreCliente, Toast.LENGTH_SHORT).show();

        //Toast con el token
        //Toast.makeText(this, "Token: " + token, Toast.LENGTH_SHORT).show();

        // ejecutar una tarea asíncrona para obtener el cliente correspondiente a través del DAO

        Call<Client> call4 = api.getCliente(token, idCliente);
        call4.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Client client = response.body();
                    if (client != null) {
                        //Obtener el primer objeto Client de la lista clients
                        // Mostrar los datos del cliente en el formulario
                        binding.editTextNombre.setText(client.getRazon_social());
                        binding.editTextTelefono.setText(client.getTelefono());
                        binding.editTextCorreo.setText(client.getCorreo());
                        //binding.editTextFechaCreacion.setText(client.getFecha_creacion());
                        //binding.editTextEstado.setText(String.valueOf(client.getEstado_id()));
//                        String estadoSeleccionado = (String) binding.editTextEstado.getSelectedItem();
//                        int estado = estados.get(estadoSeleccionado);
                        int estadoId = client.getEstado_id();
                        String estadoSeleccionado = ""; // variable para guardar el estado seleccionado en formato String
                        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
                            if (entry.getValue() == estadoId) {
                                estadoSeleccionado = entry.getKey();
                                break;
                            }
                        }
                        int posicionEstado = listaEstados.indexOf(estadoSeleccionado);
                        binding.editTextEstado.setSelection(posicionEstado);


                        binding.editTextMunicipio.setText(String.valueOf(client.getCiudad_id()));
                        binding.editTextCodigoPostal.setText(client.getCp());
                        binding.editTextCalle.setText(client.getCalle());
                        binding.editTextColonia.setText(client.getColonia());
                        binding.editTextReferencia.setText(client.getReferencia());
                        binding.editTextCoordenadaX.setText(String.valueOf(client.getLatitud()));
                        binding.editTextCoordenadaY.setText(String.valueOf(client.getLongitud()));
                    } else {
                        Log.d("TAG1", "onResponse: clients is null");
                    }
                } else {
                    Log.d("TAG1", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.d("TAG1", "onFailure: " + t.getMessage());
            }
        });


        // llamar el boton de btnEditar para editar el cliente
                binding.btnEditar.setOnClickListener(v -> {

                    //obtener los datos del formulario
                    String nombre = binding.editTextNombre.getText().toString();
                    //String apellido = binding.editTextApellidos.getText().toString();
                    String telefono = binding.editTextTelefono.getText().toString();
                    String email = binding.editTextCorreo.getText().toString();
                    String fechaNacimiento = sdf.format(fechaActual);
                    //int estado = Integer.parseInt(binding.editTextEstado.getText().toString());
                    String estadoSeleccionado = (String) binding.editTextEstado.getSelectedItem();
                    int estado = estados.get(estadoSeleccionado);
                    int municipio = Integer.parseInt(binding.editTextMunicipio.getText().toString());
//                    String estado = binding.editTextEstado.getText().toString();
//                    String municipio = binding.editTextMunicipio.getText().toString();
                    String codigoPostal = binding.editTextCodigoPostal.getText().toString();
                    String calle = binding.editTextCalle.getText().toString();
                    String colonia = binding.editTextColonia.getText().toString();
                    String referencia = binding.editTextReferencia.getText().toString();
                    double latitud = Double.parseDouble(binding.editTextCoordenadaX.getText().toString());
                    double longitud = Double.parseDouble(binding.editTextCoordenadaY.getText().toString());

                    //obtener instancia del ViewModel
                    ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);

                    //llamar al método de editar cliente del ViewModel
                    //myViewModel.actualizarCliente(cliente.getIdCliente(), nombre, apellido, telefono, email, fechaNacimiento, estado, municipio, codigoPostal, calle, colonia, referencia, latitud, longitud);
                    myViewModel.actualizarClienteCall7(idCliente,nombre, telefono, email, referencia, calle, colonia, codigoPostal, municipio, estado, latitud, longitud, fechaNacimiento);
                    // startActivity a MainActivity y finish a esta actividad
                    startActivity(new Intent(this, MainActivity.class));

                });
    }


}
