package atm;

import atm.accounttypes.Account;
import atm.accounttypes.OnlyCashAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class BankTest {

  Bank bank;

  @BeforeEach
  void initialize() {
    bank = new Bank("Banesco");
  }

  private static Stream<Arguments> accounts() {
    return Stream.of(
        Arguments.of(
            new Account(
                "Jose Perez",
                "01234567890123456789",
                "1234",
                    "joseperez@gmail.com",
                100)),
        Arguments.of(
            new OnlyCashAccount(
                "Maria Mendez",
                "00110011001100110011",
                "0011",
                    "mariamendez@gmail.com",
                1000)));
  }

  @ParameterizedTest
  @MethodSource("accounts")
  void shouldAddBankAccount(Account acc) {
    bank.addAccount(acc);
    System.out.println(acc.getOwnerName());

    Assertions.assertFalse(bank.getAccounts().isEmpty());
    Assertions.assertEquals(1, bank.getAccounts().size());
    Assertions.assertTrue(
        bank.getAccounts().stream()
            .anyMatch(
                account ->
                    account.getOwnerName().equals(acc.getOwnerName())
                        && account.getAccountNumber().equals(acc.getAccountNumber())
                        && account.getPin().equals(acc.getPin())
                        && account.getEmail().equals(acc.getEmail())
                        && account.getBalance() == acc.getBalance()));
  }
}
