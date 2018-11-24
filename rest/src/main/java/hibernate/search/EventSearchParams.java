package hibernate.search;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import hibernate.entities.Event;

public class EventSearchParams implements SearchParams<Event> {

	private String name;
	private String description;
	private String state;
	private Timestamp startTimeGt;
	private Timestamp startTimeLt;
	private Timestamp endTimeGt;
	private Timestamp endTimeLt;

	/**
	 * Empty constructor as a shorthand for no search filtering
	 */
	public static EventSearchParams empty() {
		return new EventSearchParams();
	}

	private EventSearchParams() {

	}

	public EventSearchParams(String name, String description, String state, Timestamp startTimeGt, Timestamp startTimeLt, Timestamp endTimeGt,
			Timestamp endTimeLt) {
		this.name = name;
		this.description = description;
		this.state = state;
		this.startTimeGt = startTimeGt;
		this.startTimeLt = startTimeLt;
		this.endTimeGt = endTimeGt;
		this.endTimeLt = endTimeLt;
	}

	@Override
	public void applySearchFilters(CriteriaBuilder builder, CriteriaQuery<Event> crit, Root<Event> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (this.name != null) {
			predicates.add(builder.like(root.get("name"), this.addWildcards(this.name)));
		}
		if (this.description != null) {
			predicates.add(builder.like(root.get("description"), this.addWildcards(this.description)));
		}
		if (this.state != null) {
			predicates.add(builder.equal(root.get("state"), this.state.toUpperCase()));
		}
		if (this.startTimeGt != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("startTime"), this.startTimeGt));
		}
		if (this.startTimeLt != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("startTime"), this.startTimeLt));
		}
		if (this.endTimeGt != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("endTime"), this.endTimeGt));
		}
		if (this.endTimeLt != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("endTime"), this.endTimeLt));
		}

		if (!predicates.isEmpty()) {
			crit.where(predicates.toArray(new Predicate[predicates.size()]));
		}

	}

}
