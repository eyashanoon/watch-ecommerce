package com.watches.backend.Repositories;

import com.watches.backend.model.SavedCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedCardRepository extends JpaRepository<SavedCard,Long> {
}
