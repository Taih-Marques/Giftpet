package com.giftpet.giftpet.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private String name;

    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Campaign campaign;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull
    private Integer giftCount;

    @NotNull
    private BigDecimal goal;

    @NotNull
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "event_image", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    @JsonIgnoreProperties("content")
    private List<Image> images;
}
