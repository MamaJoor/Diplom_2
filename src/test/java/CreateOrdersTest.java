import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrdersTest extends ApiMethods {
    User user;
    ApiMethods methods;
    private String token;
    private List<String> list;


    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        user = User.getUser();
        methods = new ApiMethods();
                token = methods.createRandomUser(user)
                .extract().path("accessToken");
        list = methods.getIngredients()
                .extract().path("data._id");
    }

    @After
    public void tearDown() {
        methods.deleteUser(user, token);
    }

    @Test
    @DisplayName("Тест на создание заказа с авторизацией")
    public void createOrderWithAuthorizationTest() {
        Orders order = new Orders(list);
        methods.createOrder(order, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на создание заказа без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        Orders order = new Orders(list);
        token = "";
        methods.createOrder(order, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на создание заказа с ингридиентами")
    public void createOrderWithIngredientsTest() {
        Orders order = new Orders(list);
        methods.createOrder(order, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        list = List.of();
        Orders order = new Orders(list);
        methods.createOrder(order, token)
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест на создание заказа с неверным хешем ингредиентов")
    public void createOrderWithIncorrectHashIngredientsTest() {
        list = List.of("123", "");
        Orders order = new Orders(list);
        methods.createOrder(order, token)
                .statusCode(500);
    }

}
