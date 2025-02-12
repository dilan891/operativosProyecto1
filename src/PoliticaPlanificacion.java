
import Dao.Proceso;
import Estructura.Cola;


/**
 *
 * @author Dilan891
 */
public interface PoliticaPlanificacion {
    Proceso seleccionarProceso(Cola listos);
}
