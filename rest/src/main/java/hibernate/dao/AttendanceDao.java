package hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.Attendance;

@Repository
public class AttendanceDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addAttendance(Attendance attendance) {
		this.em.persist(attendance);
	}

	@Transactional
	public Attendance updateAttendance(Attendance attendance) {
		return this.em.merge(attendance);
	}
}
