package atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "01234567890123456789",
        "11111111111111111111",
        "00110011001100110011",
        "01230123012301230123"
      })
  void validAccountNumbersReturnTrue(String accountNumber) {
    Assertions.assertTrue(InputValidator.checkAccountNumber(accountNumber));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "",
        "0123456789",
        "0123d567890123456789",
        "01234567890123*56789",
        "abcdefghijklmnopqrst",
        "0123456789012345678901234567890123456789"
      })
  void invalidAccountNumbersReturnFalse(String accountNumber) {
    Assertions.assertFalse(InputValidator.checkAccountNumber(accountNumber));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0000", "1234", "1111", "9999", "9876"})
  void validPinNumbersReturnTrue(String pinNumber) {
    Assertions.assertTrue(InputValidator.checkPinNumber(pinNumber));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "abcd", "11a1", "99999", "987"})
  void invalidPinNumbersReturnFalse(String pinNumber) {
    Assertions.assertFalse(InputValidator.checkPinNumber(pinNumber));
  }

  @Test
  void checkValidChoice() {
    Assertions.assertTrue(InputValidator.checkValidChoice(1, 3));
    Assertions.assertTrue(InputValidator.checkValidChoice(2, 3));
    Assertions.assertTrue(InputValidator.checkValidChoice(3, 3));
    Assertions.assertFalse(InputValidator.checkValidChoice(0, 3));
    Assertions.assertFalse(InputValidator.checkValidChoice(4, 3));
    Assertions.assertFalse(InputValidator.checkValidChoice(-1, 3));
  }

  @Test
  void checkValidAmount() {
    Assertions.assertTrue(InputValidator.checkValidAmount(20000));
    Assertions.assertTrue(InputValidator.checkValidAmount(20));
    Assertions.assertTrue(InputValidator.checkValidAmount(1.8));
    Assertions.assertFalse(InputValidator.checkValidAmount(0));
    Assertions.assertFalse(InputValidator.checkValidAmount(-50));
    Assertions.assertFalse(InputValidator.checkValidAmount(-20000));
  }
}
