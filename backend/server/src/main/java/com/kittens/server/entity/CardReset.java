package com.kittens.server.entity;

import com.kittens.logic.card.CardName;
import com.kittens.server.common.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class CardReset extends BaseEntity<Long>
{
    @OneToOne(mappedBy = "cardReset")
    private GameStateEntity gameState;

    @Type(
            value = EnumArrayType.class,
            parameters = @org.hibernate.annotations.Parameter(
                    name = "sql_array_type",
                    value = "card_name_type"
            )
    )
    private CardName[] value;

    public CardReset(Long id, CardName[] value)
    {
        super(id);
        this.value = value;
    }
}
