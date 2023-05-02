package com.leo_escobar.pruebafinal;

import static com.leo_escobar.pruebafinal.BR.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.leo_escobar.pruebafinal.data.Cliente;
import com.leo_escobar.pruebafinal.databinding.ActivityMainBinding;
import com.leo_escobar.pruebafinal.ui.ClienteAdapter;
import com.leo_escobar.pruebafinal.ui.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ClienteAdapter adapter;
    private ActivityMainBinding viewDataBinding;
    private ViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewDataBinding.getRoot());

        myViewModel = new ViewModelProvider(this).get(ViewModel.class);

        //instanciar el adapter
        adapter = new ClienteAdapter(new ArrayList<>(), this);

        //asignar el adapter al recycler view
        viewDataBinding.eqRecycler.setAdapter(adapter);
        viewDataBinding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));


        //manejar  el click en un item del recycler view
        adapter.setOnItemClickListener(new ClienteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cliente cliente) {
                String mensaje = "ID del cliente seleccionado: " + cliente.getIdCliente();
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();

                //Guardar el Id del cliente seleccionado en una variable en MainActivity
                int idClienteSeleccionado = cliente.getIdCliente();

                //iniciar la actividad EditarCliente y pasar el id del cliente seleccionado
                Intent intent = new Intent(MainActivity.this, editarCliente.class);
                intent.putExtra("idCliente", idClienteSeleccionado);
                startActivity(intent);
            }
        });




                //obtener los datos del ViewModel y pasarlos al adapter
                myViewModel.obtenerClientes().observe(this, clientes -> {
                    Log.d("MainActivity", "Lista actualizada: " + clientes.size());
                    adapter.setItems(clientes);
                });


        //llamar el boton para ir al activity de crear cliente
        viewDataBinding.fabAgregarCliente.setOnClickListener(v -> {

            //crear un nuevo cliente
            //Cliente clienteNuevo = new Cliente(4, "Juan", "Pérez", "1234567890", "juanperez@gmail.com", "2021-05-01", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0);

//obtener instancia del ViewModel
            //ViewModel myViewModel = new ViewModelProvider(this).get(ViewModel.class);



//llamar al método insertarClientePrueba del ViewModel
            //myViewModel.insertarClientePrueba();

            //myViewModel.crearClienteDePrueba(this,  "Juan", "Pérez", "1234567890", "pepe@hotmail.com", "2023-04-28", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0);


            startActivity(new Intent(MainActivity.this, CrearCliente.class));


    });

        adapter.setOnDeleteClickListener(new ClienteAdapter.OnDeleteClickListener(){
            @Override
            public void onDeleteClick(Cliente cliente, int position) {
                myViewModel.eliminarCliente(cliente.getIdCliente());
            }
        });

        viewDataBinding.searchView.clearFocus();

        viewDataBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.getFilter().filter("");
                    //log de que no hay texto
                    Log.d("MainActivity", "No hay texto");


                } else {
                    adapter.getFilter().filter(newText);
                }

                return false;
            }
        });


        //validación de que el token no sea nulo o vacío
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        String token = sharedPref.getString("token", "");


        Toast.makeText(this,"Token_preferences"+ token, Toast.LENGTH_SHORT).show();

        //obtener el token del putExtra del login
        Intent intent = getIntent();
        String tokenRecibido = intent.getStringExtra("token");
        Toast.makeText(this, tokenRecibido, Toast.LENGTH_SHORT).show();

    }

    private void filterList(String text) {
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //llamar al método obtenerClientes del ViewModel para actualizar la lista de clientes
            myViewModel.obtenerClientes().observe(this, clientes -> {
                adapter.setItems(clientes);
            });
        }
    }
}



