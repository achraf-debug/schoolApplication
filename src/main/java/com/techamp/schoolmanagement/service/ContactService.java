package com.techamp.schoolmanagement.service;


import com.techamp.schoolmanagement.constants.Constants;
import com.techamp.schoolmanagement.model.ContactMessage;
import com.techamp.schoolmanagement.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
			ContactMessage result = contactRepository.save(contactMessage);
			if (null != result && result.getContactId()>0) {
				isSaved = true;
			}
		} catch (Exception e) {
			log.error("********* EXCEPTION ********" + e.getMessage());
		}
		log.info("*****" + contactMessage.toString() + "******");
		return isSaved;
	}
	
	public List<ContactMessage> getMessagesWithOpenStatus() {
		return contactRepository.getContactMessageByStatus(Constants.OPEN);
	}
	
	public boolean updateMessageStatus(Long contactId, String status, String updateBy) {
		boolean isUpdated = false;
		Optional<ContactMessage> contactMessage = contactRepository.findById(contactId);
		contactMessage.ifPresent(contact -> {
			contact.setStatus(Constants.CLOSE);
			contact.setUpdatedBy(updateBy);
			contact.setUpdatedAt(LocalDateTime.now());
		});
		ContactMessage contactMessage1 = contactRepository.save(contactMessage.get());
		
		if ( null != contactMessage1 && contactMessage1.getUpdatedBy() != null) {
			isUpdated = true;
		}
		
		return isUpdated;
	}
}
