package se.iths.springbootgroupproject.services.github;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GithubOAuth2UserService extends DefaultOAuth2UserService {

    UserRepository userRepository;

    public GithubOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();
        Optional<User> authenticatedUser = userRepository.findByUserName((String) attributes.get("login"));
        if (authenticatedUser.isEmpty()) {
            User user = createNewUser(attributes);
            userRepository.save(user);
        }
        return oauth2User;
    }

    private User createNewUser(Map<String, Object> attributes) {
        User user = new User();
        user.setUserName((String) attributes.get("login"));
        user.setImage((String) attributes.get("avatar_url"));
        user.setFullName((String) attributes.get("name"));
        user.setEmail((String) attributes.get("email"));
        return user;
    }
}
