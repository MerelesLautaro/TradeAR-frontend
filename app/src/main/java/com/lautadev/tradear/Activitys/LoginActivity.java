package com.lautadev.tradear.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.lautadev.tradear.R;
import com.lautadev.tradear.model.GoogleUserInfo;
import com.lautadev.tradear.network.RetrofitClient;
import com.lautadev.tradear.repository.AuthenticationAPIClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1001;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private AuthenticationAPIClient authenticationAPIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configuración de One Tap Sign-In request
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.google_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        oneTapClient = Identity.getSignInClient(this);

        authenticationAPIClient = RetrofitClient.getClient().create(AuthenticationAPIClient.class);

        // Llamar al método para iniciar el proceso de sign-in
        signInWithGoogle();
    }

    // Método para iniciar sesión con Google usando Google Identity Services
    private void signInWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        startIntentSenderForResult(result.getPendingIntent().getIntentSender(), RC_SIGN_IN,
                                null, 0, 0, 0, null);
                    } catch (Exception e) {
                        Log.e(TAG, "Error en el proceso de inicio de sesión", e);
                    }
                })
                .addOnFailureListener(this, e -> Log.e(TAG, "Error al iniciar el proceso de inicio de sesión", e));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);

                // Obtener ID token
                String idToken = credential.getGoogleIdToken();

                if (idToken != null) {
                    Log.d(TAG, "Google ID Token: " + idToken);

                    // Llamar al endpoint /auth/login-google
                    loginWithGoogle(credential);

                } else {
                    Log.e(TAG, "ID token es null");
                }
            } catch (ApiException e) {
                Log.e(TAG, "Error al obtener las credenciales", e);
            }
        } else {
            Log.e(TAG, "El resultado de inicio de sesión no fue exitoso o cancelado.");
        }
    }

    private void loginWithGoogle(SignInCredential credential) {
        // Crear el objeto GoogleUserInfo y enviar al servidor
        GoogleUserInfo googleUserInfo = new GoogleUserInfo();
        googleUserInfo.setId(credential.getGoogleIdToken());
        googleUserInfo.setName(credential.getGivenName());
        googleUserInfo.setLastname(credential.getFamilyName());
        googleUserInfo.setEmail(credential.getId());

        Call<String> call = authenticationAPIClient.loginWithGoogle(googleUserInfo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("La respuesta es: "+response);
                // uso del JWT creado omitido momentaneamente por cuestiones de practicidad
                if (response.code() == 200 || response.code() == 401){
                    Log.d(TAG, "Usuario autenticado exitosamente con Google");

                    // Navegar a la HomeActivity después del login exitoso
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("EMAIL", credential.getId());
                    intent.putExtra("NAME",credential.getGivenName());
                    intent.putExtra("LASTNAME",credential.getFamilyName());
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, "Falló la autenticación: " + response.message());
                    Log.e(TAG, "Código de respuesta: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error del servidor: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e(TAG, "Error al leer el cuerpo de error", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Error en la llamada al servidor", t);
            }
        });
    }

    public void signOut() {
        oneTapClient.signOut().addOnCompleteListener(task -> {
            Log.d(TAG, "Sign-out successful");
        });
    }
}
