package it.polimi.ingsw.PSP13.view.GUI.status;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.GUI.TurnStatus;

public class SelectBuilderStatus extends TurnStatus {
    @Override
    public void selectCell(Coords coords) {
        map.getGuiInput().getController().notifyBuilderChoice(coords);
    }
}
