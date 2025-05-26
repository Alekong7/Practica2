package util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleToken {
    private static final String CLIENT_ID = "756620183621-hpqqr5kvklp3f990bqiqmhu49b7f5abp.apps.googleusercontent.com";

    public static GoogleIdToken.Payload validar(String idTokenString) {
        try {
            System.out.println("Validando token: " + idTokenString);
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                System.out.println("Token válido. Email: " + idToken.getPayload().getEmail());
                return idToken.getPayload();
            } else {
                System.out.println("Token inválido.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String googleToken = "your_google_token_here";
        GoogleIdToken.Payload payload = validar(googleToken);

        System.out.println("Token Google recibido: " + googleToken);
        if (payload == null) {
            System.out.println("Token de Google inválido o expirado.");
        }
        System.out.println("Payload: " + payload);
    }
}