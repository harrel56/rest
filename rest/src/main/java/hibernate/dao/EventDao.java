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
import hibernate.search.EventSearchParams;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;

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
	public Event findEventById(Long id) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Event> crit = builder.createQuery(Event.class);
		Root<Event> root = crit.from(Event.class);
		crit.where(builder.equal(root.get("id"), id));
		List<Event> events = this.em.createQuery(crit).getResultList();

		if (!events.isEmpty()) {
			return events.get(0);
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents() {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Event> crit = builder.createQuery(Event.class);
		Root<Event> root = crit.from(Event.class);
		crit.select(root);
		return this.em.createQuery(crit).getResultList();
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents(SearchParams<Event> searchParams) {
		return this.getEvents(searchParams, SortParams.empty());
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents(SortParams<Event> sortParams) {
		return this.getEvents(EventSearchParams.empty(), sortParams);
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents(SearchParams<Event> searchParams, SortParams<Event> sortParams) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Event> crit = builder.createQuery(Event.class);
		Root<Event> root = crit.from(Event.class);

		searchParams.applySearchFilters(builder, crit, root);
		sortParams.applySortParams(builder, crit, root);

		return this.em.createQuery(crit).getResultList();
	}

}
