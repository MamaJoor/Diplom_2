import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static User getUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru",
                RandomStringUtils.randomAlphanumeric(6, 10) + "pass",
                RandomStringUtils.randomAlphanumeric(6, 10) + "Name"
        );
    }
}
