package hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.Location;

@Repository
public class LocationDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addLocation(Location location) {
		this.em.persist(location);
	}

	@Transactional
	public Location updateLocation(Location location) {
		return this.em.merge(location);
	}

	@Transactional(readOnly = true)
	public List<Location> getLocations() {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Location> crit = builder.createQuery(Location.class);
		Root<Location> root = crit.from(Location.class);
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
