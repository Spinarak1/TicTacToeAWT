import sun.plugin2.message.Message;

import java.security.MessageDigest;

public class InvalidStateFormatException extends Exception {
    public InvalidStateFormatException(){
        super("Nieprawidłowy format stanu gry");
    }
}
