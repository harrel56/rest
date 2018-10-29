package hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.User;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addUser(User user) {
		this.em.persist(user);
	}

	@Transactional(readOnly = true)
	public List<User> findByLogin(String login) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);
		Root<User> root = crit.from(User.class);
		crit.where(builder.equal(root.get("login"), login));
		return this.em.createQuery(crit).getResultList();
	}

	@Transactional(readOnly = true)
	public List<User> findByEmail(String email) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);
		Root<User> root = crit.from(User.class);
		crit.where(builder.equal(root.get("email"), email));
		return this.em.createQuery(crit).getResultList();
	}

}
