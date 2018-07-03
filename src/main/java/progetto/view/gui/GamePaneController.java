package progetto.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import progetto.model.*;

public class GamePaneController extends AbstractStateController {

    private int displayedPlayersCount = -1;
    private int lastChair = -1;
    @FXML
    private AnchorPane myPane;
    @FXML
    private HBox hBox;
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox roundVBox;
    @FXML
    private VBox preGameVBox;
    @FXML
    private VBox toolCardVBox;
    @FXML
    private VBox showToolCardVBox;
    @FXML
    private VBox useToolCardVBox;
    @FXML
    private ExtractedDicesPaneController extractedDicesPaneController;
    @FXML
    private PreGamePaneController preGamePaneController;
    @FXML
    private ActionQueuePaneController actionQueuePaneController;
    @FXML
    private RoundTrackPaneController roundTrackPaneController;
    @FXML
    private PlayerMenuPaneController playerMenuPaneController;
    @FXML
    private ObjectiveCardsController objectiveCardsPaneController;
    @FXML
    private ToolCardPaneController toolCardPaneController;
    @FXML
    private UseToolCardPaneController useToolCardPaneController;
    @FXML
    private UtilityMenuPaneController utilityMenuPaneController;

    @Override
    public void setup() {
        super.setup();

        Image image = new Image(getClass().getResourceAsStream("background.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(Control.USE_COMPUTED_SIZE,Control.USE_COMPUTED_SIZE,
                true,true,true,false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        myPane.setBackground(background);

        extractedDicesPaneController.setUp(getStateManager().getGuiView());
        playerMenuPaneController.setUp(getStateManager().getGuiView());
        roundTrackPaneController.setUp(getStateManager().getGuiView());
        preGamePaneController.setUp(getStateManager().getGuiView());
        objectiveCardsPaneController.setUp(getStateManager().getGuiView());
        toolCardPaneController.setUp(getStateManager().getGuiView());
        useToolCardPaneController.setUp(getStateManager().getGuiView());
        utilityMenuPaneController.setUp(getStateManager().getGuiView());
        mainVBox.getChildren().removeAll(preGameVBox, roundVBox);
        toolCardVBox.getChildren().clear();

        getStateManager().getObsModel().getMainBoard().addObserver(ogg -> Platform.runLater(this::update));
        getStateManager().getClientController().getRoomViewCallback().addObserver(ogg -> {
            if(getStateManager().getClientController().getChair()!=lastChair){
                Platform.runLater(this::update);
            }
        });
    }

    private void update(){
        IModel model = getModel();

        whatToShow(model);

        int currentNumberOfPlayer = model.getMainBoard().getData().getPlayerCount();
        int currentChair = getChair();

        if (model.getRoundInformation().getData().getCurrentPlayer() == lastChair &&
                model.getMainBoard().getData().getGameState().getClass() == ToolCardState.class){
            if(!toolCardVBox.getChildren().contains(useToolCardVBox)){
                toolCardVBox.getChildren().clear();
                toolCardVBox.getChildren().add(useToolCardVBox);
            }
        }
        else if(!toolCardVBox.getChildren().contains(showToolCardVBox)){
            toolCardVBox.getChildren().clear();
            toolCardVBox.getChildren().add(showToolCardVBox);
        }

        if(lastChair == currentChair && displayedPlayersCount == currentNumberOfPlayer){
            return;
        }

        if(currentChair == -1){
            addPlayerBoard(0);
        }
        else {
            addPlayerBoard(currentChair);
        }
        displayedPlayersCount = currentNumberOfPlayer;
        lastChair = currentChair;

    }

    private void whatToShow(IModel model){
        AbstractGameState abstractGameState = model.getMainBoard().getData().getGameState();
        if(abstractGameState.getClass() == PreGameState.class){
            mainVBox.getChildren().remove(roundVBox);
            if (!mainVBox.getChildren().contains(preGameVBox))
                mainVBox.getChildren().add(preGameVBox);
        }else if (abstractGameState.getClass() != EndGameState.class){
            mainVBox.getChildren().remove(preGameVBox);
            if (!mainVBox.getChildren().contains(roundVBox))
                mainVBox.getChildren().add(roundVBox);
        }else {
            getStateManager().getStateFromName("EndGamePane.fxml").show(false);
        }
    }

    private void addPlayerBoard(int i){
        hBox.getChildren().clear();
        Region region1 = new Region();
        hBox.getChildren().add(region1);
        HBox.setHgrow(region1, Priority.ALWAYS);
        Pane pane = PlayerBoardPaneController.getPlayerBoard(i, getView());
        hBox.getChildren().add(pane);
        Region region2 = new Region();
        hBox.getChildren().add(region2);
        HBox.setHgrow(region2, Priority.ALWAYS);
    }
}
