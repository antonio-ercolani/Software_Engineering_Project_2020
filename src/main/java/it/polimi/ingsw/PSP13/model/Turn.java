package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.model.board.*;
import it.polimi.ingsw.PSP13.model.player.*;
import it.polimi.ingsw.PSP13.controller.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Turn {

    protected static Match match;
    protected static TurnHandler turnHandler;
    protected String effect;

    public Turn(Match match, TurnHandler turnHandler)
    {
        Turn.match = match;
        Turn.turnHandler = turnHandler;
    }

    public Turn(){}

    /**
     * Sets the position of builder1 to coords1 and builder2 to coords2
     * @param builder1 the first player's builder
     * @param builder2 the second player's builder
     * @param coords1 the coordinates where the first builder will be placed
     * @param coords2 the coordinates where the second builder will be placed
     * @throws IllegalArgumentException if params aren't legal
     */
    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IllegalArgumentException, IOException {
        if (!Map.isLegal(coords1) || !Map.isLegal(coords2) || builder1 ==  null || builder2 == null) {
            throw new IllegalArgumentException();
        } else {
            builder1.setCell(match.getCell(coords1));
            builder2.setCell(match.getCell(coords2));
            match.notifyBuilder(builder1, builder2);
        }
    }


    /**
     * Method that manages tasks at the beginning of the turn
     */
    public void start(String player) throws IOException {}

    /**
     * Checks if the builder belongs to the player
     * @param player current player
     * @param builder selected builder
     * @return true if the builder belongs to the player, false otherwise
     */
    public boolean builderSelection(String player, Builder builder) {
        return player.equals(match.getPlayerByBuilder(builder).getUsername());
    }

    /**
     * Moves builder into the cell's coordinates
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    public void move(Builder builder, Coords coords) throws IOException {
        builder.setCell(match.getCell(coords));
        match.notifyBuilder(builder,match.getOtherBuilder(builder));
    }

    /**
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return true if builder can move into coords' cell, else return false
     */
    public boolean checkMove(Builder builder, Coords coords) {
        if (!Map.isLegal(coords) || builder == null) {
            return false;
        } else {
            int diff = match.getCell(coords).getLevel().getHeight() - match.getHeight(builder.getCoords());
            if (match.getAdjacent(builder.getCoords()).contains(coords) && !match.isOccupied(coords) && diff <= 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Sets the forced position of builder
     * @param builder forced builder
     * @param coords coordinates of the cell where the builder is forced to move
     */
    public void force(Builder builder, Coords coords) throws IOException {
        builder.setCell(match.getCell(coords));
        match.notifyBuilder(builder,match.getOtherBuilder(builder));
    }

    /**
     * Builds a level in the specified position
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     */
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        int currentLevel = match.getHeight(buildingPosition);
        if(currentLevel == Level.Top.getHeight())
            match.getCell(buildingPosition).setDome(true);
        else
            match.setCellLevel(buildingPosition, Level.findLevelByHeight(currentLevel+1));
        match.notifyMap();
    }

    /**
     * Checks if the building position is legal
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @return true if it is possible to build in the specified position, false otherwise
     */
    public boolean checkBuild(Builder builder, Coords buildingPosition)
    {
        if (!Map.isLegal(buildingPosition)) return false;
        if (match.isOccupied(buildingPosition)) return false;
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        if(!adjacents.contains(buildingPosition)) return false;

        return true;
    }

    /**
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return true if the player did win in this turn, false otherwise
     */
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        if (match.getCell(precedentPosition).getLevel() == Level.Medium
                && match.getCell(currentPosition).getLevel() == Level.Top) {
            return true;
        }
        return false;
    }

    /**
     * Method that manages tasks at the end of the turn.
     */
    public void end() throws IOException {}

    /**
     * @param builder current builder
     * @return a list of adjacent cells where a builder can move in
     */
    public List<Coords> getPossibleMoves(Builder builder)
    {
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        List<Coords> possibleMove = new ArrayList<>();

        for(Coords coords : adjacents)
        {
            if(checkMove(builder, coords))
                possibleMove.add(coords);
        }
        return possibleMove;
    }

    /**
     * This method handles the elimination of the player
     */
    public void eliminated() {}

    /**
     * @param builder current builder
     * @return a list of adjacent cells where the builder can build on
     */
    public List<Coords> getBuildableCells(Builder builder)
    {
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        List<Coords> possibleBuildingSite = new ArrayList<>();

        adjacents.add(builder.getCoords());

        for(Coords coords : adjacents)
        {
            if(checkBuild(builder, coords))
                possibleBuildingSite.add(coords);
        }
        return possibleBuildingSite;
    }

    public static void setMatch(Match match) {
        Turn.match = match;
    }

    public static void setTurnHandler(TurnHandler turnHandler) {
        Turn.turnHandler = turnHandler;
    }

    public String getEffect() {
        return effect;
    }
}
