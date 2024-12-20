package com.projeto.shopee.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaEmailTest {

    @Test
    void testEmailValido() {
        assertTrue(ValidationUtils.isValidEmail("teste@exemplo.com"), "E-mail deve ser válido");
    }

    @Test
    void testEmailInvalido() {
        assertFalse(ValidationUtils.isValidEmail("testeexemplo.com"), "E-mail deve ser inválido");
        assertFalse(ValidationUtils.isValidEmail("teste@exemplo"), "E-mail deve ser inválido");
        assertFalse(ValidationUtils.isValidEmail("teste@.com"), "E-mail deve ser inválido");
    }
}