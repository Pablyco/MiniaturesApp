package com.example.miniatures.dto.miniatureClient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public abstract class MiniatureClientBaseDTO {

    @NotBlank(message = "Client name cant be empty")
    String name;

    @NotBlank(message = "Client email cant be empty")
    String email;

    @NotNull(message = "Phone number is obligatory")
    @Positive(message = "Phone number should be greater than zero '0' ")
    Integer phoneNumber;
}
