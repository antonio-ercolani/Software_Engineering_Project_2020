package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Poseidon extends Turn {

    private Builder unmovedBuilder;

    public Poseidon ()
    {
        this.unmovedBuilder = null;
    }

    /**
     * In addiction to turn's move, sets the unmoved builder
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) {
        if (match.getPlayerByBuilder(builder).getBuilders()[0] == builder) {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[1];
        } else {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[0];
        }
        super.move(builder,coords);
    }

    /**
     * If the unmoved builder is on the ground level, it can build
     * up to three times in neighbouring cells
     */
    @Override
    public void end() {
        if (match.getHeight(unmovedBuilder.getCoords()) == 0) {
            if (!getCellBuilds(unmovedBuilder).isEmpty()) {
                boolean useEffect = turnHandler.getInputUseEffect("Poseidon");
                Coords buildCoords;
                int i = 0;
                while (useEffect && (i < 3) && !getCellBuilds(unmovedBuilder).isEmpty()) {
                    buildCoords = turnHandler.getInputBuild(unmovedBuilder, getCellBuilds(unmovedBuilder));
                    super.build(unmovedBuilder, buildCoords);
                    i++;
                    useEffect = turnHandler.getInputUseEffect("Poseidon");
                }
                match.notifyMap();
            }
        }
    }


}
