package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;

public class Atlas extends Turn {

    /**
     * Sets the effect description
     */
    public Atlas() {
        effect = "Your Worker may build a dome at any level including the ground";
    }

    /**
     * Adds to the standard build Atlas' effect: the builder can build a dome at any level
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     */
    @Override
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        String username = match.getPlayerByBuilder(builder).getUsername();
        boolean useEffect = turnHandler.getInputUseEffect(username,"Atlas");
        if (useEffect) {
            match.getCell(buildingPosition).setDome(true);
        } else {
            match.setCellLevel(buildingPosition, Level.findLevelByHeight(match.getHeight(buildingPosition)+1));
        }
        match.notifyMap();
    }
}
