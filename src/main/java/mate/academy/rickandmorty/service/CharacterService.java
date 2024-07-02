package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    void saveAll(List<Character> characterList);

    Long count();

    CharacterDto findByRandomId();

    List<CharacterDto> findByName(String name, Pageable pageable);
}
