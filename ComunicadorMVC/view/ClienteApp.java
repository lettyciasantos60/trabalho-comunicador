
package view;

import controller.ChatScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;

public class ClienteApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatScreen.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Cliente");
        stage.setScene(scene);
        stage.show();

        ChatScreenController controller = loader.getController();
        Socket socket = new Socket("localhost", 12345);
        controller.setSocket(socket);
    }

    public static void main(String[] args) {
        launch();
    }
}
