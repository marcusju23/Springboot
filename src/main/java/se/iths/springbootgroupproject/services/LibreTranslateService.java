package se.iths.springbootgroupproject.services;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class LibreTranslateService {
    private final RestClient restClient;

    public LibreTranslateService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retryable
    public boolean detectMessageLanguage(String message) {
        String isEnOrSv = Objects.requireNonNull(restClient.post()
                .uri("http://localhost:5000/detect")
                .contentType(APPLICATION_JSON)
                .accept()
                .body(String.format("{\"q\":\"%s\"}", message))
                .retrieve()
                .body(String.class));
        return !isEnOrSv.contains("\"en\"");
    }

    @Retryable
    public String translateMessage(String message) {
        String sourceLanguage = "en";
        String targetLanguage = "sv";

        if(detectMessageLanguage(message)) {
            sourceLanguage = "sv";
            targetLanguage = "en";
        }

        String jsonString = String.format("{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}", message, sourceLanguage, targetLanguage);

        return Objects.requireNonNull(restClient.post()
                        .uri("http://localhost:5000/translate")
                        .contentType(APPLICATION_JSON)
                        .accept()
                        .body(jsonString)
                        .retrieve()
                        .body(String.class))
                .split(":")[1]
                .replaceAll("[{\"}]","");
    }

}
