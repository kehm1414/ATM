package ATM;

/* Contrato que obliga a las transacciones a tener el método "process" para que puedan ser procesadas*/
public interface ITransaction {
    boolean process();
}
