package com.fiec.eciot.activities;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fiec.eciot.R;
import com.fiec.eciot.databinding.ActivityMainBinding;
import com.fiec.eciot.models.Token;
import com.fiec.eciot.models.User;
import com.fiec.eciot.services.ApiService;
import com.fiec.eciot.services.RetrofitClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mMainBinding;

    EditText usernameEdit,passwordEdit;
    private Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        usernameEdit = mMainBinding.txtUsuario;
        passwordEdit = mMainBinding.txtPassword;
        if (verifyToken()){
            Intent ingreso= new Intent(getApplicationContext(), DrawerMenuActivity.class);
            startActivity(ingreso);
        }

    }
    public void iniciarSesion(View v){
        final SweetAlertDialog progress = new SweetAlertDialog(MainActivity.this);
        progress.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        progress.setCancelable(false);
        progress.setTitle("Iniciando Sesión");
        progress.show();
            ApiService api = RetrofitClient.createApiService();
            api.getToken(usernameEdit.getText().toString(),passwordEdit.getText().toString()).enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()){
                        User user = new User(usernameEdit.getText().toString(),passwordEdit.getText().
                                toString());
                    /*
                     Si es necesario guardar usuarios
                     user.save();
                     */

                        Token token = response.body();
                        token.save();
                        progress.dismissWithAnimation();
                        Intent ingreso= new Intent(getApplicationContext(), DrawerMenuActivity.class);
                        startActivity(ingreso);

                    } else {
                        progress.dismissWithAnimation();
                        Toast toast = Toast.makeText(getApplicationContext(), "Usuario no registrado", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    t.getCause();
                    Toast toast = Toast.makeText(getApplicationContext(), "Error de Conexión", Toast.LENGTH_LONG);
                    toast.show();
                    progress.cancel();
                }
            });


    }



    private boolean verifyToken(){
        Realm realm = Realm.getDefaultInstance();
        Token token = realm.where(Token.class).findFirst();
        if (token != null){
            return true;
        }
        return false;

    }



}
