package com.kittens.server.common.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseEntity <K extends Serializable>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;
}
