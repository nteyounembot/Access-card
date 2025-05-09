package com.example.Access.Card.DTO;

import com.example.Access.Card.entities.EnumRole;

import java.time.LocalDateTime;

public record UniversityMembersResponseDTO(
        Long id,
        String cni,
        String name,
        String email,
        String telephone,
        String faculte,
        String niveau,
        String matricule,
        String username,
        EnumRole role,
        boolean gardien,
        boolean eligible,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
