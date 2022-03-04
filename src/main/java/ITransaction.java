/* Contrato que obliga a las transacciones a tener el método "process" para que puedan ser procesadas*/
interface ITransaction {
    boolean process();
}
