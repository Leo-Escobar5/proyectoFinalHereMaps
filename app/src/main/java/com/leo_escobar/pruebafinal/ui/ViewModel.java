package com.leo_escobar.pruebafinal.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leo_escobar.pruebafinal.DAO.ClienteDao;
import com.leo_escobar.pruebafinal.DAO.ClienteDatabase;
import com.leo_escobar.pruebafinal.data.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Cliente myCliente;
    private Context mContext;
    private MutableLiveData<List<Cliente>> listaClientes;
    private ClienteDao clienteDao;

    public ViewModel(@NonNull Application application) {
        super(application);
        ClienteDatabase database = ClienteDatabase.getDatabase(application);
        clienteDao = database.clienteDao();
        listaClientes = new MutableLiveData<>();
        listaClientes.setValue(new ArrayList<>());

        //llamar al metodo para mostrar los clientes de prueba
        mostrarClientesPrueba();

    }

    //metodo para mostrar los clientes de prueba
    public void mostrarClientesPrueba() {
        //mostrar la información del primer cliente en el listaClientes.getValue()

        new Thread(() -> {
            List<Cliente> clientes = clienteDao.obtenerClientes();
            listaClientes.postValue(clientes);
        }).start();

        //listaClientes.getValue().add(new Cliente(1, "Leo", "Escobar", "1234567890", "leo_fcleon@hotmail.com", "2021-05-01", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0));
        //listaClientes.getValue().add(new Cliente(2, "Leo", "Escobar", "1234567890", "pepe@hotmai.com", "2021-05-01", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0));
        //listaClientes.getValue().add(new Cliente(3, "Leo", "Escobar", "1234567890", "carlos@hotmail.com", "2021-05-01", "Jalisco", "Guadalajara", "45678", "Calle 1", "Colonia 1", "Referencia 1", 20.0, 20.0));
    }

    public void insertarCliente(Cliente cliente) {
        //Obtener instancia de la base de datos

    }

    public void actualizarCliente(int clienteId, String nombres, String apellidos, String telefono, String correo, String fechaCreacion, String estado, String municipio, String codigoPostal, String calle, String colonia, String referencia, double coordenadaX, double coordenadaY) {
        new Thread(() -> {
            clienteDao.editarCliente(clienteId, nombres, apellidos, telefono, correo, fechaCreacion, estado, municipio, codigoPostal, calle, colonia, referencia, coordenadaX, coordenadaY);
        }).start();
    }

    public void eliminarCliente(int clienteId) {
        new Thread(() -> {
            clienteDao.eliminarCliente(clienteId);
            List<Cliente> clientes = clienteDao.obtenerClientes();
            listaClientes.postValue(clientes);
        }).start();
    }


    public void crearClienteDePrueba(Context context, String nombre, String apellido, String telefono, String email, String fechaNacimiento, String estado, String ciudad, String codigoPostal, String calle, String colonia, String referencia, double latitud, double longitud) {
        new Thread(() -> {
            Cliente cliente = new Cliente(0, nombre, apellido, telefono, email, fechaNacimiento, estado, ciudad, codigoPostal, calle, colonia, referencia, latitud, longitud);
            ClienteDatabase db = ClienteDatabase.getDatabase(context);
            db.clienteDao().insertarCliente(cliente);
            //log con el id y nombre de todos los clientes
            for (Cliente cliente1 : db.clienteDao().obtenerClientes()) {
                Log.d("ViewModel", "Cliente: " + cliente1.getIdCliente() + " " + cliente1.getNombres());
            }
        }).start();
        //obtener la lista de clientes actualizada
        new Thread(() -> {
            List<Cliente> clientes = clienteDao.obtenerClientes();
            listaClientes.postValue(clientes);
        }).start();
        //notificar a los observadores(MainActivity) que la lista de clientes ha cambiado
        listaClientes.postValue(listaClientes.getValue());
    }






    public LiveData<List<Cliente>> obtenerClientes() {
        //log de prueba
        Log.i("ViewModel", "Esta es una prueba de log 3");


        //Obtener lista de clientes del modelo
        return listaClientes;
    }

    //metodo filtrar clientes


}
