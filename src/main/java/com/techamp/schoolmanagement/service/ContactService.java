package com.techamp.schoolmanagement.service;


import com.techamp.schoolmanagement.constants.Constants;
import com.techamp.schoolmanagement.model.ContactMessage;
import com.techamp.schoolmanagement.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {
	
	private final ContactRepository contactRepository;
	
	public boolean saveMessageDetails(ContactMessage contactMessage) {
		boolean isSaved = false;
		try {
			contactMessage.setStatus(Constants.OPEN);
			contactMessage.setCreatedBy(Constants.ANONYMOUS);
			contactMessage.setCreatedAt(LocalDateTime.now());
			int result = contactRepository.saveContactMsg(contactMessage);
			if (result>0) {
				isSaved = true;
			}
		} catch (Exception e) {
			log.error("********* EXCEPTION *******" + e.getMessage());
		}
		log.info("*****" + contactMessage.toString() + "******");
		return isSaved;
	}
	
	public List<ContactMessage> getMessagesWithOpenStatus() {
		return contactRepository.findMessagesWithOpenStatus(Constants.OPEN);
	}
	
	public boolean updateMessageStatus(int id, String status, String updateBy) {
		boolean isUpdated = false;
		try {
			int result = contactRepository.updateMessageStatus(id, status, updateBy);
			if(result > 0) {
				isUpdated = true;
			}
			
		} catch (Exception e) {
			log.error("******* EXCEPTION ******* \n" + e.getMessage());
		}
		return isUpdated;
	}
}
