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
import hibernate.search.SearchParams;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addUser(User user) {
		this.em.persist(user);
	}

	@Transactional
	public User updateUser(User user) {
		return this.em.merge(user);
	}

	@Transactional(readOnly = true)
	public List<User> getUsers() {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);
		Root<User> root = crit.from(User.class);
		crit.select(root);
		return this.em.createQuery(crit).getResultList();
	}

	@Transactional(readOnly = true)
	public List<User> getUsers(SearchParams<User> params) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);

		params.applySearchFilters(builder, crit);

		return this.em.createQuery(crit).getResultList();
	}

	@Transactional(readOnly = true)
	public User findByLogin(String login) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);
		Root<User> root = crit.from(User.class);
		crit.where(builder.equal(root.get("login"), login));

		List<User> users = this.em.createQuery(crit).getResultList();
		if (!users.isEmpty()) {
			return users.get(0);
		} else {
			return null;
		}
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
