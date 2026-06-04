package com.giftpet.giftpet.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.GiftCard;
import com.giftpet.giftpet.repository.GiftCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiftCardService {
    // do not use 'O', '0', 'I', '1', 'L' to avoid typos and confusion
    private static final String ALLOWED_CHARS = "23456789ABCDEFGHJKMNPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final GiftCardRepository giftCardRepository;

    public List<GiftCard> generateGiftCards(Integer quantity, BigDecimal suggestedAmount) {
        if (quantity == null || quantity <= 0) {
            return List.of(); // No gift cards to generate
        }

        var codes = generateNonDuplicateCodes(quantity, 12, 4);

        return codes.stream().map((code) -> {
            GiftCard gc = new GiftCard();
            gc.setCode(code);
            gc.setSuggestedAmount(suggestedAmount);
            return gc;
        }).toList();
    }

    private List<String> generateNonDuplicateCodes(int quantity, int codeLength, int blockLength) {
        List<String> availableCodes = new ArrayList<>();

        while (availableCodes.size() < quantity) {
            int remainingQuantity = quantity - availableCodes.size();
            List<String> candidateCodes = generateCodes(remainingQuantity, codeLength, blockLength);
            Set<String> existingCodes = new HashSet<>(giftCardRepository.findExistingCodes(candidateCodes));

            candidateCodes.stream()
                    .filter(code -> !existingCodes.contains(code))
                    .filter(code -> !availableCodes.contains(code))
                    .forEach(availableCodes::add);
        }

        return availableCodes;
    }

    private List<String> generateCodes(int quantity, int codeLength, int blockLength) {
        return Stream.generate(() -> "GP" + generateCode(codeLength, blockLength))
                .limit(quantity)
                .toList();
    }

    private String generateCode(int length, int blockLength) {
        String base = RANDOM.ints(length, 0, ALLOWED_CHARS.length())
                .mapToObj(ALLOWED_CHARS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        // format as groups like XXXX-XXXX-XXXX
        return base.replaceAll("(.{" + blockLength + "})", "$1-").replaceAll("-$", "");
    }
}
