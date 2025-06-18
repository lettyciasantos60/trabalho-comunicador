
package controller;

import model.Mensagem;
import util.SomUtils;
import util.TransferenciaUtils;

import java.io.*;
import java.net.Socket;

public class ChatController {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public ChatController(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    public void enviarMensagem(String msg) throws IOException {
        TransferenciaUtils.enviarMensagem(msg, out);
    }

    public void enviarArquivo(File file) throws IOException {
        TransferenciaUtils.enviarArquivo(file, out);
    }

    public void ouvir() {
        new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(in);
                while (true) {
                    String tipo = dis.readUTF();
                    if (tipo.equals("MSG")) {
                        String mensagem = dis.readUTF();
                        System.out.println("Recebido: " + mensagem);
                        SomUtils.tocarSom("resources/sons/notificacao.wav");
                    } else if (tipo.equals("FILE")) {
                        TransferenciaUtils.receberArquivo(in, "arquivos_recebidos");
                        SomUtils.tocarSom("resources/sons/notificacao.wav");
                        System.out.println("Arquivo recebido.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
