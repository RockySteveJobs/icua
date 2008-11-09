package iCua.Media;

import java.util.EventListener;
import java.util.EventObject;

public class Eplayer extends EventObject {
    // Variable de instancia para diferencia a cada objeto de este tipo
    String id;

    // Constructor parametrizado
    Eplayer( Object obj,String id ) {
        // Se le pasa el objeto como parametro a la superclase
        super( obj );
        // Se guarda el identificador del objeto
        this.id = id;
        }

    // Metodo para recuperar el identificador del objeto
    String getEventoID() {
        return( id );
        }
    }

 