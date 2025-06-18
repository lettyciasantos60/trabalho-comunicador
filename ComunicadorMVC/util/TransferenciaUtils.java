
package util;

import java.io.*;

public class TransferenciaUtils {

    public static void enviarMensagem(String msg, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeUTF("MSG");
        dos.writeUTF(msg);
        dos.flush();
    }

    public static void enviarArquivo(File file, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeUTF("FILE");
        dos.writeUTF(file.getName());
        dos.writeLong(file.length());

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytes;
        while ((bytes = fis.read(buffer)) != -1) {
            dos.write(buffer, 0, bytes);
        }
        fis.close();
        dos.flush();
    }

    public static void receberArquivo(InputStream in, String pastaDestino) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        String nome = dis.readUTF();
        long tamanho = dis.readLong();

        FileOutputStream fos = new FileOutputStream(pastaDestino + File.separator + nome);
        byte[] buffer = new byte[4096];
        int bytesLidos;
        long bytesRecebidos = 0;

        while (bytesRecebidos < tamanho && (bytesLidos = dis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesLidos);
            bytesRecebidos += bytesLidos;
        }
        fos.close();
    }
}
