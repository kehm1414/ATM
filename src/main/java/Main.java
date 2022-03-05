public class Main {
    public static void main(String[] args) {

        // Creamos el banco y algunas cuentas de prueba
        Bank bank = new Bank("Mercantil");
        bank.addAccount(
                new Account(
                        "Kevin Hernandez",
                        "01234567890123456789",
                        "1234",
                        "Client",
                        20000)
        );
        bank.addAccount(
                new OnlyCashAccount(
                        "Jose Perez",
                        "00110011001100110011",
                        "0011",
                        "OnlyCashClient",
                        10000)
        );

        // Inicializamos nuestro cajero
        ATM atm = new ATM("1", bank, "8888",1000000);
        atm.start();

    }
}
