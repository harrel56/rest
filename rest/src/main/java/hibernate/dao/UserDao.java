package hibernate.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.User;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;

@Repository
public class UserDao {

	@Autowired
	private CommonDao commonDao;

	@Transactional
	public void addUser(User user) {
		this.commonDao.persist(user);
	}

	@Transactional
	public User updateUser(User user) {
		return this.commonDao.merge(user);
	}

	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return this.commonDao.findAll(User.class);
	}

	@Transactional(readOnly = true)
	public List<User> getUsers(SearchParams<User> searchParams, SortParams<User> sortParams) {
		CriteriaBuilder builder = this.commonDao.getCriteriaBuilder();
		CriteriaQuery<User> crit = builder.createQuery(User.class);
		Root<User> root = crit.from(User.class);

		searchParams.applySearchFilters(builder, crit, root);
		sortParams.applySortParams(crit, root);

		return this.commonDao.findByCriteria(User.class, crit);
	}

	@Transactional(readOnly = true)
	public User findByLogin(String login) {
		return this.commonDao.findFirstByField(User.class, "login", login);
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return this.commonDao.findFirstByField(User.class, "email", email);
	}

}
