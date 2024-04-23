package practicas.gestion_personal.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ListForDatsGraphics {
    private List<String> codes = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<Integer> dats = new ArrayList<>();
}
