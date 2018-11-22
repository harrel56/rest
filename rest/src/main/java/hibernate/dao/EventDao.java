package hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.Event;

@Repository
public class EventDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addEvent(Event event) {
		this.em.persist(event);
	}

	@Transactional
	public Event updateEvent(Event event) {
		return this.em.merge(event);
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents() {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Event> crit = builder.createQuery(Event.class);
		Root<Event> root = crit.from(Event.class);
		crit.select(root);
		return this.em.createQuery(crit).getResultList();
	}

//	@Transactional(readOnly = true)
//	public List<User> getUsers(SearchParams<User> searchParams) {
//		return this.getUsers(searchParams, SortParams.empty());
//	}
//
//	@Transactional(readOnly = true)
//	public List<User> getUsers(SortParams<User> sortParams) {
//		return this.getUsers(UserSearchParams.empty(), sortParams);
//	}
//
//	@Transactional(readOnly = true)
//	public List<User> getUsers(SearchParams<User> searchParams, SortParams<User> sortParams) {
//		CriteriaBuilder builder = this.em.getCriteriaBuilder();
//		CriteriaQuery<User> crit = builder.createQuery(User.class);
//		Root<User> root = crit.from(User.class);
//
//		searchParams.applySearchFilters(builder, crit, root);
//		sortParams.applySortParams(builder, crit, root);
//
//		return this.em.createQuery(crit).getResultList();
//	}

}
