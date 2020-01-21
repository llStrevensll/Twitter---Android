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
import com.example.minitwitter.retrofit.request.RequestSignUp;
import com.example.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    //Botones
    Button btnSignUp;
    TextView tvGoLogin;

    //EditText
    EditText etUsername, etEmail, etPassword;

    //Retrofit
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Ocultar ActionBar
        getSupportActionBar().hide();

        //Asignar variables retrofit
        retrofitInit();

        //Asignar id
        findViews();

        //Eventos
        events();

    }
    //Asignar variables retrofit
    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();

        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }


    //Asignar id
    private void findViews() {
        btnSignUp = findViewById(R.id.buttonSignUp);
        tvGoLogin = findViewById(R.id.textViewGoLogin);
        etUsername = findViewById(R.id.editTextUsername);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
    }

    private void events() {
        btnSignUp.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Identificar por medio del id cual boton es
        int id = v.getId();

        switch (id) {
            case R.id.buttonSignUp:
                goToSignUp();
                break;
            case R.id.textViewGoLogin:
                goToLogin();
                break;
        }
    }

    private void goToSignUp() {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty()){
            etUsername.setError("El nombre de usuario es requerido");
        } else if (email.isEmpty()){
            etEmail.setError("El email es requerido");
        } else if (password.isEmpty() || password.length()<4){
            etPassword.setError("La contraseña es requerida y debe tener al menos 4 caracteres");
        } else {
            String code = "UDEMYANDROID";
            RequestSignUp requestSignUp = new RequestSignUp(username, email, password, code);
            Call<ResponseAuth> call = miniTwitterService.doSignUp(requestSignUp);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){
                        Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Algo salio mal, revisa los datos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Error en la Conexion, Intenta Nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
