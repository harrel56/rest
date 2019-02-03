package hibernate.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.Event;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.pagination.PaginationParams;

@Repository
public class EventDao {

	@Autowired
	private CommonDao commonDao;

	@Transactional
	public void addEvent(Event event) {
		this.commonDao.persist(event);
	}

	@Transactional
	public Event updateEvent(Event event) {
		return this.commonDao.merge(event);
	}

	@Transactional(readOnly = true)
	public Event findEventById(Long id) {
		return this.commonDao.findById(Event.class, id);
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents() {
		return this.commonDao.findAll(Event.class);
	}

	@Transactional(readOnly = true)
	public List<Event> getEvents(SearchParams<Event> searchParams, SortParams<Event> sortParams, PaginationParams paginationParams) {
		CriteriaBuilder builder = this.commonDao.getCriteriaBuilder();
		CriteriaQuery<Event> crit = builder.createQuery(Event.class);
		Root<Event> root = crit.from(Event.class);

		searchParams.applySearchFilters(builder, crit, root);
		sortParams.applySortParams(crit, root);

		return this.commonDao.findPaginatedByCriteria(Event.class, crit, paginationParams);
	}

	@Transactional(readOnly = true)
	public Long getEventsCount(SearchParams<Event> searchParams) {
		CriteriaBuilder builder = this.commonDao.getCriteriaBuilder();
		CriteriaQuery<Long> crit = builder.createQuery(Long.class);
		Root<Event> root = crit.from(Event.class);

		searchParams.applySearchFilters(builder, crit, root);

		return this.commonDao.findCountByCriteria(Event.class, crit, root);
	}

}
