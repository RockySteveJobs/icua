package iCua.Media;

import java.util.EventListener;

//Define el interfaz para el nuevo tipo de receptor de eventos
public interface IPlayer extends EventListener {
    void capturarMiEvento( Eplayer evt );
    }
