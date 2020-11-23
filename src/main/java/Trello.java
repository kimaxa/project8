import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.testng.Assert;

public class Trello {

    // key 3b39b1ad0a0cc225980e65253a228d35
    // token 0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52
    // id 5fba648cd60d9550a772875b

    @Test
    public void information () {
        RestAssured.baseURI = "https://api.trello.com/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "1/members/me/boards?key=3b39b1ad0a0cc225980e65253a228d35&token=0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("Status code is " + statusCode);
        Assert.assertEquals(statusCode, 200);

        String statusLine = response.statusLine();
        System.out.println("Status line is " + statusLine);
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
    }
    // Сверху все работает ОК, отображает валидную информацию.
    // Ошибка: could not find the board that the card belongs to
    @Test
    public void createCardForApi ()  {
        RestAssured.baseURI = "https://api.trello.com/1/cards";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("key", "3b39b1ad0a0cc225980e65253a228d35");
        requestParams.put("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        requestParams.put("idList", "5fba648cd60d9550a772875b");
        requestParams.put("name", "CardForApi");
        httpRequest.header("CardForApi", "application/json");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"?key=3b39b1ad0a0cc225980e65253a228d35&0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52&idList=5fba648cd60d9550a772875b");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);


        }
        // Ошибка: could not find the board that the card belongs to
    @Test
    public void createCardForDelete ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("idList", "5fba648cd60d9550a772875b");
        httpRequest.queryParam("name", "CardForDelete");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/1/cards");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);

    }
    //Решил попробовать через UniRest, как написано в руководстве Trello. Ошибка: could not find the board that the card belongs to
    @Test
    public void createCardForApiUniRest() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://api.trello.com/1/cards")
                .queryString("key", "3b39b1ad0a0cc225980e65253a228d35")
                .queryString("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52")
                .queryString("idList","5fba648cd60d9550a772875b")
                .queryString("name", "CardForApi")
                .asString();
        System.out.println(response.getBody());

    }


    }

