package org.springframework.ntfh.character;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.ntfh.entity.character.Character;
import org.springframework.ntfh.entity.character.CharacterGenderEnum;
import org.springframework.ntfh.entity.character.CharacterService;
import org.springframework.ntfh.entity.character.CharacterTypeEnum;
import org.springframework.ntfh.entity.turn.concretestates.EnemyState;
import org.springframework.ntfh.entity.turn.concretestates.MarketState;
import org.springframework.ntfh.entity.turn.concretestates.PlayerState;
import org.springframework.ntfh.entity.turn.concretestates.RefreshState;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@Import({ BCryptPasswordEncoder.class, PlayerState.class, MarketState.class, EnemyState.class, RefreshState.class })
public class CharacterServiceTest {

    @Autowired
    private CharacterService characterService;

    @Test
    public void testfindById() {
        Character tester = this.characterService.findCharacterById(2).get();
        assertEquals(CharacterTypeEnum.RANGER, tester.getCharacterTypeEnum());
        assertEquals(CharacterGenderEnum.FEMALE, tester.getCharacterGenderEnum());
    }

}
