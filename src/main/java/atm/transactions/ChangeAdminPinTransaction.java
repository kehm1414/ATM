package atm.transactions;

import atm.ATM;

public class ChangeAdminPinTransaction implements ITransaction {

    private ATM atm;

    public ChangeAdminPinTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        String adminPin = atm.getAdminPin();

        // Se le pide a administrador colocar el pin antiguo, tiene 3 intentos, si falla se le sacará de sesión
        int tries = 3;

        while(tries > 0){
            // Pide ingresar pin
            atm.getDisplay().askChangePin();

            // almacenamos el pin ingresado
            String oldPin = atm.getKeyboard().getPin();

            // validamos que el pin = al de la session actual
            if(oldPin.equals(adminPin)){
                break;
            }

            tries--;
            atm.getDisplay().pinDoesntMatch(tries);
        }

        // Si el usuario no introduce bien su antigua contraseña en los tres intentos, sacar de sesión
        if(tries == 0) {
            atm.getDisplay().pinForgottenLogout();
            atm.getSession().logout();
            return false;
        }

        // pedimos ingresar nuevo pin
        atm.getDisplay().changePin();
        // almacenamos el pin nuevo ingresado
        String newPin = atm.getKeyboard().getPin();

        // asignamos pin por medio de setPin
        atm.setAdminPin(newPin);
        atm.getDisplay().okChangePin();
        // Imprimir mensaje de cambio de pin
        System.out.println("su nuevo pin es: " + atm.getAdminPin());

        return true;
    }
}
