
package Encryption;
import java.io.*;
import java.net.*;

/**
 *
 * @author satal
 */
public class client {
    
   public static void main(String[] args) throws IOException {
          islem();
     }
   public static void islem() throws UnknownHostException, IOException {
          Socket socket = null;
          PrintWriter out = null;
          BufferedReader in = null;
          String deger;
          try {
               //* server 'a localhost ve 7755 portu üzerinden başlantı sağlanıyor *//
               socket = new Socket( "192.168.56.1", 7755);
          } catch (Exception e) {
               System.out.println("Port Hatası!");
          }
          out = new PrintWriter(socket.getOutputStream(), true);
 
          //* Server'dan gelen verileri tutan BufferedReader nesnesi oluşturulur *//
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
          System.out.println("Server'a gönderilecek sayısı giriniz:");
 
          //* Gönderilecek sayının girişi yapılıyor *//
          BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
 
          while((deger = data.readLine()) != null) {
               out.println(deger);
               
               
          }
          out.close();
          in.close();
          data.close();
          socket.close();
   }   
}
