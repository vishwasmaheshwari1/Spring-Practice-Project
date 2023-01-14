package com.vmspring.schoolapp.service;

import com.vmspring.schoolapp.config.SchoolProps;
import com.vmspring.schoolapp.constants.AppConstants;
import com.vmspring.schoolapp.model.Contact;
import com.vmspring.schoolapp.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
//@RequestScope
//@SessionScope
//@ApplicationScope
public class ContactService {

    //using Slf4j instead of instantiating logger class
    //private static Logger log = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    SchoolProps schoolProps;
    private int counter = 0;

    public ContactService() {
        System.out.println("Contact service been initialized");
    }

    public boolean saveMessageDetails(Contact contact) {

        boolean isSaved = false;

        contact.setStatus(AppConstants.OPEN);
//        contact.setCreatedBy(AppConstants.ANONYMOUS);
//        contact.setCreatedAt(LocalDateTime.now());

        log.info(contact.toString());

       //int result = contactRepository.saveContactMsg(contact);
       Contact result = contactRepository.save(contact);


        if (result != null && result.getContactId() > 0) {
            isSaved = true;
        }
//        if (result >0) {
//            isSaved = true;
//        }
                //Controller layer (business logic) -> Service Layer -> persistant (data/repository) layer

        return isSaved;
    }
//    public List<Contact> findMsgsWithOpenStatus(){
//        //List<Contact> contactMsgs = contactRepository.findMsgsWithStatus(AppConstants.OPEN);
//        List<Contact> contactMsgs = contactRepository.findByStatus(AppConstants.OPEN);
//        return contactMsgs;
//    }


    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortfield, String sortDir) {
        //int pageSize = 5;
        int pageSize = schoolProps.getPageSize();
        if(null!=schoolProps.getContact() && null!=schoolProps.getContact().get("pageSize")){
            pageSize = Integer.parseInt(schoolProps.getContact().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum,pageSize,
                (sortDir.equals("asc")) ? Sort.by(sortfield).ascending() : Sort.by(sortfield).descending());

        //Page<Contact> msgPage = contactRepository.findByStatus(AppConstants.OPEN, pageable);
        Page<Contact> msgPage = contactRepository.findOpenMsgs(AppConstants.OPEN, pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated = false;
        //int result = contactRepository.updateStatusById(AppConstants.CLOSE, contactId);
        int result = contactRepository.UpdateMsgStatus(AppConstants.CLOSE, contactId);
        if (result > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }
//    public boolean updateMsgStatus(int contactId, String updatedBy){
//        boolean isUpdated = false;
//        //int result = contactRepository.updateMsgStatus(contactId,AppConstants.CLOSE, updatedBy);
//        Optional<Contact> c = contactRepository.findById(contactId);
//        c.ifPresent(contact -> {
//            contact.setStatus(AppConstants.CLOSE);
////            contact.setUpdatedBy(updatedBy);
////            contact.setUpdatedAt(LocalDateTime.now());
//        });
//
//
////        int result = contactRepository.save(c);
////        if(result>0) {
////            isUpdated = true;
////        }
//
//        Contact result = contactRepository.save(c.get());
//
//
//        if (result != null && result.getUpdatedBy() != null) {
//            isUpdated = true;
//        }
//
//        return isUpdated;
//    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
