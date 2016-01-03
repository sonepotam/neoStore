package ru.pavel2107.neostoreRest.utils;

/**
 * Created by lenovo on 18.12.2015.
 */
//
// кодирока паролей.
// как-то не очень хорошо хранить открытые пароли клиентов в таблицах. могут не понять
//
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

public class PasswordUtil {
  private static final LoggerWrapper LOG = LoggerWrapper.get( PasswordUtil.class);

  static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  public static BCryptPasswordEncoder getPasswordEncoder() {
     LOG.info( "PasswordEncoder.getPasswordEncoder");
     return PASSWORD_ENCODER;
  }

  public static String encode(String newPassword) {
      LOG.info( "PasswordEncoder.encode. password=" + newPassword);
      if (StringUtils.isEmpty(newPassword)) {
        return null;
     }
     return PASSWORD_ENCODER.encode(newPassword);
}

   public static boolean isMatch(String rawPassword, String password) {
      boolean result = PASSWORD_ENCODER.matches(rawPassword, password);
      LOG.info( "PasswordEncoder.isMath. math=" + rawPassword + "/" + password + "/" + result);
      return result;
   }


}
