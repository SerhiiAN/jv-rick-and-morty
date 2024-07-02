package mate.academy.rickandmorty.dto.internal;

public record CharacterDto(
        Long id,
        String externalId,
        String name,
        String status,
        String gender
){
}
