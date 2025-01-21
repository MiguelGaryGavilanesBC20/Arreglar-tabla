package Menu_P;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipoJugadorMap {
    private Map<String, List<Jugador>> equipoJugadoresMap;

    public EquipoJugadorMap() {
        equipoJugadoresMap = new HashMap<>();
    }

    public void agregarJugadores(String equipo, List<Jugador> jugadores) {
        equipoJugadoresMap.put(equipo, jugadores);
    }

    public List<Jugador> obtenerJugadores(String equipo) {
        return equipoJugadoresMap.get(equipo);
    }

    public Map<String, List<Jugador>> obtenerMapa() {
        return equipoJugadoresMap;
    }
}
