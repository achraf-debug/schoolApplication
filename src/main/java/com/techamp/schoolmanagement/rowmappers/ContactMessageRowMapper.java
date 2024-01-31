package com.techamp.schoolmanagement.rowmappers;

import com.techamp.schoolmanagement.model.ContactMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMessageRowMapper implements RowMapper<ContactMessage> {
	@Override
	public ContactMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
		ContactMessage contactMessage = new ContactMessage();
		contactMessage.setContactId((long) rs.getInt("CONTACT_ID"));
		contactMessage.setMessage(rs.getString("MESSAGE"));
		contactMessage.setName(rs.getString("NAME"));
		contactMessage.setMobileNum(rs.getString("MOBILE_NUM"));
		contactMessage.setEmail(rs.getString("EMAIL"));
		contactMessage.setStatus(rs.getString("STATUS"));
		contactMessage.setSubject(rs.getString("SUBJECT"));
		contactMessage.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
		contactMessage.setCreatedBy(rs.getString("CREATED_BY"));
		
		if(null != rs.getTimestamp("UPDATED_AT")) {
			contactMessage.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
		}
		contactMessage.setUpdatedBy(rs.getString("UPDATED_BY"));
		return contactMessage;
	}
}
