package se.iths.springbootgroupproject.configurations;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.MessageRepository;
import se.iths.springbootgroupproject.repositories.UserRepository;

import java.time.LocalDate;

@Configuration
@EnableJpaAuditing
public class DbInitConfig {

    //For development purpose we init db with some values
    @Bean
    ApplicationRunner databaseInit2(MessageRepository repository, UserRepository userRepository){
        return args -> {
            var result = repository.findByTitle("TempTitle");
            var result2 = repository.findByTitle("En riktig titel");
            var result3 = repository.findByTitle("Privat meddelande titel");
            var result4 = repository.findByTitle("Test 255");
            var userResult = userRepository.findByUserName("Jame Sbond 070");
            var user = new User();
            if (userResult.isEmpty()){
                user.setUserName("Jame Sbond 070");
                userRepository.save(user);
            }
            if (result.isEmpty()) {
                var message = new Message();
                message.setTitle("TempTitle");
                message.setMessageBody("BodyOfTheMessage");
                saveUser(message, user, repository);
            }
            if (result2.isEmpty()){
                var message = new Message();
                message.setTitle("En riktig titel");
                message.setMessageBody("Riktig riklig text");
                saveUser(message, user, repository);
            }
            if (result3.isEmpty()){
                var message = new Message();
                message.setTitle("Privat meddelande titel");
                message.setMessageBody("Schh hemligt hehehihi");
                message.setPrivateMessage(true);
                saveUser(message, user, repository);
            }
            if (result4.isEmpty()){
                var message = new Message();
                message.setTitle("Test 255");
                message.setMessageBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed faucibus felis ac libero vestibulum, sed lobortis velit sodales. Nulla non risus non enim scelerisque ullamcorper. Suspendisse potenti. Proin euismod dictum velit, id consectetur elit tempor et.");
                saveUser(message, user, repository);
            }
        };
    }
    private void saveUser(Message message, User user, MessageRepository repository){
        message.setUser(user);
        message.setDate(LocalDate.now());
        repository.save(message);
    }
}
