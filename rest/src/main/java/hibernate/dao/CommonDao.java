package hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import web.rest.resources.pagination.PaginationParams;

@Repository
public class CommonDao {

	@PersistenceContext
	private EntityManager em;

	public CriteriaBuilder getCriteriaBuilder() {
		return this.em.getCriteriaBuilder();
	}

	@Transactional
	public <T> void persist(T entity) {
		this.em.persist(entity);
	}

	@Transactional
	public <T> T merge(T entity) {
		return this.em.merge(entity);
	}

	/**
	 * Finds single object by id
	 * 
	 * @param clazz class of entity class, which primary key is id field
	 * @param id
	 * @return Object of class T, or null if not found
	 */
	@Transactional(readOnly = true)
	public <T> T findById(Class<T> clazz, Long id) {
		return this.findFirstByField(clazz, "id", id);
	}

	/**
	 * Finds all objects from given class
	 * 
	 * @param clazz class of entity class
	 * @return List of objects of class T
	 */
	@Transactional(readOnly = true)
	public <T> List<T> findAll(Class<T> clazz) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<T> crit = builder.createQuery(clazz);
		Root<T> root = crit.from(clazz);
		crit.select(root);
		return this.em.createQuery(crit).getResultList();
	}

	/**
	 * Finds all objects from given class which match given criteria
	 * 
	 * @param clazz    class of entity class
	 * @param criteria
	 * @return List of objects of class T
	 */
	@Transactional(readOnly = true)
	public <T> List<T> findByCriteria(Class<T> clazz, CriteriaQuery<T> crit) {
		return this.em.createQuery(crit).getResultList();
	}

	/**
	 * Finds paginated list of objects from given class which match given criteria
	 * 
	 * @param clazz            class of entity class
	 * @param criteria
	 * @param paginationParams pagination params provided by user
	 * @return List of objects of class T
	 */
	@Transactional(readOnly = true)
	public <T> List<T> findPaginatedByCriteria(Class<T> clazz, CriteriaQuery<T> crit, PaginationParams paginationParams) {
		return this.em.createQuery(crit).setFirstResult(paginationParams.getFirstPos()).setMaxResults(paginationParams.size).getResultList();
	}

	/**
	 * Same as {@link #findByCriteria(Class, CriteriaQuery)} but instead of
	 * returning list of object, it returns number of matching objects
	 * 
	 * @param clazz
	 * @param crit
	 * @return Number of matching objects
	 */
	@NotNull
	@Transactional(readOnly = true)
	public <T> Long findCountByCriteria(Class<T> clazz, CriteriaQuery<Long> crit, Root<T> root) {
		crit.select(this.getCriteriaBuilder().count(root));
		return this.em.createQuery(crit).getSingleResult();
	}

	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return First matching object, otherwise null
	 */
	@Transactional(readOnly = true)
	public <T> T findFirstByField(Class<T> clazz, String fieldName, Object fieldValue) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<T> crit = builder.createQuery(clazz);
		Root<T> root = crit.from(clazz);
		crit.where(builder.equal(root.get(fieldName), fieldValue));
		List<T> results = this.em.createQuery(crit).getResultList();

		return results.isEmpty() ? null : results.get(0);
	}

}
