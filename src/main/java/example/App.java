package example;

public class App {

    public static void main(String[] args) throws Exception {

        UserService service = new UserService();
        service.findUser("admin");
        service.deleteUser("admin");
    }
}