package com.example.Access.Card.DTO;

import com.example.Access.Card.entities.EnumRole;

public record UniverstyMembersRequestDTO(
        String cni,
         String name,
        String email,
         String telephone,
         String faculte,
         String niveau,
         String matricule,
        String username,
         EnumRole role,
        boolean eligible
) { }
