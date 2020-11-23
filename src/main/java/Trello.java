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

    // key     3b39b1ad0a0cc225980e65253a228d35
    // token   0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52
    // idBoard 5fba648cd60d9550a772875b htapikim
    // idList1 5fbc2855a67b4a603deff54f FirstList
    // idList2 5fbc31d82b29677b7cde66b8 ApiList
    // idCard  5fbc2bc750e838780f5c6f30 CardForDelete

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

    @Test
    public void createFirstList ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("idBoard", "5fba648cd60d9550a772875b");
        httpRequest.queryParam("name", "FirstList");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/1/lists");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);
        }
    @Test
    public void createCardForApi ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("idList", "5fbc2855a67b4a603deff54f");
        httpRequest.queryParam("name", "CardForApi");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/1/cards");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);
        }

    @Test
    public void createCardForDelete ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("idList", "5fbc2855a67b4a603deff54f");
        httpRequest.queryParam("name", "CardForDelete");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/1/cards");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);

    }
    //Решил попробовать через UniRest, как прописано в руководстве Trello.
    @Test
    public void createCardForApiUniRest() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://api.trello.com/1/cards")
                .queryString("key", "3b39b1ad0a0cc225980e65253a228d35")
                .queryString("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52")
                .queryString("idList","5fbc2855a67b4a603deff54f")
                .queryString("name", "CardForApi")
                .asString();
        System.out.println(response.getBody());

    }
    @Test
    public void editCardForApi ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("desc", "Описание карточки через rest-Api");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.PUT,"/1/cards/5fbc2bbd1a7d3c4db79e06cc");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);

    }
    @Test
    public void createApiList ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("idBoard", "5fba648cd60d9550a772875b");
        httpRequest.queryParam("name", "ApiList");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/1/lists/");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);
    }

    @Test
    // move CardForApi from FirstList to ApiList
    public void moveToApiList ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.queryParam("idBoard", "5fba648cd60d9550a772875b");
        httpRequest.queryParam("idList", "5fbc31d82b29677b7cde66b8");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.POST,"/1/lists/5fbc2855a67b4a603deff54f/moveAllCards");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);
    }
    @Test
    public void deleteCardForDelete ()  {
        RestAssured.baseURI = "https://api.trello.com";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        httpRequest.queryParam("key", "3b39b1ad0a0cc225980e65253a228d35");
        httpRequest.queryParam("token", "0d0f0cbebb3e927766533634decaa9331c6acdf563d018f1831d77146890db52");
        httpRequest.body(requestParams.toJSONString());

        Response response = httpRequest.request(Method.DELETE,"/1/cards/5fbc2bc750e838780f5c6f30");

        String responseBody = response.getBody().asString();
        System.out.println("Response body is: " + responseBody);
    }

}

