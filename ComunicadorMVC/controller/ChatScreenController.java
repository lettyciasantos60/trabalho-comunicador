
package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Mensagem;
import util.SomUtils;
import util.TransferenciaUtils;

import java.io.*;
import java.net.Socket;

public class ChatScreenController {

    @FXML private TextField mensagemField;
    @FXML private TextArea chatArea;

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        ouvirMensagens();
    }

    public void enviarMensagem(ActionEvent event) {
        String msg = mensagemField.getText();
        if (!msg.isEmpty()) {
            try {
                TransferenciaUtils.enviarMensagem(msg, out);
                appendMensagem("Você: " + msg);
                mensagemField.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void enviarArquivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                TransferenciaUtils.enviarArquivo(file, out);
                appendMensagem("Você enviou o arquivo: " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ouvirMensagens() {
        new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(in);
                while (true) {
                    String tipo = dis.readUTF();
                    if (tipo.equals("MSG")) {
                        String mensagem = dis.readUTF();
                        SomUtils.tocarSom("resources/sons/notificacao.wav");
                        appendMensagem("Remoto: " + mensagem);
                    } else if (tipo.equals("FILE")) {
                        File pasta = new File("arquivos_recebidos");
                        pasta.mkdirs();
                        TransferenciaUtils.receberArquivo(in, "arquivos_recebidos");
                        SomUtils.tocarSom("resources/sons/notificacao.wav");
                        appendMensagem("Arquivo recebido.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void appendMensagem(String texto) {
        Platform.runLater(() -> chatArea.appendText(texto + "\n"));
    }
}
