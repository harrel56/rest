package hibernate.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface SearchParams<T> {

	void applySearchFilters(CriteriaBuilder builder, CriteriaQuery<T> crit, Root<T> root);

	default String addWildcards(String exp) {
		return "%" + exp + "%";
	}
}
