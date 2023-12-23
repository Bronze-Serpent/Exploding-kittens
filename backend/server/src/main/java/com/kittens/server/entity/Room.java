package com.kittens.server.entity;

import com.kittens.server.common.entity.BaseEntity;
import com.kittens.server.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Room extends BaseEntity<Long>
{
    @OneToOne(mappedBy = "room")
    private GameStateEntity gameState;

    @ManyToMany
    @JoinTable(name = "user_in_room",
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public void addUser(User user)
    {
        users.add(user);
    }
}
