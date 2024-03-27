package se.iths.springbootgroupproject.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.springbootgroupproject.dto.PublicMessageAndUsername;
import se.iths.springbootgroupproject.repositories.MessageRepository;

import java.util.List;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Cacheable("messages")
    public List<PublicMessageAndUsername> findAllByPrivateMessageIsFalse() {
        return messageRepository.findAllByPrivateMessageIsFalse();
    }
    @Cacheable("messages")
    public List<PublicMessageAndUsername> findAllByPrivateMessageIsFalse(Pageable pageable){
        return messageRepository.findAllByPrivateMessageIsFalse(pageable);
    }

    @CacheEvict(value = "messages", allEntries = true)
    public void setMessagePrivacy(boolean isPrivate, Long id) {
        messageRepository.setMessagePrivacy(isPrivate, id);
    }

    @CacheEvict(value = "messages", allEntries = true)
    public void editMessage(String updatedBody, Long id) {
        messageRepository.editMessage(updatedBody, id);
    }

    @CacheEvict(value = "messages", allEntries = true)
    public void editTitle(String updatedTitle, Long id) {
        messageRepository.editTitle(updatedTitle, id);
    }
}