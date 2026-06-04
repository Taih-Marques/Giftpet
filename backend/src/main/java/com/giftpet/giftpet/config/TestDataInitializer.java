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

        String streetDogsImage = findAndConvertImage("caes-de-rua.jpg");

        var campaigns = List.of(
            new Campaign(null, "Ração para cães resgatados",
                "Campanha para comprar ração e alimentar cães resgatados das ruas durante o próximo mês.",
                BigDecimal.valueOf(1800.00), LocalDate.of(2025, 11, 12), List.of(new Image(streetDogsImage))),
            new Campaign(null, "Vacinação dos cães do abrigo",
                "Ajude a garantir vacinas essenciais para cães acolhidos temporariamente antes da adoção.",
                BigDecimal.valueOf(2450.00), LocalDate.of(2025, 11, 28), List.of(new Image(streetDogsImage))),
            new Campaign(null, "Castração comunitária",
                "Mutirão de castração para reduzir o abandono e melhorar a saúde dos cães em situação de rua.",
                BigDecimal.valueOf(5200.00), LocalDate.of(2025, 12, 5), List.of(new Image(streetDogsImage))),
            new Campaign(null, "Tratamento de ferimentos",
                "Arrecadação para consultas, curativos, antibióticos e exames de cães encontrados feridos.",
                BigDecimal.valueOf(3100.00), LocalDate.of(2025, 12, 18), List.of(new Image(streetDogsImage))),
            new Campaign(null, "Construção de baias seguras",
                "Compra de materiais para montar baias limpas, cobertas e seguras para cães recém-resgatados.",
                BigDecimal.valueOf(6800.00), LocalDate.of(2026, 1, 10), List.of(new Image(streetDogsImage))),
            new Campaign(null, "Lar temporário solidário",
                "Apoio com ração, medicamentos e transporte para voluntários que oferecem lar temporário.",
                BigDecimal.valueOf(2700.00), LocalDate.of(2026, 1, 22), List.of(new Image(streetDogsImage))),
            new Campaign(null, "Feira de adoção responsável",
                "Campanha para organizar uma feira de adoção com triagem veterinária, divulgação e estrutura básica.",
                BigDecimal.valueOf(3900.00), LocalDate.of(2026, 2, 8), List.of(new Image(streetDogsImage)))
        );

        campaignRepository.saveAll(campaigns);
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
