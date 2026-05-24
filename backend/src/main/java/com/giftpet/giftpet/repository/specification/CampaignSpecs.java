package com.giftpet.giftpet.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.giftpet.giftpet.model.Campaign;

public class CampaignSpecs {

    public static Specification<Campaign> hasNameOrDescription(String search) {
        return (root, query, builder) -> {
            String searchPattern = "%" + search.toLowerCase() + "%";

            return builder.or(
                    builder.like(builder.lower(root.get("name")), searchPattern),
                    builder.like(builder.lower(root.get("description")), searchPattern));
        };
    }
}
