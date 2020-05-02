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
        System.out.println("Wybierz po czym chcesz znaleźć miejsce dla którego wyświetlisz pogodę \n0 - Zakończ działanie \n1 - Nazwa Miasta \n2 - Kod pocztowy");
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
        }
    }

    private void connectByCityName() {
        //TODO

        System.out.println("Podaj nazwę miasta: ");
        String cityName = scanner.next();
        try {
            String response = new HttpService().connect(Config.APP_URL + "?q=" + cityName  + "&appid=" + Config.APP_ID);
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//
//
//    private void connectByCityName() {
//
//        System.out.println("Podaj nazwę miasta: ");
//
//        String cityName = scanner.next();
//
//        String response = connectByCityName(cityName);
//
//        parseJson(response);
//
//    }
//
//    public String connectByCityName(String cityName) {
//
//        String response = null;
//
//        try {
//
//            response = new HttpService().connect(Config.APP_URL + "?q=" + cityName + "&appid=" + Config.APP_ID);
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//        return response;
//
//    }




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
        //TODO
        double temp;
        int humidity;
        int pressure;
        int clouds;
        String country;

        JSONObject rootObject = new JSONObject(json);
        if (rootObject.getInt("cod") == 200) {
            JSONObject mainObject = rootObject.getJSONObject("main");
            JSONObject sysObject = rootObject.getJSONObject("sys");

country= sysObject.getString("country");

            System.out.println(sysObject.getInt("sunrise"));
            System.out.println(rootObject.getString("name"));

            DecimalFormat df = new DecimalFormat("#.##");
            temp = mainObject.getDouble("temp");
            temp = temp - 273;

            humidity = mainObject.getInt("humidity");
            pressure = mainObject.getInt("pressure");
            JSONObject cloudsObject = rootObject.getJSONObject("clouds");
            clouds = cloudsObject.getInt("all");

            System.out.println("Temperatura: " + df.format(temp) + " \u00b0C");
            System.out.println("Wilgotność: " + humidity + " %");
            System.out.println("Zachmurzenie: " + clouds + "%");
            System.out.println("Ciśnienie: " + pressure + " hPa");

        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        startApp();
    }
}
