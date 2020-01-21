package com.example.minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitwitter.R;
import com.example.minitwitter.retrofit.MiniTwitterClient;
import com.example.minitwitter.retrofit.MiniTwitterService;
import com.example.minitwitter.retrofit.request.RequestLogin;
import com.example.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Botones
    Button btnLogin;
    TextView tvGoSignUp;

    //EditText
    EditText etEmail, etPassword;

    //Retrofit
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ocultar ActionBar
        getSupportActionBar().hide();
        
        //Asignar variables retrofit
        retrofitInit();
        
        //Asignar respectivo id
        findViews();
        
        //Eventos
        events();

    }

    //Asignar variables retrofit
    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();

        miniTwitterService = miniTwitterClient.getMiniTwitterService();//Devuelve instancia de nuestro servicio api
    }


    //Buscar y asignar los id de la interfaz
    private void findViews() {
        btnLogin = findViewById(R.id.buttonLogin);
        tvGoSignUp = findViewById(R.id.textViewGoSignUp);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
    }

    //Eventos
    private void events() {
        btnLogin.setOnClickListener(this);
        tvGoSignUp.setOnClickListener(this);
    }

    //OnClick
    @Override
    public void onClick(View v) {
        //Identificar por medio del id cual boton es
        int id = v.getId();

        switch (id) {
            case R.id.buttonLogin:
                gotoLogin();
                break;
            case R.id.textViewGoSignUp:
                goToSignUp();
                break;
        }
    }

    private void gotoLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty()){
            etEmail.setError("El email es requerido");
        } else if (password.isEmpty()){
            etPassword.setError("La contraseña es requerida");
        } else {
            //Realizar Login
            //requestLogin-> objeto de la petición
            RequestLogin requestLogin = new RequestLogin(email, password);

            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);
            //enqueue->Peticion Asincrona
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){//Si es correcto code >= 200 && code < 300;
                        Toast.makeText(MainActivity.this, "Sesion iniciada correctamente", Toast.LENGTH_SHORT).show();

                        //Redirigir al dasboard
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);

                        //Destruir el actual Activity para que el usuario no pueda regresar al loguin
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Algo salio mal, revisa tus datos de acceso", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problemas de Conexión. Intenta Nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Ir al registro
    private void goToSignUp() {
        //Intent
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);

        //Redirigir al activity SignUpActivity
        startActivity(intent);
        finish();

    }
}
