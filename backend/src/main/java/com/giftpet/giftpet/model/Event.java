package com.giftpet.giftpet.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank
    @Size(max = 250)
    private String name;

    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIncludeProperties({"id", "name"})
    private Campaign campaign;

    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIncludeProperties("id")
    private User owner;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull
    private BigDecimal goal;

    @NotNull
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "event_image", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    @JsonIncludeProperties("id")
    private List<Image> images;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "event_giftcard", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "giftcard_id"))
    @JsonIgnore
    private List<GiftCard> giftCards;
}
