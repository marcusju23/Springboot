package se.iths.springbootgroupproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

@ContextConfiguration(classes = {LibreTranslateService.class})
@ExtendWith(SpringExtension.class)
class LibreTranslateServiceTest {

    @Autowired
    private LibreTranslateService libreTranslateService;

    @MockBean
    private RestClient restClient;

    private RestClient.RequestBodyUriSpec requestBodyUriSpec;
    private RestClient.ResponseSpec responseSpec;
    private RestClient.RequestBodySpec requestBodySpec, requestBodySpec2,
                                       requestBodySpec3, requestBodySpec4;

    @BeforeEach
    void setUp() {
        requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        responseSpec = mock(RestClient.ResponseSpec.class);

        requestBodySpec = mock(RestClient.RequestBodySpec.class);
        requestBodySpec2 = mock(RestClient.RequestBodySpec.class);
        requestBodySpec3 = mock(RestClient.RequestBodySpec.class);
        requestBodySpec4 = mock(RestClient.RequestBodySpec.class);
    }

    @Test
    @DisplayName("Detect message language with valid text")
    void detectMessageLanguage_ValidText() {
        when(responseSpec.body(Mockito.<Class<String>>any())).thenReturn("Hello this is a text");
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(requestBodySpec2.body(Mockito.<Object>any())).thenReturn(requestBodySpec);
        when(requestBodySpec3.accept(isA(MediaType[].class))).thenReturn(requestBodySpec2);
        when(requestBodySpec4.contentType(Mockito.<MediaType>any())).thenReturn(requestBodySpec3);
        when(requestBodyUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec4);
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        boolean actualDetectMessageLanguageResult = libreTranslateService.detectMessageLanguage("Text");

        verify(restClient).post();
        verify(requestBodySpec2).body(isA(Object.class));
        verify(requestBodySpec4).contentType(isA(MediaType.class));
        verify(requestBodySpec3).accept(isA(MediaType[].class));
        verify(requestBodySpec).retrieve();
        verify(responseSpec).body(isA(Class.class));
        verify(requestBodyUriSpec).uri(eq("http://localhost:5000/detect"), isA(Object[].class));
        assertTrue(actualDetectMessageLanguageResult);
    }

    @Test
    @DisplayName("Detect message language with invalid text")
    void detectMessageLanguage_InvalidText() {
        when(responseSpec.body(Mockito.<Class<String>>any())).thenReturn("\"en\"");
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(requestBodySpec2.body(Mockito.<Object>any())).thenReturn(requestBodySpec);
        when(requestBodySpec3.accept(isA(MediaType[].class))).thenReturn(requestBodySpec2);
        when(requestBodySpec4.contentType(Mockito.<MediaType>any())).thenReturn(requestBodySpec3);
        when(requestBodyUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec4);
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        boolean actualDetectMessageLanguageResult = libreTranslateService.detectMessageLanguage("Text");

        verify(restClient).post();
        verify(requestBodySpec2).body(isA(Object.class));
        verify(requestBodySpec4).contentType(isA(MediaType.class));
        verify(requestBodySpec3).accept(isA(MediaType[].class));
        verify(requestBodySpec).retrieve();
        verify(responseSpec).body(isA(Class.class));
        verify(requestBodyUriSpec).uri(eq("http://localhost:5000/detect"), isA(Object[].class));
        assertFalse(actualDetectMessageLanguageResult);
    }

    @Test
    @DisplayName("Translate message with valid text")
    void translateMessage_ValidText() {
        when(responseSpec.body(Mockito.<Class<String>>any())).thenReturn("http://localhost:5000/detect");
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(requestBodySpec2.body(Mockito.<Object>any())).thenReturn(requestBodySpec);
        when(requestBodySpec3.accept(isA(MediaType[].class))).thenReturn(requestBodySpec2);
        when(requestBodySpec4.contentType(Mockito.<MediaType>any())).thenReturn(requestBodySpec3);
        when(requestBodyUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec4);
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        String actualTranslateMessageResult = libreTranslateService.translateMessage("Text");

        verify(restClient, atLeast(1)).post();
        verify(requestBodySpec2, atLeast(1)).body(Mockito.<Object>any());
        verify(requestBodySpec4, atLeast(1)).contentType(isA(MediaType.class));
        verify(requestBodySpec3, atLeast(1)).accept(isA(MediaType[].class));
        verify(requestBodySpec, atLeast(1)).retrieve();
        verify(responseSpec, atLeast(1)).body(isA(Class.class));
        verify(requestBodyUriSpec, atLeast(1)).uri(Mockito.<String>any(), isA(Object[].class));
        assertEquals("//localhost", actualTranslateMessageResult);
    }

    @Test
    @DisplayName("Translate message with invalid text")
    void translateMessage_InvalidText() {
        when(responseSpec.body(Mockito.<Class<String>>any())).thenReturn("{\"q\":\"%s\"}");
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(requestBodySpec2.body(Mockito.<Object>any())).thenReturn(requestBodySpec);
        when(requestBodySpec3.accept(isA(MediaType[].class))).thenReturn(requestBodySpec2);
        when(requestBodySpec4.contentType(Mockito.<MediaType>any())).thenReturn(requestBodySpec3);
        when(requestBodyUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec4);
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        String actualTranslateMessageResult = libreTranslateService.translateMessage("Text");

        verify(restClient, atLeast(1)).post();
        verify(requestBodySpec2, atLeast(1)).body(Mockito.<Object>any());
        verify(requestBodySpec4, atLeast(1)).contentType(isA(MediaType.class));
        verify(requestBodySpec3, atLeast(1)).accept(isA(MediaType[].class));
        verify(requestBodySpec, atLeast(1)).retrieve();
        verify(responseSpec, atLeast(1)).body(isA(Class.class));
        verify(requestBodyUriSpec, atLeast(1)).uri(Mockito.<String>any(), isA(Object[].class));
        assertEquals("%s", actualTranslateMessageResult);
    }

    @Test
    @DisplayName("Translate message with source and target language")
    void translateMessage_SourceAndTargetLanguage() {
        when(responseSpec.body(Mockito.<Class<String>>any()))
                .thenReturn("{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}");
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(requestBodySpec2.body(Mockito.<Object>any())).thenReturn(requestBodySpec);
        when(requestBodySpec3.accept(isA(MediaType[].class))).thenReturn(requestBodySpec2);
        when(requestBodySpec4.contentType(Mockito.<MediaType>any())).thenReturn(requestBodySpec3);
        when(requestBodyUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec4);
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        String actualTranslateMessageResult = libreTranslateService.translateMessage("Text");

        verify(restClient, atLeast(1)).post();
        verify(requestBodySpec2, atLeast(1)).body(Mockito.<Object>any());
        verify(requestBodySpec4, atLeast(1)).contentType(isA(MediaType.class));
        verify(requestBodySpec3, atLeast(1)).accept(isA(MediaType[].class));
        verify(requestBodySpec, atLeast(1)).retrieve();
        verify(responseSpec, atLeast(1)).body(isA(Class.class));
        verify(requestBodyUriSpec, atLeast(1)).uri(Mockito.<String>any(), isA(Object[].class));
        assertEquals("%s,source", actualTranslateMessageResult);
    }

}