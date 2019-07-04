package edu.uchicago.skoop.profinal2019;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// https://bitbucket.org/csgerber/currencies2e/src/master/

public class JsonParser {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static JSONObject fetchJson(String urlString) throws Exception {

        URL url = new URL(urlString);

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("User-Agent", USER_AGENT);

        int httpResponseCode = httpConnection.getResponseCode();

        if (httpResponseCode == 200){
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuffer stringBuffer = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
            bufferedReader.close();

            try {
                return new JSONObject(stringBuffer.toString());
            } catch (JSONException e) {
                return null;
            }

        } else {
            return null;
        }

    }
}
