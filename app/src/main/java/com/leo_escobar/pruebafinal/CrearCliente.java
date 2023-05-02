package com.leo_escobar.pruebafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.databinding.ActivityCrearClienteBinding;
import com.leo_escobar.pruebafinal.databinding.ActivityMainBinding;
import com.leo_escobar.pruebafinal.ui.ViewModel;

public class CrearCliente extends AppCompatActivity {

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
    }
}