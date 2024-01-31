package com.techamp.schoolmanagement.repositories;


import com.techamp.schoolmanagement.model.ContactMessage;
import com.techamp.schoolmanagement.rowmappers.ContactMessageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContactRepository {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ContactRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int saveContactMsg(ContactMessage contactMessage) {
		String sql = "INSERT INTO CONTACT_MSG (NAME, MOBILE_NUM, EMAIL, SUBJECT, " +
		"MESSAGE, STATUS, CREATED_AT, CREATED_BY) VALUES (?,?,?,?,?,?,?,?)";
		
		return jdbcTemplate.update(sql, contactMessage.getName(), contactMessage.getMobileNum(),
		contactMessage.getEmail(), contactMessage.getSubject(), contactMessage.getMessage(),
		contactMessage.getStatus(), contactMessage.getCreatedAt(), contactMessage.getCreatedBy());
	}
	
	public List<ContactMessage> findMessagesWithOpenStatus(String status) {
		String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS= ?";
		
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, status);
			}
		}, new ContactMessageRowMapper());
	}
	
	public int updateMessageStatus(int id, String status, String updateBy) {
		String sql = "UPDATE CONTACT_MSG SET STATUS = ?, UPDATED_BY=?, UPDATED_AT=?  WHERE CONTACT_ID = ?";
		
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, status);
				ps.setString(2, updateBy);
				ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
				ps.setInt(4, id);
			}
		});
	}
}
