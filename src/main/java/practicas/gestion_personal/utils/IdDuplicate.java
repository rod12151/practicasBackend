package practicas.gestion_personal.utils;

public class IdDuplicate extends RuntimeException{
    private static final String ERROR_MESSAGE ="El  %s insertado ya existe en la base de datos";
    public IdDuplicate(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}
