package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientSetupBehavior extends ClientBuildBehavior{

    @Override
    public void behavior(MessageCV messageCV) {
        boolean callNumber = messageCV.isCallNumber();
        boolean error = messageCV.isError();

        input.builderSetUpInput(callNumber, error);
    }

    public ClientSetupBehavior(Input input)
    {
        super(input);
    }
}
