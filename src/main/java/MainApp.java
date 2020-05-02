import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainApp implements Runnable {

    private Scanner scanner;

    private void startApp() {
        scanner = new Scanner(System.in);
        System.out.println("Wybierz po czym chcesz znaleźć miejsce dla którego wyświetlisz pogodę \n0 - Zakończ działanie \n1 - Nazwa Miasta \n2 - Kod pocztowy \n3 - Wsppółrzędne geograficzne");
        Integer name = scanner.nextInt();
        chooseTypeSearching(name);
    }

    private void chooseTypeSearching(Integer typeNumber) {
        switch (typeNumber) {
            case 0:
                break;
            case 1:
                connectByCityName();
                startApp();
                break;
            case 2:
                connectByZipCode();
                startApp();
                break;
            case 3:
                connectByCoords();
                startApp();
                break;
        }
    }
    public void connectByCoords(){
        System.out.println("podaj szerokość geograficzną:");
        String N = scanner.next();
        System.out.println("podaj długość geograficzną:");
        String W = scanner.next();
        try{
            String response = new HttpService().connect(Config.APP_URL+"?lat=" +N+"&lon="+W+"&appid="+ Config.APP_ID+"&lang=pl");
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void connectByCityName() {
//        //TODO
//        System.out.println("Podaj nazwę miasta: ");
//        String cityName = scanner.next();
//        try {
//            String response = new HttpService().connect(Config.APP_URL + "?q=" + cityName + "&appid=" + Config.APP_ID + "&lang=pl");
//            parseJson(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void connectByCityName() {

        System.out.println("Podaj nazwę miasta: ");
        String cityName = scanner.next();
        String response = connectByCityName(cityName);
        parseJson(response);
    }
    public String connectByCityName(String cityName) {
        String response = null;
        try {
            response = new HttpService().connect(Config.APP_URL + "?q=" + cityName + "&appid=" + Config.APP_ID + "&lang=pl");

        } catch (IOException e) {

            e.printStackTrace();
        }
        return response;
    }

    private void connectByZipCode() {
        //TODO
        System.out.println("Podaj kod pocztowy miasta: ");
        String zipcode = scanner.next();
        try {
            String response = new HttpService().connect(Config.APP_URL + "?zip=" + zipcode + ",pl" + "&appid=" + Config.APP_ID);
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void parseJson(String json) {

        JSONObject rootObject = new JSONObject(json);

        if (rootObject.getInt("cod") == 200) {
            JSONObject mainObject = rootObject.getJSONObject("main");
            JSONObject sysObject = rootObject.getJSONObject("sys");
            JSONObject cloudsObject = rootObject.getJSONObject("clouds");
            JSONObject windObject = rootObject.getJSONObject("wind");

            JSONArray weatherArrayObject = rootObject.getJSONArray("weather");
            JSONObject one = (JSONObject) weatherArrayObject.get(0);
            System.out.println("Opis Pogody" + one.getString("description"));

            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println(rootObject.getString("name"));
            System.out.println("Temperatura: " + df.format(mainObject.getDouble("temp") - 273) + " \u00b0C");
            System.out.println("Temp.max: " + df.format(mainObject.getDouble("temp_max") - 273) + " \u00b0C");
            System.out.println("Temp.średnia: " + df.format((mainObject.getDouble("temp_max") - 273 + mainObject.getDouble("temp_min") - 273) / 2) + " \u00b0C");
            System.out.println("Zachmurzenie: " + cloudsObject.getInt("all") + "%");
            System.out.println("Wiatr" + windObject.getDouble("speed") + "km/h");
            System.out.println("Ciśnienie: " + mainObject.getInt("pressure") + " hPa");
            System.out.println("Wilgotność: " + mainObject.getInt("humidity") + " %");
        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        startApp();
    }
}
