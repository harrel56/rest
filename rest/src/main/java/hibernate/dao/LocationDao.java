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
import hibernate.search.LocationSearchParams;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;

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
	public Location findLocationById(Long id) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Location> crit = builder.createQuery(Location.class);
		Root<Location> root = crit.from(Location.class);
		crit.where(builder.equal(root.get("id"), id));
		List<Location> locations = this.em.createQuery(crit).getResultList();

		if (!locations.isEmpty()) {
			return locations.get(0);
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Location> getLocations() {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Location> crit = builder.createQuery(Location.class);
		Root<Location> root = crit.from(Location.class);
		crit.select(root);
		return this.em.createQuery(crit).getResultList();
	}

	@Transactional(readOnly = true)
	public List<Location> getLocations(SearchParams<Location> searchParams) {
		return this.getLocations(searchParams, SortParams.empty());
	}

	@Transactional(readOnly = true)
	public List<Location> getLocations(SortParams<Location> sortParams) {
		return this.getLocations(LocationSearchParams.empty(), sortParams);
	}

	@Transactional(readOnly = true)
	public List<Location> getLocations(SearchParams<Location> searchParams, SortParams<Location> sortParams) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<Location> crit = builder.createQuery(Location.class);
		Root<Location> root = crit.from(Location.class);

		searchParams.applySearchFilters(builder, crit, root);
		sortParams.applySortParams(builder, crit, root);

		return this.em.createQuery(crit).getResultList();
	}

}
