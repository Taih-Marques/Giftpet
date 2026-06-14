package com.giftpet.giftpet.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.giftpet.giftpet.model.GiftCard;

public class GiftCardSpecs {

    public static Specification<GiftCard> isValid(String code) {
        return (root, query, builder) -> {
            if (code == null || code.isBlank()) {
                return builder.disjunction();
            }

            return builder.and(
                    builder.equal(root.get("code"), code),
                    builder.isNull(root.get("dateUsed")));
        };
    }
}
