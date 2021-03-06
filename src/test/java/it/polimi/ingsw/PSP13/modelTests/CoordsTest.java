package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.player.Coords;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordsTest {

    @Test
    public void setX_CorrectInput_CorrectBehaviour()
    {
        Coords coords = new Coords(3,2);
        coords.setX(5);

        assertEquals(coords, new Coords(5,2));
    }

    @Test
    public void setY_CorrectInput_CorrectBehaviour()
    {
        Coords coords = new Coords(3,2);
        coords.setY(5);

        assertEquals(coords, new Coords(3,5));

    }


}
