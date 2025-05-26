package util;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

public class FacebookToken {
    public static String validar(String accessToken) {
        try {
            // Solicita los datos del usuario a Facebook
            URL url = new URL("https://graph.facebook.com/me?fields=email&access_token=" + accessToken);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            JSONObject obj = new JSONObject(content.toString());
            if (obj.has("email")) {
                return obj.getString("email");
            }
        } catch (Exception e) {
            // Error al validar token
        }
        return null;
    }
}

