package progetto.clientintegration;

import progetto.gui.AbstractStateController;

public abstract class AbstractClientStateController extends AbstractStateController {

    private ClientViewStateMachine clientViewStateMachine;

    public void setClientViewStateMachine(ClientViewStateMachine clientViewStateMachine){

        this.clientViewStateMachine = clientViewStateMachine;

    }

    public ClientViewStateMachine getClientViewStateMachine(){

        return clientViewStateMachine;

    }

}

