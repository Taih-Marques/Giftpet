package com.giftpet.giftpet.config;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.giftpet.giftpet.model.Campaign;
import com.giftpet.giftpet.model.Event;
import com.giftpet.giftpet.model.Image;
import com.giftpet.giftpet.repository.CampaignRepository;
import com.giftpet.giftpet.repository.EventRepository;
import com.giftpet.giftpet.service.GiftCardService;
import com.giftpet.giftpet.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TestDataInitializer {
    private UserService userService;
    private CampaignRepository campaignRepository;
    private EventRepository eventRepository;
    private GiftCardService giftCardService;

    @PostConstruct
    public void initialize() {
        // Seed test data after Spring creates this component.
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
                        BigDecimal.valueOf(3900.00), LocalDate.of(2026, 2, 8), List.of(new Image(streetDogsImage))));

        campaignRepository.saveAll(campaigns);
    }

    public void createEvents() {

        var campaignOpt = campaignRepository.findAll().stream().findFirst();
        if (campaignOpt.isEmpty())
            return;
        var campaign = campaignOpt.get();

        var event1Cards = giftCardService.generateGiftCards(5, BigDecimal.valueOf(30.00));
        var event1 = new Event(null, "Feira de Adoção", campaign, "Feira para adoção de animais resgatados.",
                BigDecimal.valueOf(500.00), LocalDate.now(),
                loadImages("caes-de-rua.jpg"), event1Cards);
        // Keep the bidirectional relation consistent before saving.
        event1Cards.forEach(giftCard -> giftCard.setEvent(event1));

        var event2Cards = giftCardService.generateGiftCards(5, BigDecimal.valueOf(12.00));
        var event2 = new Event(null, "Campanha de Vacinação", campaign, "Vacinação gratuita para cães resgatados.",
                BigDecimal.valueOf(800.00), LocalDate.now().plusDays(10),
                loadImages("caes-de-rua.jpg"), event2Cards);
        event2Cards.forEach(giftCard -> giftCard.setEvent(event2));
        
        var event3Cards = giftCardService.generateGiftCards(5, BigDecimal.valueOf(20.00));
        var event3 = new Event(null, "Arrecadação de Ração", campaign, "Arrecadação de ração para cães resgatados.",
                BigDecimal.valueOf(300.00), LocalDate.now().plusDays(20),
                loadImages("caes-de-rua.jpg"), event3Cards);
        event3Cards.forEach(giftCard -> giftCard.setEvent(event3));

        eventRepository.saveAll(List.of(event1, event2, event3));
    }

    private List<Image> loadImages(String filename) {
        return loadImages(List.of(filename));
    }

    private List<Image> loadImages(List<String> filenames) {
        return filenames.stream()
                .map(this::findAndConvertImage)
                .filter(Objects::nonNull)
                .map(Image::new)
                .collect(Collectors.toList());
    }

    private String findAndConvertImage(String path) {
        try {
            // Store classpath images as Base64 strings for test records.
            ClassPathResource resource = new ClassPathResource("images" + File.separator + path);
            byte[] imageBytes = resource.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            System.err.println(String.format("Image %s not found", path));
            return null;
        }
    }

}
