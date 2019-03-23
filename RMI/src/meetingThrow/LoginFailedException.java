package meetingThrow;

/**
 * 登录失败
 */
public class LoginFailedException extends Exception {
    public LoginFailedException() {
        super("login failed !");
    }
}
