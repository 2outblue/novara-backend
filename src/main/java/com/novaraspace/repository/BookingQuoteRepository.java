package com.novaraspace.repository;

import com.novaraspace.model.entity.BookingQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingQuoteRepository extends JpaRepository<BookingQuote, Long> {

    Optional<BookingQuote> findByReference(String reference);
}
