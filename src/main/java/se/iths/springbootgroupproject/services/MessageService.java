package se.iths.springbootgroupproject.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByPrivateMessageIsFalse() {
        return messageRepository.findAllByPrivateMessageIsFalse();
    }

    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByUserIdAndPrivateMessageIsFalse(Long id) {
        return messageRepository.findAllByUserIdAndPrivateMessageIsFalse(id);
    }

    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByPrivateMessageIsFalse(Pageable pageable) {
        return messageRepository.findAllByPrivateMessageIsFalse(pageable);
    }

    @Cacheable("messages")
    public List<MessageAndUsername> findAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable).getContent().stream()
                .map(message -> new MessageAndUsername(
                        message.getId(),
                        message.getDate(),
                        message.getLastChanged(),
                        message.getTitle(),
                        message.getMessageBody(),
                        message.getUser().getUserName()))
                .toList();
    }

    @Cacheable("messages")
    public List<MessageAndUsername> findAllMessages() {
        return messageRepository.findAll().stream()
                .map(message -> new MessageAndUsername(
                        message.getId(),
                        message.getDate(),
                        message.getLastChanged(),
                        message.getTitle(),
                        message.getMessageBody(),
                        message.getUser().getUserName()))
                .toList();
    }

    public List<MessageAndUsername> findAllMessagesByUser(User user, Pageable pageable) {
        return messageRepository.findAllByUser(user, pageable);
    }

    public List<MessageAndUsername> findAllMessagesByUser(User user) {
        return messageRepository.findAllByUser(user);
    }
    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public void save(Message message) {
        messageRepository.save(message);
    }

    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public Message findById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent())
            return message.get();
        throw new EntityNotFoundException();
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}