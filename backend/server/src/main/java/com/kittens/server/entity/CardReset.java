package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
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

    @Type(StringArrayType.class)
    private String[] value;

    public CardReset(Long id, String[] value)
    {
        super(id);
        this.value = value;
    }
}
