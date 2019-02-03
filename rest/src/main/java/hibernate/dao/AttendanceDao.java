package hibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.Attendance;

@Repository
public class AttendanceDao {

	@Autowired
	private CommonDao commonDao;

	@Transactional
	public void addAttendance(Attendance attendance) {
		this.commonDao.persist(attendance);
	}

	@Transactional
	public Attendance updateAttendance(Attendance attendance) {
		return this.commonDao.merge(attendance);
	}
}
