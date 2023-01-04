package weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Weather {
    private final String KEY = "";
    private String uri;
    private String city;
    private String response = "";

    public void setCity(String city) {
        this.city = city;
    }


    public String getWeather() throws JSONException {

        try {
            if (!this.city.isBlank()) {
                uri = "https://api.openweathermap.org/data/2.5/weather?q=" + this.city + "&appid=" + KEY + "&units=metric";
            }
            URL url_weather = new URL(uri);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url_weather.openConnection();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader inputStreamReader =
                        new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader =
                        new BufferedReader(inputStreamReader, 8192);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }

                bufferedReader.close();
            } else {
                return "Не удалось найти город: " + this.city;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject jsonObject = new JSONObject(response);

        //"main"
        JSONObject JSONObject_main = jsonObject.getJSONObject("main");
        Double result_humidity = JSONObject_main.getDouble("humidity");
        Double result_feels_like = JSONObject_main.getDouble("feels_like");

        //"wind"
        JSONObject JSONObject_wind = jsonObject.getJSONObject("wind");
        Double result_speed = JSONObject_wind.getDouble("speed");

        //"name"
        String result_name = jsonObject.getString("name");

        return "Город: " + result_name + "\n" +
                "Температура: " + result_feels_like + "\n" +
                "Влажность: " + result_humidity + " %" + "\n" +
                "Ветер: " + result_speed + " м\\с" + "\n" +
                "\n" +
                "https://openweathermap.org/";
    }
}
