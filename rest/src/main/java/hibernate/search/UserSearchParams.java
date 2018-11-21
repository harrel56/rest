package hibernate.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import hibernate.entities.User;

public class UserSearchParams implements SearchParams<User> {

	private final String login;
	private final String name;
	private final String surname;
	private final String location;

	public UserSearchParams(String login, String name, String surname, String location) {
		this.login = login;
		this.name = name;
		this.surname = surname;
		this.location = location;
	}

	@Override
	public void applySearchFilters(CriteriaBuilder builder, CriteriaQuery<User> crit) {

		Root<User> root = crit.from(User.class);
		List<Predicate> predicates = new ArrayList<>();

		if (this.login != null) {
			predicates.add(builder.like(root.get("login"), this.addWildcards(this.login)));
		}
		if (this.name != null) {
			predicates.add(builder.like(root.get("name"), this.addWildcards(this.name)));
		}
		if (this.surname != null) {
			predicates.add(builder.like(root.get("surname"), this.addWildcards(this.surname)));
		}
		if (this.location != null) {
			predicates.add(builder.like(root.get("location"), this.addWildcards(this.location)));
		}

		crit.where(predicates.toArray(new Predicate[predicates.size()]));
	}

}
