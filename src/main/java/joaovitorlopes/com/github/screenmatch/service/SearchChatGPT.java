package joaovitorlopes.com.github.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class SearchChatGPT {

    public static String getTranslation(String text) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));
        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();
        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
