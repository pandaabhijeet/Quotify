package com.example.quotify;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader;
import com.example.quotify.models.LoginUserModel;
import com.example.quotify.utilities.SigninApiUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private AppCompatButton loginBtn;
    private TextView signUpLink;
    private TrailingCircularDotsLoader loader;

    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseFields();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                if(TextUtils.isEmpty(email)){et_email.setError("Email id is required");}
                else if(TextUtils.isEmpty(password)){et_password.setError("Password id is required");}
                else
                {
                    //loader.setVisibility(View.VISIBLE);
                    loginBtn.setClickable(false);
                    loginUser();
                }
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

    }

    private void initialiseFields()
    {
        et_email = findViewById(R.id.user_email);
        et_password = findViewById(R.id.user_password);
        loginBtn = findViewById(R.id.login_btn);
        signUpLink = findViewById(R.id.signup_link_text);
        loader = findViewById(R.id.trailing_dots_loader);
    }


    private void loginUser()
    {
        LoginUserModel loginUserModel = new LoginUserModel(email,password);
        SigninApiUtility.getApiInstance()
                        .getApiInterface()
                        .loginUser(loginUserModel)
                        .enqueue(new Callback<LoginUserModel>() {
                            @Override
                            public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {
                                if(response.body() != null)
                                {
                                    Boolean loginSuccess = response.body().getSuccess();
                                    if (loginSuccess)
                                    {
                                        String loginUserEmail = response.body().getEmail();

                                        loginBtn.setClickable(true);
                                        Toast.makeText(LoginActivity.this,
                                                "Login in Successful for: "+ loginUserEmail, Toast.LENGTH_LONG).show();
                                    }else
                                    {
                                        String loginError = response.body().getError();

                                        loginBtn.setClickable(true);
                                        Toast.makeText(LoginActivity.this,loginError, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginUserModel> call, Throwable t) {
                                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
    }



}