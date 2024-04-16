package practicas.gestion_personal.utils;

public class IdNotFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE ="el codigo ingresado no existe en la tabla %s";
    public IdNotFoundException(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}
