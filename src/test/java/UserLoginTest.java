import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest extends API_methods{
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
    @DisplayName("Тест на авторизацию существующего пользователя")
    public void validLoginUserTest() {
        UserCredential credentials = UserCredential.from(user);
        methods.loginUser(credentials)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест с некорректным паролем")
    public void loginWithIncorrectPasswordTest() {
        UserCredential credentials = UserCredential.from(user);
        credentials.setPassword("312123321");
        methods.loginUser(credentials)
                .statusCode(401)
                .assertThat()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Тест с некорректной почтой")
    public void loginWithIncorrectEmailTest() {
        UserCredential credentials = UserCredential.from(user);
        credentials.setEmail("231231");
        methods.loginUser(credentials)
                .statusCode(401)
                .assertThat()
                .body("message", equalTo("email or password are incorrect"));
    }

}
