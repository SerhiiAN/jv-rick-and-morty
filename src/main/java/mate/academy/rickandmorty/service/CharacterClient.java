package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CharacterClient {
    private final ObjectMapper objectMapper;
    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    @PostConstruct
    public void getCharactersAndSaveToDatabase() {
        if (characterService.count() > 0) {
            return;
        }
        List<Character> characterList = getCharactersRates();
        characterService.saveAll(characterList);
    }

    public List<Character> getCharactersRates() {
        List<Character> characters = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            String url = "https://rickandmortyapi.com/api/character";
            CharacterResponseDataDto dataDto;
            do {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(url))
                        .build();
                HttpResponse<String> response = httpClient.send(httpRequest,
                        HttpResponse.BodyHandlers.ofString());
                dataDto = objectMapper
                    .readValue(response.body(), CharacterResponseDataDto.class);
                characters.addAll(dataDto.getResults().stream()
                        .map(characterMapper::toModel)
                        .toList());
                url = dataDto.getInfo().next();
            } while (url != null);
            return characters;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
