package practicas.gestion_personal.utils;

public class UserHaveRoleException extends RuntimeException {
    private static final String ERROR_MESSAGE ="El usuario con Dni : %s, ya es jefe en otra Area ";
    public UserHaveRoleException(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}

