package web.rest.user.management;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.User;

@Component
public class UserManagementUtil {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public UserRegistrationResponseData.ResponseState validateRegistrationData(
			UserRegistrationRequestData registrationData) {

		if (!this.isLoginUnique(registrationData.getLogin())) {
			return UserRegistrationResponseData.ResponseState.LOGIN_ALREADY_TAKEN;
		} else {
			return null;
		}

	}

	@Transactional
	public void addNewUser(User user) {
		this.em.persist(user);
	}

	private boolean isLoginUnique(String login) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);
		Root<User> root = crit.from(User.class);
		crit.where(builder.equal(root.get("login"), login));
		return this.em.createQuery(crit).getResultList().isEmpty();
	}
}
