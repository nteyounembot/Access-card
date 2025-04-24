package com.example.Access.Card.Specification;

import com.example.Access.Card.entities.UniversityMembers;
import org.springframework.data.jpa.domain.Specification;

public class UniversityMembersSpecification {
    public static Specification<UniversityMembers> hasCni(String cni) {
        return (root, query, criteriaBuilder) ->
                cni == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("cni")), "%" + cni.toLowerCase() + "%");
    }
    public static Specification<UniversityMembers> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<UniversityMembers> hasFaculte(String faculte) {
        return (root, query, criteriaBuilder) ->
                faculte == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("telephone")), "%" + faculte.toLowerCase() + "%");
    }
    public static Specification<UniversityMembers> hasTelephone(String telephone) {
        return (root, query, criteriaBuilder) ->
                telephone == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("telephone")), "%" + telephone.toLowerCase() + "%");
    }

    public static Specification<UniversityMembers> hasNiveau(String niveau) {
        return (root, query, criteriaBuilder) ->
                niveau == null ? null : criteriaBuilder.equal(root.get("niveau"), niveau);
    }

    public static Specification<UniversityMembers> isEligible(Boolean eligible) {
        return (root, query, criteriaBuilder) ->
                eligible == null ? null : criteriaBuilder.equal(root.get("eligible"), eligible);
    }

    public static Specification<UniversityMembers> hasMatricule(String matricule) {
        return (root, query, criteriaBuilder) ->
                matricule == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("matricule")), "%" + matricule.toLowerCase() + "%");
    }
}




