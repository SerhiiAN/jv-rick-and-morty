package mate.academy.rickandmorty.service;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.exception.DataProcessingException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public void saveAll(List<Character> characterList) {
        characterRepository.saveAll(characterList);
    }

    @Override
    public Long count() {
        return characterRepository.count();
    }

    @Override
    public CharacterDto findByRandomId() {
        Random random = new Random();
        Character character = characterRepository.findById(random.nextLong(826)).orElseThrow(
                () -> new DataProcessingException("Cannot find character by random id"));
        return characterMapper.toDto(character);
    }

    @Override
    public List<CharacterDto> findByName(String name, Pageable pageable) {
        return characterRepository.findCharactersByNameContainingIgnoreCase(name, pageable)
                .stream()
                .map(characterMapper::toDto)
                .toList();
    }
}
