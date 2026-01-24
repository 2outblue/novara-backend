package com.novaraspace.component;

import com.novaraspace.model.exception.BookingException;
import com.novaraspace.repository.BookingRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class BookingReferenceGenerator {

    private final String ALPHA_NUM_CHARS = "ABCDEFG1HIJ2KL3MN4OP5QR6STU7VW8XYZ9";
    private final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String DIGITS = "123456789";
    private final int CODE_LENGTH = 6;
    private final Random random = new SecureRandom();
    private final BookingRepository bookingRepository;

    public BookingReferenceGenerator(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String generateUniqueReference() {
        for (int i = 0; i < 10; i++) {
            String reference = generateReference();
            if (!bookingRepository.existsByReference(reference)) {
                return reference;
            }
        }
        throw BookingException.creationFailed("Reference generation error");
    }

    private String generateReference() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);

        int firstCharIndex = random.nextInt(LETTERS.length());
        int lastCharIndex = random.nextInt(LETTERS.length());
        int fourthCharIndex = random.nextInt(DIGITS.length());

        sb.append(LETTERS.charAt(firstCharIndex));
        for (int i = 0; i < CODE_LENGTH - 2; i++) {
            if (i == 2) {
                sb.append(DIGITS.charAt(fourthCharIndex));
                continue;
            }
            int index = random.nextInt(ALPHA_NUM_CHARS.length());
            sb.append(ALPHA_NUM_CHARS.charAt(index));
        }
        sb.append(LETTERS.charAt(lastCharIndex));
        return sb.toString();
    }
}
