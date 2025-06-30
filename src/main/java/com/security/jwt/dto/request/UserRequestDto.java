package com.security.jwt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.jwt.validation.annotation.ValidRoles;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

public class UserRequestDto {

    @CPF(message = "O CPF deverá ser válido")
    @NotNull(message = "CPF não pode ser nulo")
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            message = "O CPF deve estar no formato 000.000.000-00")
    @JsonProperty("cpf")
    private String cpf;
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 50, message = "Senha deve ter entre 6 e 50 caracteres")
    private String password;
    @NotNull(message = "Lista de roles não pode ser nula")
    @ValidRoles //Valida a instância de regras.
    private List<String> roles = new ArrayList<>();

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
