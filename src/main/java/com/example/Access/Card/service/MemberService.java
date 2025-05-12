package com.example.Access.Card.service;

import com.example.Access.Card.entities.UniversityMembers;
import com.example.Access.Card.repository.UniversityMembersRepository;
import com.example.Access.Card.utile.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final EmailService emailService;
    private final UniversityMembersRepository memberRepository;

    public MemberService(EmailService emailService, UniversityMembersRepository memberRepository) {
        this.emailService = emailService;
        this.memberRepository = memberRepository;
    }

    public void createMemberAndSendQRCode(Long id) {
        Optional<UniversityMembers> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Membre introuvable avec l'ID : " + id);
        }

        UniversityMembers member = optionalMember.get();

        String dataToEncode = String.valueOf(member.getId());

        try {
            byte[] qrImage = QRCodeGenerator.generateQRCodeImage(dataToEncode, 250, 250);
            emailService.sendQRCodeEmail(
                    member.getEmail(),
                    "Votre QR Code",
                    "Voici votre code QR",
                    qrImage
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la génération ou de l'envoi du QR code.");
        }
    }

}
