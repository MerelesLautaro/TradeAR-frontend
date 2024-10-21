package com.lautadev.tradear;

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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1001;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

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

        // Inicializa el cliente de Google Identity
        oneTapClient = Identity.getSignInClient(this);

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
                System.out.println("El GivenName es: "+credential.getGivenName());
                System.out.println("el FamilyName es: "+credential.getFamilyName());
                System.out.println("El email es: "+credential.getId());
                String idToken = credential.getGoogleIdToken();
                if (idToken != null) {
                    Log.d(TAG, "Google ID Token: " + idToken);

                    // Aquí puedes enviar el ID token al servidor para validación
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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

    // Método para cerrar sesión
    public void signOut() {
        oneTapClient.signOut().addOnCompleteListener(task -> {
            // Cerrar sesión fue exitoso
            Log.d(TAG, "Sign-out successful");
        });
    }
}

