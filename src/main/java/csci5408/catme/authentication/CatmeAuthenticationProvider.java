package csci5408.catme.authentication;

import csci5408.catme.dao.IUserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CatmeAuthenticationProvider implements AuthenticationProvider {

    private final IUserDao userDao;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CatmeAuthenticationProvider(IUserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    private UserSummary authenticate(String email, String password) {
        User u = userDao.findByEmail(email);
        if(u == null) {
            throw new UsernameNotFoundException(email+ " Not Exist");
        }
        if(bCryptPasswordEncoder.matches(password, u.getPassword())) {
            return UserSummary.from(u);
        }
        throw new RuntimeException("Wrong Username or Password Exception");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserSummary summary = authenticate(email, password);
        if(summary != null) {
            return new UsernamePasswordAuthenticationToken(summary, password, new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
