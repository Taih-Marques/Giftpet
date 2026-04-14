package com.giftpet.giftpet.config;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.giftpet.giftpet.model.Campaign;
import com.giftpet.giftpet.model.Event;
import com.giftpet.giftpet.model.Image;
import com.giftpet.giftpet.repository.CampaignRepository;
import com.giftpet.giftpet.repository.EventRepository;
import com.giftpet.giftpet.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TestDataInitializer {
    private UserService userService;
    private CampaignRepository campaignRepository;
    private EventRepository eventRepository; // adicionar injeção do EventRepository

    @PostConstruct
    public void initialize() {
        createUsers();
        createCampaigns();
        createEvents();
    }

    public void createUsers() {
        userService.createUser("Admin", "admin@giftpet.org", "123", true);
        userService.createUser("Usuario", "usuario@exemplo.com", "123", false);
    }

    public void createCampaigns() {

        String campaignDescription = """
            💔 Ajude o Thor a Voltar a Respirar sem Dor

            Olá, meu nome é Carolina, e venho pedir sua ajuda para o meu cachorro Thor, um vira-lata de 5 anos cheio de energia e amor.
            Há algumas semanas, ele começou a ter dificuldade para respirar e ficou muito abatido. Depois de vários exames, descobrimos que ele tem um problema torácico grave, que está comprimindo o pulmão e dificultando a respiração.

            O veterinário explicou que o único tratamento possível é uma cirurgia torácica corretiva, orçada em R$ 2.000.
            Infelizmente, essa quantia está além do que conseguimos arcar no momento — e cada dia é mais difícil ver o Thor sofrendo sem poder correr e brincar como antes.

            Por isso, estamos pedindo sua ajuda 🙏
            Qualquer contribuição, por menor que seja, fará uma enorme diferença para que o Thor possa fazer a cirurgia e voltar a ter qualidade de vida.

            💰 Meta: R$ 2.000
            🐾 Destino dos valores: cirurgia torácica, internação e medicamentos pós-operatórios
            📅 Urgência: o procedimento precisa ser realizado nas próximas semanas

            Thor é parte da nossa família, e ver ele assim parte o coração.
            Com sua ajuda, acreditamos que ele logo estará de volta, correndo feliz pelo quintal. ❤️

            Obrigado de coração a todos que puderem colaborar ou compartilhar.
            """;

        var images = List.of(new Image(findAndConvertImage("thor-campaign.png")));
        var campaign = new Campaign(null, "Castração do Thor", campaignDescription, BigDecimal.valueOf(2059.44), LocalDate.of(2025, 12, 24), images);
        campaignRepository.save(campaign);
    }

    public void createEvents() {
       
        var campaignOpt = campaignRepository.findAll().stream().findFirst();
        if (campaignOpt.isEmpty()) return;
        var campaign = campaignOpt.get();

        var event = new Event(
            null,
            "Feira de Adoção",
            campaign,
            "Feira para adoção de animais resgatados.",
            30,
            BigDecimal.valueOf(500.00),
            LocalDate.of(2024, 11, 10),
            List.of(new Image(findAndConvertImage("caes-de-rua.jpg")))
        );

        eventRepository.saveAll(List.of(event));
    }

    private String findAndConvertImage(String path) {
        try {
            ClassPathResource resource = new ClassPathResource("images" + File.separator + path);
            byte[] imageBytes = resource.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            System.err.println(String.format("Image %s not found", path));
            return null;
        }
    }

}
