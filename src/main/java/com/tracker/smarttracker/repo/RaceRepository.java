package com.tracker.smarttracker.repo;

import com.tracker.smarttracker.model.Race;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RaceRepository extends CrudRepository<Race, Long> {
    Race findByIsActiveTrueAndUserId(Long userId);

    List<Race> findByUserIdAndStartDatetimeAfterAndFinishDatetimeBefore(long userId, LocalDateTime orElse, LocalDateTime orElse1);
}
