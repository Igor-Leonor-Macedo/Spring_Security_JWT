    package com.security.jwt.validation.annotation;

    import com.security.jwt.validation.validator.ValidRolesValidator;
    import jakarta.validation.Constraint;
    import jakarta.validation.Payload;

    import java.lang.annotation.*;

        @Documented
        @Constraint(validatedBy = ValidRolesValidator.class)
        @Target({ElementType.FIELD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ValidRoles {
            String message() default "Regra de acesso inválida";
            Class<?>[] groups() default {};
            Class<? extends Payload>[] payload() default {};
        }
