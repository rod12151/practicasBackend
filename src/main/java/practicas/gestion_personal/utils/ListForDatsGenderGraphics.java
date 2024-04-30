package practicas.gestion_personal.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListForDatsGenderGraphics {
    private List<String> genders = new ArrayList<>();
    private List<Integer> dats = new ArrayList<>();
}
