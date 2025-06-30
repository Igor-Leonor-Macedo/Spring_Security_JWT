    package com.security.jwt.validation.validator;

    import com.security.jwt.validation.annotation.ValidRoles;
    import jakarta.validation.ConstraintValidator;
    import jakarta.validation.ConstraintValidatorContext;

    import java.util.Arrays;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

        public class ValidRolesValidator implements ConstraintValidator<ValidRoles, List<String>> {

            private static final Set<String> VALID_ROLES = new HashSet<>(
                    Arrays.asList("ROLE_ADMIN", "ROLE_USER", "ROLE_MANAGER")
            );
            @Override
            public void initialize(ValidRoles constraintAnnotation) {
                // Inicialização se necessário
            }
            @Override
            public boolean isValid(List<String> roles, ConstraintValidatorContext context) {
                // Se a lista for nula ou vazia, considerar válida (use @NotNull/@NotEmpty se necessário)
                if (roles == null || roles.isEmpty()) {
                    return true;
                }
                // Verificar se todos os roles são válidos
                for (String role : roles) {
                    if (role == null || !VALID_ROLES.contains(role)) {
                        return false;
                    }
                }
                return true;
            }
        }
