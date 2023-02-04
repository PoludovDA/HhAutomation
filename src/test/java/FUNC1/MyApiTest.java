package FUNC1;

import api.ApiRequests;
import api.Specifications;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class MyApiTest {
    private final static String BASE_URL = "https://reqres.in/";

    @Test
    public void unSuccessRegisterTest() {

        Map<String, Object> dataRegister = new HashMap<>();
        dataRegister.put("email", "sydney@fife");

        Map<String, Object> responseMap = ApiRequests.responseMap(
                "post",
                BASE_URL,
                "api/register",
                400,
                "",
                dataRegister
        );
    }

    @Test
    public void checkAvatars() {
        List<Map<String, Object>> responseMapList = ApiRequests.responseMapList(
                "get",
                BASE_URL,
                "api/users?page=2",
                200,
                "data"
        );
    }

    @Test
    public void deleteUserTest() {
        Map<String, Object> responseMap = ApiRequests.responseMap(
                "delete",
                BASE_URL,
                "api/users/2",
                204,
                ""
        );
        Assert.assertTrue((Boolean) responseMap.get("IsSuccess"));
    }

//    @Test
//    public void avatarTest() {
//        List<UserData> users = given()
//                .when()
//                .get("api/users?page=2")
//                .then().log().all()
//                .extract().body().jsonPath().getList("data", UserData.class);
//
//        List<UserData> userData = ApiRequests.responsePojoList(
//                "get",
//                BASE_URL,
//                "api/users?page=2",
//                200,
//                "data"
//        );
//    }
}
