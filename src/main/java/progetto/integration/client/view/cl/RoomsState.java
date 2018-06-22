package progetto.integration.client.view.cl;

public class RoomsState extends AbstractCLViewState {

    public RoomsState(CommandLineView view) {
        super("RoomState", view);
    }

    @Override
    public void onApply() {

        registerCommand(new CreateRoomCommand(getView()));
        registerCommand(new ShowRoomCommand(getView()));
        registerCommand(new UpdateCommand(getView()));
        registerCommand(new ReturnCommand(getView(), new DefaultViewState(getView()), "Indietro"));

    }

    @Override
    public String getMessage() {
        return "\nSelezionare una tra le opzioni proposte:\n";
    }
}
