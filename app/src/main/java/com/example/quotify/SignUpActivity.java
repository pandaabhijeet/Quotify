package com.example.quotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader;
import com.example.quotify.models.SignUpUserModel;
import com.example.quotify.utilities.SigninApiUtility;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_firstName,et_lastName,et_emailId,et_password;
    private AppCompatButton signUpButton;
    private TextView tv_loginLink;
    private TrailingCircularDotsLoader loader;

    private String firstName,lastName,email,password,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeFields();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 firstName = et_firstName.getText().toString();
                 lastName = et_lastName.getText().toString();
                 username = firstName+" "+lastName;
                 email = et_emailId.getText().toString();
                 password = et_password.getText().toString();

                if(TextUtils.isEmpty(firstName)){et_firstName.setError("Enter a valid firstname");}
                else if(TextUtils.isEmpty(lastName)){et_lastName.setError("Enter a valid last name");}
                else if(TextUtils.isEmpty(email)){et_firstName.setError("Email cannot be empty");}
                else if(TextUtils.isEmpty(password)){et_password.setError("Password cannot be empty");}
                else
                {
                    loader.setVisibility(View.VISIBLE);
                    signUpButton.setClickable(false);
                    signUpUser();
                }


            }
        });

        tv_loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLoginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(startLoginIntent);
            }
        });
    }

    public void initializeFields()
    {
        et_firstName = findViewById(R.id.first_name);
        et_lastName = findViewById(R.id.last_name);
        et_emailId = findViewById(R.id.user_email);
        et_password = findViewById(R.id.user_password);

        signUpButton = findViewById(R.id.signup_btn);
        tv_loginLink = findViewById(R.id.login_link_text);
        loader = findViewById(R.id.trailing_dots_loader);
    }

    private void signUpUser()
    {
        SignUpUserModel signUpUserModel = new SignUpUserModel(username,email,password);
        SigninApiUtility
                .getApiInstance()
                .getApiInterface()
                .register(signUpUserModel)
                .enqueue(new Callback<SignUpUserModel>() {
                    @Override
                    public void onResponse(Call<SignUpUserModel> call, Response<SignUpUserModel> response) {

                        if(response.body() != null)
                        {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    loader.setVisibility(View.GONE);

                                    Boolean saveSuccess = response.body().getSuccess();

                                    if (saveSuccess)
                                    {
                                        String savedUserName = response.body().getUsername();
                                        String savedUserId = response.body().getId();
                                        String savedUserEmail = response.body().getEmail();
                                        String savedUserPassword = response.body().getPassword();

                                        signUpButton.setClickable(true);
                                        Toast.makeText(SignUpActivity.this,
                                                "Sign in Successful for: "+savedUserName, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));

                                    }else
                                    {
                                        String saveError = response.body().getError();

                                        signUpButton.setClickable(true);
                                        Toast.makeText(SignUpActivity.this,saveError, Toast.LENGTH_LONG).show();
                                    }
                                }
                            },2000);


                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpUserModel> call, Throwable t)
                    {
                        loader.setVisibility(View.GONE);
                        signUpButton.setClickable(true);
                        Toast.makeText(SignUpActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }
}