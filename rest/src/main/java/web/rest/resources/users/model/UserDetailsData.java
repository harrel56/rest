package web.rest.resources.users.model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class UserDetailsData implements Serializable {

	private final String name;
	private final String surname;
	private final String location;
	private final Date dateOfBirth;

	public UserDetailsData(String name, String surname, String location, Date dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.location = location;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getLocation() {
		return this.location;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}
}
