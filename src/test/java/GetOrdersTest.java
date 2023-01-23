import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersTest extends API_methods{

    CreateUser user;
    API_methods methods;
    private String token;


    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        user = CreateUser.getUser();
        methods = new API_methods();
        token = methods.createRandomUser(user)
                .extract().path("accessToken");
    }

    @After
    public void tearDown() {
        methods.deleteUser(user, token);
    }


    @Test
    @DisplayName("Тест на получаение заказа с авторизацией")
    public void getOrderWithAuthorizationTest() {
        methods.getOrder(token)
                .statusCode(200);
    }

    @Test
    @DisplayName("Тест на получаение заказа без авторизации")
    public void getOrderWithoutAuthorizationTest() {
        methods.getOrder("")
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
