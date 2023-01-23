import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class API_methods {
    private final String REGISTER = "/auth/register";
    private final String USER = "/auth/user";
    private final String LOGIN = "/auth/login";
    private final String ORDERS = "/orders";
    private final String INGREDIENTS = "/ingredients";

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Orders order, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получить заказ")
    public ValidatableResponse getOrder(String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .get(ORDERS)
                .then();
    }

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredients() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .get(INGREDIENTS)
                .then();
    }

    @Step("Создать случайного пользователя")
    public ValidatableResponse createRandomUser(CreateUser user) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(REGISTER)
                .then();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse deleteUser(CreateUser user, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .body(user)
                .when()
                .delete(USER)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(UserCredential credentials) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(credentials)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Изменить данные пользователя")
    public ValidatableResponse changeDataUser(CreateUser user, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .body(user)
                .when()
                .patch(USER)
                .then();
    }
}
