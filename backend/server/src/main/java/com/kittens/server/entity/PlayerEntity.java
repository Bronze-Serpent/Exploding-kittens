package com.kittens.server.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "player")
public class PlayerEntity extends BaseEntity<Integer>
{
    @OneToMany
    @JoinTable(name = "player_card",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "card_info_id"))
    private List<CardInfo> cards = new ArrayList<>();

}
