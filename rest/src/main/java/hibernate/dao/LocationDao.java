package hibernate.dao;

import hibernate.entities.Location;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.rest.resources.pagination.PaginationParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LocationDao {

    @Autowired
    private CommonDao commonDao;

    @Transactional
    public void addLocation(Location location) {
        this.commonDao.persist(location);
    }

    @Transactional
    public Location updateLocation(Location location) {
        return this.commonDao.merge(location);
    }

    @Transactional(readOnly = true)
    public Location findLocationById(Long id) {
        return this.commonDao.findById(Location.class, id);
    }

    @Transactional(readOnly = true)
    public List<Location> getLocations() {
        return this.commonDao.findAll(Location.class);
    }

    @Transactional(readOnly = true)
    public List<Location> getLocations(SearchParams<Location> searchParams, SortParams<Location> sortParams, PaginationParams paginationParams) {
        CriteriaBuilder builder = this.commonDao.getCriteriaBuilder();
        CriteriaQuery<Location> crit = builder.createQuery(Location.class);
        Root<Location> root = crit.from(Location.class);

        searchParams.applySearchFilters(builder, crit, root);
        sortParams.applySortParams(crit, root);

        return this.commonDao.findPaginatedByCriteria(Location.class, crit, paginationParams);
    }

    @Transactional(readOnly = true)
    public Long getLocationsCount(SearchParams<Location> searchParams) {
        CriteriaBuilder builder = this.commonDao.getCriteriaBuilder();
        CriteriaQuery<Long> crit = builder.createQuery(Long.class);
        Root<Location> root = crit.from(Location.class);

        searchParams.applySearchFilters(builder, crit, root);

        return this.commonDao.findCountByCriteria(Location.class, crit, root);
    }

}
