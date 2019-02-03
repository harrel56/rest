package hibernate.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import hibernate.entities.Location;

public class LocationSearchParams implements SearchParams<Location> {

	private String name;
	private String description;
	private String state;
	private Double latitude;
	private Double longitude;
	private Double radius;

	/**
	 * Empty constructor as a shorthand for no search filtering
	 */
	public static LocationSearchParams empty() {
		return new LocationSearchParams();
	}

	private LocationSearchParams() {

	}

	public LocationSearchParams(String name, String description, String state, Double latitude, Double longitude, Double radius) {
		this.name = name;
		this.description = description;
		this.state = state;

		if (latitude == null && longitude != null || latitude != null && longitude == null) {
			throw new SearchParamsException();
		}

		this.latitude = latitude;
		this.longitude = longitude;

		if (radius == null) {
			this.radius = Double.valueOf(1.0);
		} else {
			this.radius = radius;
		}
	}

	@Override
	public void applySearchFilters(CriteriaBuilder builder, CriteriaQuery<?> crit, Root<Location> root) {

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
		if (this.latitude != null) {
			predicates.add(builder.between(root.get("latitude"), this.latitude - this.radius, this.latitude + this.radius));
		}
		if (this.longitude != null) {
			predicates.add(builder.between(root.get("longitude"), this.longitude - this.radius, this.longitude + this.radius));
		}

		if (!predicates.isEmpty()) {
			crit.where(predicates.toArray(new Predicate[predicates.size()]));
		}

	}

}
