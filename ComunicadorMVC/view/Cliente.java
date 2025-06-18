
package view;

import controller.ChatController;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            ChatController controller = new ChatController(socket);
            controller.ouvir();

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("1 - Enviar mensagem
2 - Enviar arquivo");
                String opcao = sc.nextLine();

                if (opcao.equals("1")) {
                    System.out.print("Digite a mensagem: ");
                    String msg = sc.nextLine();
                    controller.enviarMensagem(msg);
                } else if (opcao.equals("2")) {
                    System.out.print("Caminho do arquivo: ");
                    String caminho = sc.nextLine();
                    File file = new File(caminho);
                    if (file.exists()) {
                        controller.enviarArquivo(file);
                    } else {
                        System.out.println("Arquivo não encontrado.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
