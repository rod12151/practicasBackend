package practicas.gestion_personal.utils;

public class ServiceWithBoss extends RuntimeException{
    private static final String ERROR_MESSAGE ="El servicio con codigo %s ; ya cuenta con un jefe; datos del jefe actual: %s";
    public ServiceWithBoss(String codeService,String datosJefe){
        super(String.format(ERROR_MESSAGE,codeService,datosJefe));
    }
}
