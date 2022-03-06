package ATM.Transactions;

import ATM.ATM;
import ATM.ITransaction;

public class ChangePinTransaction implements ITransaction {
    private ATM atm;

    public ChangePinTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        //Pide ingresar pin
        atm.getDisplay().askChangePin();
        //almacenamos el  pin de la session actual
        String pin=atm.getSession().getActiveAccount().getPin();
        //almacenamos el pin ingresado
        String PIN = atm.getKeyboard().getPin();

        if (PIN.equals(pin) == PIN.equals(PIN)){
            atm.getDisplay().changePin();
            //almacenamos el pin nuevo ingresado
            String PINnew= atm.getKeyboard().getPin();
            if (validPin(PINnew)){
                // asignamos pin por medio de setPin
                atm.getSession().getActiveAccount().setPin(PINnew);
                atm.getDisplay().okChangePin();
                //Imprimir mensaje de cambio de pin
                System.out.println("su nuevo pin es: "+atm.getSession().getActiveAccount().getPin());

            }
            return false;
        }
        return false;

    }


    public boolean validPin(String pin){
        return pin.length() >= 3 && pin.length() <= 4;
    }

}
