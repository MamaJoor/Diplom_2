import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserUpdateTest extends ApiMethods {
    User user;
    ApiMethods methods;
    private String token;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        user = User.getUser();
        methods = new ApiMethods();
        token = methods.createRandomUser(user)
                .extract().path("accessToken");
    }

    @After
    public void tearDown() {
        methods.deleteUser(user, token);
    }

    @Test
    @DisplayName("Тест на изменение почты пользователя с авторизацией")
    public void updateEmailTest() {
        user.setEmail("qwerty123@yandex.ru");
        methods.changeDataUser(user, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение пароля пользователя с авторизацией")
    public void updatePasswordTest() {
        user.setPassword("qwerty123");
        methods.changeDataUser(user, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение имени пользователя с авторизацией")
    public void updateNameTest() {
        user.setName("Артем");
        methods.changeDataUser(user, token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Тест на изменение почты пользователя без авторизации")
    public void updateEmailWithoutAuthorizationTest() {
        user.setEmail("qwerty123@yandex.ru");
        token = "qqq";
        methods.changeDataUser(user, token)
                .statusCode(401)
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Тест на изменение пароля пользователя без авторизации")
    public void updatePasswordWithoutAuthorizationTest() {
        user.setPassword("qwerty123");
        token = "qqq";
        methods.changeDataUser(user, token)
                .statusCode(401)
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    public void updateNameWithoutAuthorizationTest() {
        user.setName("Artem");
        token = "qqq";
        methods.changeDataUser(user, token)
                .statusCode(401)
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

}
