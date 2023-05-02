package com.leo_escobar.pruebafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.leo_escobar.pruebafinal.data.BodyLogin;
import com.leo_escobar.pruebafinal.data.LoginRespuesta;
import com.leo_escobar.pruebafinal.data.User;
import com.leo_escobar.pruebafinal.data.Usesrs;
import com.leo_escobar.pruebafinal.databinding.ActivityLoginBinding;
import com.leo_escobar.pruebafinal.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    String token;
    String correo_usuario;
    String password;
    private ServiceRetrofit api;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        api = RetrofitPrueba.getInstance().getService();


        //boton para iniciar sesion
        binding.btnLogin.setOnClickListener(v -> {
            //asignarle el valor de correo_usuario y password a las variables
            correo_usuario = binding.correoUsuario.getText().toString();
            password = binding.password.getText().toString();

            //llamar al metodo para obtener el token
            obtenerToken(correo_usuario, password);
        });

    }

    private void obtenerToken(String correo_usuario, String password) {



        Call<LoginRespuesta> call = api.login(new BodyLogin(correo_usuario, password));
        call.enqueue(new Callback<LoginRespuesta>() {
            @Override
            public void onResponse(Call<LoginRespuesta> call, Response<LoginRespuesta> response) {
                if(response.isSuccessful()) {
                    //toast de exito
                    Toast.makeText(Login.this, "Exito: " + response.body().getToken(), Toast.LENGTH_SHORT).show();

                    token = response.body().getToken();



                    SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", token);
                    editor.apply();

                    //toast de token
                    //Toast.makeText(Login.this, "Token: " + token, Toast.LENGTH_SHORT).show();

                    //iniciar la actividad MainActivity
                    //pasar el token en un putExtra a la actividad MainActivity
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginRespuesta> call, Throwable t) {
                //toast de error
                Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        Call<Usesrs> call2 = api.getUsuarios(token);
        call2.enqueue(new Callback<Usesrs>() {
            @Override
            public void onResponse(Call<Usesrs> call, Response<Usesrs> response) {
                if (response.isSuccessful()) {
                    Log.d("Respuesta", "onResponse: " + response.body());
                    Log.i("Respuesta", "Estoy en el Login desde getClientes");

                    Usesrs usesrs = response.body();
                    List<User> users = usesrs.getUsers();
                    for (User user : users) {
                        Log.d("Respuesta", "onResponse: " + user.getCorreo());
                    }
                } else {
                    Log.d("Respuesta", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Usesrs> call, Throwable t) {
                Log.d("Respuesta", "onFailure: " + t.getMessage());
            }
        });

    }
}