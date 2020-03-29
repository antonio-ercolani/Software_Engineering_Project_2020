package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.model.board.*;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.player.*;

import java.util.List;


public class Turn {

    protected Match match;

    public Turn(Match match)
    {
        this.match = match;
    }

    public Turn()
    {

    }
    /**
     * Sets the position of builder1 to coords1 and builder2 to coords2
     * @param builder1
     * @param builder2
     * @param coords1
     * @param coords2
     * @throws IllegalArgumentException if params aren't legal
     */
    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IllegalArgumentException {
        if (!Map.isLegal(coords1) || !Map.isLegal(coords2) || coords1 == null || coords2 == null || builder1 ==  null || builder1 == null) {
            throw new IllegalArgumentException();
        } else {
            builder1.setCoords(coords1);
            builder2.setCoords(coords2);
        }
    }

    /**
     * Moves builder into the cell's coordinates
     * @param builder
     * @param coords
     * @throws IllegalMoveException if checkMove(builder, coords) return false
     */
    public void move(Builder builder, Coords coords) throws IllegalMoveException {
        if (checkMove(builder, coords)) {
            builder.setCoords(coords);
        } else {
            throw new IllegalMoveException();
        }
    }

    /**
     * @param builder
     * @param coords coordinates of the cell where the builder wants to move to
     * @return true if builder can move into coords' cell, else return false
     * @throws IllegalArgumentException if params aren't legal
     */
    public boolean checkMove(Builder builder, Coords coords) throws IllegalArgumentException {
        if (!Map.isLegal(coords) || coords == null || builder == null) {
            throw new IllegalArgumentException();
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
     * @param builder
     * @param coords
     */
    public void force(Builder builder, Coords coords) {
        builder.setCoords(coords);
    }

    /**
     * Builds a level in the specified position
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IllegalBuildException if buildingPosition is not legal
     */
    public void build(Builder builder, Coords buildingPosition) throws IllegalBuildException
    {
        if(!checkBuild(builder, buildingPosition)) {
            throw new IllegalBuildException();
        }
        int currentLevel = match.getHeight(buildingPosition);
        match.setCell(buildingPosition, Level.findLevelByHeight(currentLevel+1));
    }

    /**
     * Checks if the building position is legal.
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @return true if it is possible to build in the specified position, false otherwise.
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
     * @return true if the player did win in this turn, false otherwise.
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
     * Method the manages tasks in the end of the turn.
     */
    public void end()
    {}


}