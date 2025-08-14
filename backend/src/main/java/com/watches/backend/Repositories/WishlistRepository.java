package com.watches.backend.Repositories;

import com.watches.backend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
}
