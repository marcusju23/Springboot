package se.iths.springbootgroupproject.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.iths.springbootgroupproject.configurations.SecurityConfig;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.entities.Message;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.services.LibreTranslateService;
import se.iths.springbootgroupproject.services.MessageService;
import se.iths.springbootgroupproject.services.UserService;
import se.iths.springbootgroupproject.services.github.GithubOAuth2UserService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WebController.class)
@Import(SecurityConfig.class)
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private GithubOAuth2UserService githubOAuth2UserService;
    @MockBean
    private LibreTranslateService libreTranslateService;

    private MessageAndUsername message;
    private MessageAndUsername message2;

    @BeforeEach
    void setUp() {
        message = new MessageAndUsername(1L,
                LocalDate.now(),
                LocalDate.now(),
                "First",
                "First message",
                "FirstUser");
        message2 = new MessageAndUsername(2L,
                LocalDate.now(),
                LocalDate.now(),
                "Second",
                "Second message",
                "SecondUser");
    }

    @Test
    @WithAnonymousUser
    void redirectUnauthenticatedUsersToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/messages"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("http://localhost/oauth2/authorization/github"));
    }

    @Test
    @WithMockUser
    void shouldShowAllMessagesToAuthenticated() throws Exception {
        List<MessageAndUsername> messages = List.of(message, message2);
        when(messageService.findAllMessages(any(Pageable.class))).thenReturn(messages);
        mockMvc.perform(MockMvcRequestBuilders.get("/web/messages"))
                .andExpectAll(
                        status().isOk(),
                        model().attribute("messages", messages),
                        view().name("messages"));
    }

    @Test
    @WithAnonymousUser
    void shouldShowPublicMessagesToUnauthenticatedUser() throws Exception {
        List<MessageAndUsername> messages = List.of(message, message2);
        when(messageService.findAllByPrivateMessageIsFalse(any(Pageable.class))).thenReturn(messages);
        mockMvc.perform(MockMvcRequestBuilders.get("/web/welcome"))
                .andExpectAll(status().isOk(),
                        model().attribute("messages", messages),
                        view().name("welcome"));
    }

    @Test
    void authenticatedUserAccessTheirProfile() throws Exception {
        User user = new User();
        user.setFirstName("Pelle");
        user.setEmail("Pelle@gmail.com");
        user.setUserName("Pelle2k");
        user.setLastName("Pellsson");
        user.setImage("/img.jpg");
        when(userService.findByGitHubId(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/web/myprofile")
                        .with(oauth2Login()))
                .andExpectAll(
                        model().attribute("name", user.getFirstName()+" "+user.getLastName()),
                        model().attribute("email", user.getEmail()),
                        model().attribute("userName", user.getUserName()),
                        model().attribute("profilepic", user.getImage()),
                        status().isOk(),
                        view().name("userprofile"));
    }

    @Test
    @WithAnonymousUser
    void redirectedIfNotAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/myprofile/edit"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("http://localhost/oauth2/authorization/github"));
    }

    @Test
    @WithMockUser
    void returnViewIfAuthenticated() throws Exception {
        when(userService.findByGitHubId(any())).thenReturn(new User());
        mockMvc.perform(MockMvcRequestBuilders.get("/web/myprofile/edit").with(oauth2Login()))
                .andExpectAll(
                        status().isOk(),
                        view().name("edituser"));
    }

    @Test
    @WithMockUser
    void authenticatedUserEditOk() throws Exception {
        when(userService.findByGitHubId(any())).thenReturn(new User());
        mockMvc.perform(post("/web/myprofile/edit").with(csrf())
                        .param("userName", "newUser")
                        .param("firstName", "don")
                        .param("lastName", "derp")
                        .param("email", "new@new.se")
                        .param("image", "/new/new")
                        .with(oauth2Login()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void authenticatedUserEditMessageOkAndContentIsApplied() throws Exception {
        Message message = new Message();
        message.setId(1L);
        message.setTitle("Title");
        message.setMessageBody("My message body");
        User user = new User();
        user.setId(1L);
        message.setUser(user);
        when(userService.findByGitHubId(any())).thenReturn(user);
        when(messageService.findById(any())).thenReturn(message);
        mockMvc.perform(get("/web/myprofile/editmessage").with(oauth2Login())
                        .param("id", message.getId().toString()))
                .andExpectAll(
                        status().isOk(),
                        view().name("editmessage"),
                        result -> {
                            String content = result.getResponse().getContentAsString();
                            assertThat(content).contains("My message body", "Title");
                        });
    }

    @Test
    @WithMockUser
    void shouldRedirectWhenUserWantToCreateMessage() throws Exception {
        mockMvc.perform(get("/web/myprofile/create"))
                .andExpectAll(
                        status().isOk(),
                        view().name("createmessage"));
    }

    @Test
    void shouldRedirectToMyProfileIfCreatedMessage() throws Exception {
        when(userService.findByGitHubId(any())).thenReturn(new User());
        mockMvc.perform(post("/web/myprofile/create").with(csrf())
                        .param("title", "My title")
                        .param("messageBody", "My message")
                        .param("privateMessage", "true")
                        .with(oauth2Login()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/web/myprofile"));
    }

    @Test
    void shouldReturn200okAndViewCreatemessageIfError() throws Exception {
        when(userService.findByGitHubId(any())).thenReturn(new User());
        mockMvc.perform(post("/web/myprofile/create").with(csrf())
                        .param("title", "My title")
                        .with(oauth2Login()))
                .andExpectAll(
                        status().isOk(),
                        view().name("createmessage"));
    }


    @Test
    void shouldRedirectToMyprofile() throws Exception {
        Long id = 1L;
        when(messageService.findById(id)).thenReturn(new Message());
        mockMvc.perform(post("/web/myprofile/editmessage").with(csrf())
                        .param("id", String.valueOf(id))
                        .param("title", "My title")
                        .param("messageBody", "My message")
                        .param("privateMessage", "false")
                        .with(oauth2Login()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/web/myprofile"));
    }

    @Test
    void redirectToEditmessageIfError() throws Exception {
        Long id = 1L;
        when(messageService.findById(id)).thenReturn(new Message());
        mockMvc.perform(post("/web/myprofile/editmessage").with(csrf())
                        .param("id", String.valueOf(id))
                        .param("title", "My title")
                        .param("privateMessage", "false")
                        .with(oauth2Login()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/web/myprofile/editmessage?id=" + id));
    }

    @Test
    void forbiddenActionIfNotAuthenticated() throws Exception {
        mockMvc.perform(post("/web/myprofile/editmessage")
                        .param("id", String.valueOf(1))
                        .param("title", "My title")
                        .param("privateMessage", "false"))
                .andExpect(status().isForbidden());
    }
}