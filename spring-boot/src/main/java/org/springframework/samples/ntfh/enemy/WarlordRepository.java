package org.springframework.samples.ntfh.enemy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WarlordRepository extends CrudRepository<Warlord, Integer> {
}
