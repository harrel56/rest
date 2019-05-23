package server.rest.config.security;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) {

        boolean accountEnabled = true;
        User user = this.userDao.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid login or password");
        } else if (user.getActivationString() != null) {
            accountEnabled = false;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), accountEnabled, true, true, true,
                authorities);
    }
}
