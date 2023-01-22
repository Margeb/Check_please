package pl.margeb.checkplease.person.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PersonDto {

    private UUID id;

    private String name;

    private BigDecimal balance;

    private UUID groupId;
}
