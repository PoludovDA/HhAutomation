package api;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import org.junit.Assert;

import java.util.*;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ApiRequests {

    private static final Logger log = Logger.getLogger(ApiRequests.class.getName());

    @SafeVarargs
    public static JsonPath responseJson(String typeRequest, String baseUrl, String addUrl,
                                        int expectedStatusCode, String parameterJsonPath,
                                        Map<String, Object>... sentBody) throws EmptyStackException {
        Specifications.installSpecifications(Specifications.requestSpecification(baseUrl),
                Specifications.responseSpecificationUnique(expectedStatusCode));

        switch (typeRequest) {

            case "get":
                return given()
                        .when()
                        .get(addUrl)
                        .then().log().all()
                        .body(parameterJsonPath, notNullValue())
                        .extract().jsonPath();

            case "post":
                return given()
                        .body(sentBody)
                        .when()
                        .post(addUrl)
                        .then().log().all()
                        .body(parameterJsonPath, notNullValue())
                        .extract().jsonPath();

            case "delete":
                given()
                        .when()
                        .delete(addUrl)
                        .then();
                log.info("Метод delete успешно выполнился");
                throw new EmptyStackException();

            case "put":
                return given()
                        .body(sentBody)
                        .when()
                        .put(addUrl)
                        .then().log().all()
                        .body(parameterJsonPath, notNullValue())
                        .extract().jsonPath();

            default:
                Assert.fail("Не указан тип запроса");
        }

        throw new NullPointerException("Неверно указан тип метода");
    }

    @SafeVarargs
    public static Map<String, Object> responseMap(String typeRequest, String baseUrl, String addUrl,
                                                  int expectedStatusCode, String parameterJsonPath,
                                                  Map<String, Object>... sentBody) {
        Map<String, Object> response = new HashMap<>();

        try {
            response = responseJson(typeRequest, baseUrl, addUrl, expectedStatusCode, parameterJsonPath, sentBody)
                    .getMap(parameterJsonPath);
        }
        catch (EmptyStackException e) {
            response.put("IsSuccess", true);
            return response;
        }
        return response;
    }

    @SafeVarargs
    public static List<Map<String, Object>> responseMapList(String typeRequest, String baseUrl, String addUrl,
                                                            int expectedStatusCode, String parameterJsonPath,
                                                            Map<String, Object>... sentBody) {
        return Objects.requireNonNull(responseJson(typeRequest, baseUrl, addUrl, expectedStatusCode, parameterJsonPath, sentBody))
                .getList(parameterJsonPath);
    }


    @SafeVarargs
    public static ExtractableResponse responseExtract(String typeRequest, String baseUrl, String addUrl,
                                                   int expectedStatusCode, String parameterJsonPath,
                                                   Object... sentBody) throws EmptyStackException {
        Specifications.installSpecifications(Specifications.requestSpecification(baseUrl),
                Specifications.responseSpecificationUnique(expectedStatusCode));

        switch (typeRequest) {

            case "get":
                return given()
                        .when()
                        .get(addUrl)
                        .then().log().all()
                        .body(parameterJsonPath, notNullValue())
                        .extract();

            case "post":
                return given()
                        .body(sentBody)
                        .when()
                        .post(addUrl)
                        .then().log().all()
                        .body(parameterJsonPath, notNullValue())
                        .extract();

            case "delete":
                given()
                        .when()
                        .delete(addUrl)
                        .then();
                log.info("Метод delete успешно выполнился");
                throw new EmptyStackException();

            case "put":
                return given()
                        .body(sentBody)
                        .when()
                        .put(addUrl)
                        .then().log().all()
                        .body(parameterJsonPath, notNullValue())
                        .extract();

            default:
                Assert.fail("Не указан тип запроса");
        }

        throw new NullPointerException("Неверно указан тип метода");
    }

    @SafeVarargs
    public static Object responsePojo(String typeRequest, String baseUrl, String addUrl,
                                                  int expectedStatusCode, String parameterJsonPath,
                                                  Object... sentBody) {
        try {
            return responseExtract(typeRequest, baseUrl, addUrl, expectedStatusCode, parameterJsonPath, sentBody)
                    .as(Object.class);
        }
        catch (EmptyStackException e) {
            return null;
        }
    }

//    @SafeVarargs
//    public static <V> List<V> responsePojoList(String typeRequest, String baseUrl, String addUrl,
//                                               int expectedStatusCode, String parameterJsonPath,
//                                               Object... sentBody) {
//        return responseExtract(typeRequest, baseUrl, addUrl, expectedStatusCode, parameterJsonPath, sentBody)
//                .body().jsonPath().getList(parameterJsonPath, new <V>.getClass());
//    }

}
