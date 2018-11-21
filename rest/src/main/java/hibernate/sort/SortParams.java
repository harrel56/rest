package hibernate.sort;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.OrderImpl;

import hibernate.entities.User;

public class SortParams<T> {

	private Class<T> clazz;
	private Root<T> root;

	private String[] sorts;

	/**
	 * Empty constructor as a shorthand for no sorting
	 */
	public static <T> SortParams<T> empty() {
		return new SortParams<T>();
	}

	private SortParams() {
	}

	public SortParams(Class<T> clazz, String[] sorts) {
		this.clazz = clazz;
		this.sorts = sorts;
	}

	public void applySortParams(CriteriaBuilder builder, CriteriaQuery<T> crit, Root<T> root) {

		if (this.sorts != null) {
			this.root = root;

			List<Order> orders = new ArrayList<>(this.sorts.length);
			for (String sort : this.sorts) {
				orders.add(this.toOrder(sort));
			}

			crit.orderBy(orders);
		}
	}

	private Order toOrder(String sort) {

		String[] array = sort.split(":");

		if (array.length == 1) {
			return new OrderImpl(this.toExpression(array[0]));
		} else if (array.length == 2) {

			boolean asc;
			if ("asc".equals(array[1])) {
				asc = true;
			} else if ("desc".equals(array[1])) {
				asc = false;
			} else {
				throw new SortParamsException("Expected (asc, desc). Found: " + array[1]);
			}

			return new OrderImpl(this.toExpression(array[0]), asc);
		} else {
			throw new SortParamsException("Too many colon separated parameters!");
		}
	}

	private Expression<User> toExpression(String fieldName) {

		try {
			this.clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new SortParamsException("Invalid sort column name!", e);
		}

		return this.root.get(fieldName);
	}
}
