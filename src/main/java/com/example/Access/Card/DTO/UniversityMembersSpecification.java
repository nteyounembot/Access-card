package com.example.Access.Card.DTO;

import com.example.Access.Card.entities.UniversityMembers;
import org.springframework.data.jpa.domain.Specification;


public class UniversityMembersSpecification {

    public static Specification<UniversityMembers> matchesQuery(String query) {
        return (root, queryObj, cb) -> {
            if (query == null || query.trim().isEmpty()) return null;

            String likeQuery = "%" + query.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), likeQuery),
                    cb.like(cb.lower(root.get("cni")), likeQuery),
                    cb.like(cb.lower(root.get("email")), likeQuery),
                    cb.like(cb.lower(root.get("telephone")), likeQuery),
                    cb.like(cb.lower(root.get("faculte")), likeQuery),
                    cb.like(cb.lower(root.get("niveau")), likeQuery),
                    cb.like(cb.lower(root.get("matricule")), likeQuery)
            );
        };
    }

    // Tu peux laisser ici d'autres spécifications comme hasName(), hasCni(), etc. si besoin
}
