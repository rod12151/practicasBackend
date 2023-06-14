package practicas.gestion_personal.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion - Personal",
                version = "1.0",
                description = "Documentation for endpoints in practicas"
        )
)
public class OpenApiConfig {
}
