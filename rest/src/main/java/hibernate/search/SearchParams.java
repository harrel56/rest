package hibernate.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface SearchParams<T> {

	void applySearchFilters(CriteriaBuilder builder, CriteriaQuery<T> crit);

	default String addWildcards(String exp) {
		return "%" + exp + "%";
	}
}
