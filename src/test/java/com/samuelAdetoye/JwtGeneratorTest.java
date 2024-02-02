package com.samuelAdetoye;

import org.junit.jupiter.api.Test;

import static com.samuelAdetoye.UserValidatorImpl.isUsernameValid;
import static org.junit.jupiter.api.Assertions.*;

class JwtGeneratorTest {

    @Test
    public void testValidToken() {
        String username = "Graceman";
        if (isUsernameValid(username)) {
            String validJwt = JwtGenerator.generateJwt(username);
            String verificationMessage = JwtGenerator.verifyToken(validJwt);
            assertEquals("Verification pass", verificationMessage);
        } else {
            fail("Username not valid: " + username);

        }
    }

    @Test
    public void testInvalidToken() {
        String invalidJwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJHcmFjZW1hbiIsImlhdCI6MTYyMzIwNzYwMCwiZXhwIjoxNjIzMjE0MDAwfQ.";
        String verificationMessage = JwtGenerator.verifyToken(invalidJwt);
        assertEquals("Verification fails", verificationMessage);
    }
}
