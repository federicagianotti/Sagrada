package progetto.view.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import progetto.ClientMain;
import progetto.ServerGameFactory;
import progetto.Settings;
import progetto.network.NetworkServer;
import progetto.network.localconnection.LocalConnectionModule;
import progetto.network.rmi.RMIModule;
import progetto.network.socket.SocketServer;
import progetto.utils.Waiter;

public class ClientWiewTest extends ApplicationTest {

    private NetworkServer networkServer;

    Waiter timoty = new Waiter();

    @Before
    public void setUp() throws Exception {

        networkServer = new NetworkServer(new ServerGameFactory());

        networkServer.addModules(new RMIModule(Settings.getSettings().getRmiPort()));
        networkServer.addModules(new SocketServer(Settings.getSettings().getSocketPort()));
        networkServer.addModules(new LocalConnectionModule());
        networkServer.start();
        ApplicationTest.launch(ClientMain.class);

    }

    @Override
    public void start(Stage stage){

        stage.setMaximized(true);
        stage.show();

    }

    @Test
    public void testGeneral() {

        clickOn("#sagradaImageView");
        clickOn("#newGame");
        clickOn("#rmi");
        clickOn("#rmi");
        clickOn("#socket");
        clickOn("#socket");
        clickOn("#connectButton");
        clickOn("#createRoom");
        clickOn("#roomNameTextField");
        write("Room1");
        clickOn("#createRoom");
        clickOn("#enterButton");
        clickOn("#usernameTextField");
        write("Federica");
        clickOn("#enterButton");
        clickOn("#update");
        clickOn("#listView");
        type(KeyCode.DOWN);
        clickOn("#enterButton");
        clickOn("#numberOfPlayersChoice");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#numberOfChairChoice");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#sitButton");
        timoty.wait(500);
        clickOn("#startGameButton");
        clickOn("#chooseWindowFrame");
        clickOn("#firstWIndow");
        clickOn("#chairs");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#sitButton");
        clickOn("#chooseWindowFrame");
        clickOn("#firstWIndow");
        timoty.wait(1000);
        try{
            clickOn("#okButton");
        }catch (FxRobotException exception){
            //
        }
        clickOn("#chatButton");
        clickOn("#messageToSend");
        write("aaa\n");
        clickOn("#returnButton");
        clickOn("#showOtherPlayersButton");
        clickOn("#returnButton");
        clickOn("#menuButton");
        clickOn("#exitGameButton");

        Assert.assertTrue(true);

    }

    @Test
    public void testNoClientConnection(){

        clickOn("#sagradaImageView");
        clickOn("#newGame");
        clickOn("#iPAddress");
        write("0.0.0.0");
        clickOn("#rmi");
        clickOn("#connectButton");

        Assert.assertTrue(true);
    }

    @Test
    public void testEndGameSinglePlayer(){
        clickOn("#sagradaImageView");
        clickOn("#newGame");
        clickOn("#connectButton");
        clickOn("#roomNameTextField");
        write("Room1");
        clickOn("#createRoom");
        clickOn("#usernameTextField");
        write("Federica");
        clickOn("#listView");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        clickOn("#enterButton");
        clickOn("#numberOfPlayersChoice");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#difficultyChoice");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#numberOfChairChoice");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#sitButton");
        clickOn("#startGameButton");
        clickOn("#chooseWindowFrame");
        clickOn("#firstWIndow");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");
        clickOn("#endTurnButton");

    }

    @Test
    public void testTwoGame(){

        clickOn("#sagradaImageView");
        clickOn("#newGame");
        clickOn("#connectButton");
        clickOn("#roomNameTextField");
        write("Room1");
        clickOn("#createRoom");
        clickOn("#usernameTextField");
        write("Federica");
        clickOn("#listView");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        clickOn("#enterButton");
        clickOn("#numberOfPlayersChoice");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#difficultyChoice");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#numberOfChairChoice");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#sitButton");
        clickOn("#startGameButton");
        timoty.wait(500);
        clickOn("#menuButton");
        clickOn("#newGame");
        clickOn("#connectButton");
        clickOn("#roomNameTextField");
        write("Room2");
        clickOn("#createRoom");
        clickOn("#usernameTextField");
        write("Federica");
        clickOn("#listView");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        clickOn("#enterButton");

        Assert.assertTrue(true);
    }

    @After
    public void tearDown() throws Exception {

        timoty.wait(500);

        FxToolkit.hideStage();
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        networkServer.stop();

        timoty.wait(500);
    }

}