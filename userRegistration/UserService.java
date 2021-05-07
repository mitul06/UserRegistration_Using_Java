import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

public class UserService {

    private List<User> users = new ArrayList<>();

    public boolean isUserExist(User user) {
        return !users.stream()
            .filter(u -> u.getEmail().equals(user.getEmail()))
            .collect(Collectors.toList())
            .isEmpty();
    }

    public User registerUser(User user) throws Exception {
        if(isUserExist(user)) {
            throw new Exception("User already exists");
        }
        users.add(user);
        return user;
    }

    public User login(String email, String password) throws LoginException {
        String error = "";
        for(User user: users) {
            if(user.getEmail().equals(email)) {
                if(user.getPassword().equals(password)) {
                    return user;
                } else {
                    error = "Incorrect Password";
                }
            }
        }
        if(error.isBlank()) {
            error = "User does not exist. Please register first.";
        }
        throw new LoginException(error);
    }
}
