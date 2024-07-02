package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterClient;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character management", description = "Endpoints for managing characters")
@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterClient characterClient;
    private final CharacterService characterService;

    @GetMapping("/random")
    public CharacterDto testCharacters() {
        return characterService.findByRandomId();
    }

    @GetMapping("/search")
    public List<CharacterDto> searchByName(@RequestParam("name") String name,
                                           @PageableDefault Pageable pageable) {
        return characterService.findByName(name, pageable);
    }
}
