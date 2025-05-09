package com.example.Access.Card.Mapper;

import com.example.Access.Card.DTO.UniversityMembersResponseDTO;
import com.example.Access.Card.entities.UniversityMembers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UniversityMembersMapper implements Function<UniversityMembers, UniversityMembersResponseDTO> {

    @Override
    public UniversityMembersResponseDTO apply(UniversityMembers member) {
        var utilisateur = member.getUtilisateur();

        return new UniversityMembersResponseDTO(
                member.getId(),
                member.getCni(),
                member.getName(),
                member.getEmail(),
                member.getTelephone(),
                member.getFaculte(),
                member.getNiveau(),
                member.getMatricule(),

                utilisateur != null ? utilisateur.getUsername() : null,
                utilisateur != null ? utilisateur.getRole() : null,
                member.isGardien(),
                member.isEligible(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    public static List<UniversityMembersResponseDTO> toDtoList(List<UniversityMembers> membersList) {
        List<UniversityMembersResponseDTO> dtoList = new ArrayList<>();
        UniversityMembersMapper mapper = new UniversityMembersMapper();
        for (UniversityMembers member : membersList) {
            dtoList.add(mapper.apply(member));
        }
        return dtoList;
    }
}
