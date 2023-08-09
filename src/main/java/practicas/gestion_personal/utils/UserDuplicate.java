package practicas.gestion_personal.utils;

public class UserDuplicate extends RuntimeException {
    private static final String ERROR_MESSAGE ="El usuario con DNI: %s, %s ";
    public UserDuplicate(String dni,String message){
        super(String.format(ERROR_MESSAGE,dni,message));
    }
}

