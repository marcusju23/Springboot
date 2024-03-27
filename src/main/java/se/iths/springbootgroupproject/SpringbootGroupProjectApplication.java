package se.iths.springbootgroupproject;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.MessageRepository;
import se.iths.springbootgroupproject.repositories.UserRepository;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootGroupProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGroupProjectApplication.class, args);
	}
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	ApplicationRunner databaseInit2(MessageRepository repository, UserRepository userRepository){
		return args -> {
			var result = repository.findByTitle("TempTitle");
			var result2 = repository.findByTitle("En riktig titel");
			var result3 = repository.findByTitle("Privat meddelande titel");
			var result4 = repository.findByTitle("Test 255");
			if (result.isEmpty()) {
				var message = new Message();
				message.setTitle("TempTitle");
				message.setMessageBody("BodyOfTheMessage");
				message.setPrivateMessage(false);
				message.setDate(LocalDate.now());
				var user = new User();
				user.setUserName("LeifGW");
				userRepository.save(user);
				message.setUser(user);
				repository.save(message);
			}
			if (result2.isEmpty()){
				var message = new Message();
				message.setTitle("En riktig titel");
				message.setMessageBody("Riktig riklig text");
				message.setPrivateMessage(false);
				message.setDate(LocalDate.now());
				var user = new User();
				user.setUserName("Ranta Runtiringen");
				userRepository.save(user);
				message.setUser(user);
				repository.save(message);
			}
			if (result3.isEmpty()){
				var message = new Message();
				message.setTitle("Privat meddelande titel");
				message.setMessageBody("Schh hemligt hehehihi");
				message.setPrivateMessage(true);
				message.setDate(LocalDate.now());
				var user = new User();
				user.setUserName("Jame Sbond");
				userRepository.save(user);
				message.setUser(user);
				repository.save(message);
			}
			if (result4.isEmpty()){
				var message = new Message();
				message.setTitle("Test 255");
				message.setMessageBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed faucibus felis ac libero vestibulum, sed lobortis velit sodales. Nulla non risus non enim scelerisque ullamcorper. Suspendisse potenti. Proin euismod dictum velit, id consectetur elit tempor et.");
				message.setPrivateMessage(false);
				message.setDate(LocalDate.now());
				var user = new User();
				user.setUserName("Långe Jaun");
				userRepository.save(user);
				message.setUser(user);
				repository.save(message);
			}
		};
	}
}
