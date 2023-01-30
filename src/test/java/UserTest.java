import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;


public class UserTest extends ApiMethods {

    User user;
    ApiMethods methods;
    private String token;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        user = User.getUser();
        methods = new ApiMethods();
    }

    @After
    public void tearDown() {
        try {
            UserCredential credentials = UserCredential.from(user);
            token = methods.loginUser(credentials)
                    .extract().path("accessToken");
            methods.deleteUser(user, token);
        } catch (IllegalArgumentException exception) {
            System.out.println("User not created");
        }
    }
    @Test
    @DisplayName("Тест на создание нового пользователя")
    public void createNewUserTest() {
        methods.createRandomUser(user)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на создание аналогичного пользователя")
    public void createSameUserTest() {
        methods.createRandomUser(user);
        methods.createRandomUser(user)
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Тест на создание пользователя без имени")
    public void createUserWithoutNameTest() {
        user.setName("");
        methods.createRandomUser(user)
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test

    @DisplayName("Тест на создание пользователя без почты")
    public void createUserWithoutEmailTest() {
        user.setEmail("");
        methods.createRandomUser(user)
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Тест на создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        user.setPassword("");
        methods.createRandomUser(user)
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
