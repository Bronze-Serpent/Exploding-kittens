package com.kittens.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEditRoomDto
{
    Long createdRoomId;
    List<Long> usersId;
}
