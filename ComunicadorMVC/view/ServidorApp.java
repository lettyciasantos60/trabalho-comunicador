
package view;

import controller.ChatScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatScreen.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Servidor");
        stage.setScene(scene);
        stage.show();

        ChatScreenController controller = loader.getController();
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket socket = serverSocket.accept();
        controller.setSocket(socket);
    }

    public static void main(String[] args) {
        launch();
    }
}
