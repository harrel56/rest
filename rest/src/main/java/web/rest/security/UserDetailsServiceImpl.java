package web.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;

/**
 * Created by tharsan on 4/24/18.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		boolean accountEnabled = true;
		List<User> userList = this.userDao.findByLogin(username);

		if (userList.isEmpty()) {
			throw new UsernameNotFoundException("Invalid login or password");
		} else if (userList.get(0).getActivationString() != null) {
			accountEnabled = false;
		}

		User user = userList.get(0);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
				accountEnabled, true, true, true, authorities);
	}
}
