package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;


public class ViewObservable {

    private Match match;
    private VirtualView virtualView;

    public ViewObservable (Match match, VirtualView virtualView) throws IOException {
        this.match = match;
        this.virtualView = virtualView;
    }

    /**
     * Notifies view's ModelObserver sending a MapVM object
     */
    public void notifyMap() throws IOException {
        virtualView.updateMap(new MapVM(match.getMap().getMatrix()));
    };

    /**
     * Notifies view's ModelObserver sending a BuilderVM object
     * @param builder1 first builder
     * @param builder2 second builder
     */
    public void notifyBuilder(Builder builder1, Builder builder2) throws IOException {
        Coords[] builders = new Coords[2];
        builders[0] = builder1.getCoords();
        builders[1] = builder2.getCoords();
        virtualView.updateBuilders(new BuilderVM(builders, match.getPlayerByBuilder(builder1).getColor()));
    }

    /**
     * Notifies the remotion of a player's builder to the view's ModelObserver sending a BuilderVM object
     * @param player eliminated player
     * @throws IOException
     */
    public void removeBuilder(Player player) throws IOException {
        Coords[] builders = new Coords[]{null, null};
        virtualView.updateBuilders(new BuilderVM(builders, player.getColor()));
    }



}
