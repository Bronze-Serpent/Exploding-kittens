package com.kittens.server.entity;

import com.kittens.logic.card.CardName;
import com.kittens.server.common.entity.BaseEntity;
import com.kittens.server.user.entity.User;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "", callSuper = true)
@Entity
@Table(name = "player")
public class PlayerEntity extends BaseEntity<Long>
{
    @Type(
            value = EnumArrayType.class,
            parameters = @org.hibernate.annotations.Parameter(
                    name = "sql_array_type",
                    value = "card_name_type"
            )
    )
    private CardName[] cards;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    private Room room;

    public PlayerEntity(Room room, User user, CardName[] cards, Long id)
    {
        super(id);
        this.room = room;
        this.user = user;
        this.cards = cards;
    }
}
