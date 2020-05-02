import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Test;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.io.IOException;


//public class MainAppTest {
//
//    @Test
//    public void connectionTest() {
//        HttpService httpService = new HttpService();
//        JSONObject rootObject = null;
//        try {
//            String respone = httpService.connect(Config.APP_URL + "?q=" + "Warszawa" + "&appid=" + Config.APP_ID);
//            rootObject = new JSONObject(respone);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Assert.assertTrue(rootObject.getInt("cod") == 200);
//    }
//}


public class MainAppTest {

    @BeforeAll

    static void init() {

        System.out.println("Rozpoczęcie testów aplikacji");

    }

    @Test

    @DisplayName("Główny test połączenia")

    @Tag("dev")

    void connectionTest() {

        HttpService httpService = new HttpService();

        JSONObject rootObject = null;

        try {

            String respone = httpService.connect(Config.APP_URL + "?q=" + "Warszawa" + "&appid=" + Config.APP_ID);

            rootObject = new JSONObject(respone);

        } catch (IOException e) {

            e.printStackTrace();

        }

        Assertions.assertTrue(rootObject.getInt("cod") == 200);

    }

//    @Test
//
//    @DisplayName("Test pogody dla miasta Warszawa")
//
//    void connectByCityNameTest() {
//
//        MainApp mainApp = new MainApp();
//
//        String responseTest = mainApp.connectByCityName("Warsaw");
//
//        JSONObject jsonObject = new JSONObject(responseTest);
//
//        Assertions.assertEquals(200, jsonObject.getInt("cod"));
//
//        Assertions.assertEquals("Warsaw", jsonObject.getString("name"));
//
//    }

    @Test

    @Disabled("Nie została obsłużona")

    void parseJsonForXDaysTest() {

        //TODO

    }

    @AfterAll

    static void done() {

        System.out.println("Zakończenie testów");

    }

}