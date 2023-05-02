package com.leo_escobar.pruebafinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.leo_escobar.pruebafinal.DAO.ClienteDao;
import com.leo_escobar.pruebafinal.DAO.ClienteDatabase;
import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.databinding.ActivityEditarClienteBinding;
import com.leo_escobar.pruebafinal.ui.ViewModel;

public class editarCliente extends AppCompatActivity {

    private ActivityEditarClienteBinding binding;
    private ClienteDao clienteDao;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditarClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Crear una nueva instancia de ClienteDao
        ClienteDatabase clienteDatabase = ClienteDatabase.getDatabase(this);
        // Obtener una instancia del DAO a través de la base de datos
        clienteDao = clienteDatabase.clienteDao();

        // obtener el id del cliente que se quiere editar desde el intent
        int idCliente = getIntent().getIntExtra("idCliente", -1);

        // ejecutar una tarea asíncrona para obtener el cliente correspondiente a través del DAO
        new AsyncTask<Integer, Void, Cliente>() {

            @Override
            protected Cliente doInBackground(Integer... integers) {
                return clienteDao.obtenerClientePorId(integers[0]);
            }

            @Override
            protected void onPostExecute(Cliente result) {
                cliente = result;
                // cargar los datos del cliente en los EditText de la vista
                binding.editTextNombre.setText(cliente.getNombres());
                binding.editTextApellidos.setText(cliente.getApellidos());
                binding.editTextTelefono.setText(cliente.getTelefono());
                binding.editTextCorreo.setText(cliente.getCorreo());
                binding.editTextFechaCreacion.setText(cliente.getFechaCreacion());
                binding.editTextEstado.setText(cliente.getEstado());
                binding.editTextMunicipio.setText(cliente.getMunicipio());
                binding.editTextCodigoPostal.setText(cliente.getCodigoPostal());
                binding.editTextCalle.setText(cliente.getCalle());
                binding.editTextColonia.setText(cliente.getColonia());
                binding.editTextReferencia.setText(cliente.getReferencia());
                binding.editTextCoordenadaX.setText(String.valueOf(cliente.getCoordenadaX()));
                binding.editTextCoordenadaY.setText(String.valueOf(cliente.getCoordenadaY()));
            }
        }.execute(idCliente);


        // llamar el boton de btnEditar para editar el cliente
        binding.btnEditar.setOnClickListener(v -> {

            //obtener los datos del formulario
            String nombre = binding.editTextNombre.getText().toString();
            String apellido = binding.editTextApellidos.getText().toString();
            String telefono = binding.editTextTelefono.getText().toString();
            String email = binding.editTextCorreo.getText().toString();
            String fechaNacimiento = binding.editTextFechaCreacion.getText().toString();
            String estado = binding.editTextEstado.getText().toString();
            String municipio = binding.editTextMunicipio.getText().toString();
            String codigoPostal = binding.editTextCodigoPostal.getText().toString();
            String calle = binding.editTextCalle.getText().toString();
            String colonia = binding.editTextColonia.getText().toString();
            String referencia = binding.editTextReferencia.getText().toString();
            double latitud = Double.parseDouble(binding.editTextCoordenadaX.getText().toString());
            double longitud = Double.parseDouble(binding.editTextCoordenadaY.getText().toString());

            //obtener instancia del ViewModel
            ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);

            //llamar al método de editar cliente del ViewModel
            myViewModel.actualizarCliente(cliente.getIdCliente(), nombre, apellido, telefono, email, fechaNacimiento, estado, municipio, codigoPostal, calle, colonia, referencia, latitud, longitud);

            // startActivity a MainActivity y finish a esta actividad
            finish();
            startActivity(new Intent(this, MainActivity.class));

        });
    }


}
