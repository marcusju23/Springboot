package se.iths.springbootgroupproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.services.MessageService;
import se.iths.springbootgroupproject.services.github.GithubOAuth2UserService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GuestController.class)
class GuestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MessageService messageService;

    @MockBean
    private GithubOAuth2UserService githubOAuth2UserService;

    private MessageAndUsername message1;
    private MessageAndUsername message2;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserName("Olle2k");
        user.setFirstName("Olle");
        user.setLastName("Olsson");
        message1 = new MessageAndUsername(1L, LocalDate.now(), LocalDate.now(), "Hello World", "This is the first message.", "user1");
        message2 = new MessageAndUsername(2L, LocalDate.now(), LocalDate.now(), "Good Morning", "Hope you have a great day!", "user2");
    }

    @Test
    @WithMockUser
    void getPrivateMessages() throws Exception {
        List<MessageAndUsername> msgList = List.of(message1, message2);
        when(messageService.findAllByPrivateMessageIsFalse()).thenReturn(msgList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("Application/Json"),
                        content().string(containsString(message1.messageBody())),
                        content().string(containsString(message2.title())));
    }

    @Test
    @WithMockUser
    void return204IfNoMessages() throws Exception {
        when(messageService.findAllByPrivateMessageIsFalse()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages"))
                .andExpect(
                        status().isNoContent());
    }

    @Test
    @WithMockUser
    void getAllPublicUserMessagesOfUser() throws Exception {
        when(messageService.findAllByUserIdAndPrivateMessageIsFalse(user.getId())).thenReturn(List.of(message1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/users/"+user.getId()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("Application/Json"),
                        content().string(containsString(message1.messageBody())));
    }

    @Test
    @WithMockUser
    void return204IfNoUserMessages() throws Exception {
        when(messageService.findAllByUserIdAndPrivateMessageIsFalse(user.getId())).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/users/"+user.getId()))
                .andExpect(
                        status().isNoContent());
    }
}