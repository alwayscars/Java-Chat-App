import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class client extends Frame implements Runnable, ActionListener {
    TextField textField;
    TextArea textArea;
    Button send;
    Thread chat;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;  
    client(){
           textArea=new TextArea();
           textField=new TextField();
           send=new Button("Send");

           send.addActionListener(this);
            try{
            socket=new Socket("localhost",12000);
           dataInputStream= new DataInputStream(socket.getInputStream());
           dataOutputStream= new DataOutputStream(socket.getOutputStream());
           }catch(Exception e){
                textArea.append("Could not connect to server.\n");
            e.printStackTrace();
           }

           add(textField);
           add(textArea);
           add(send);
           
           chat= new Thread(this);
           chat.setDaemon(true); // Set as daemon thread
           chat.start();
           setSize(500,500);
           setTitle("Client Chat");
              setVisible(true);
           setLayout(new FlowLayout());
    }
         
        public void actionPerformed(ActionEvent e) {
          String msg= textField.getText();
          textArea.append("You: " + msg + "\n");
          textField.setText("");
        try{
           dataOutputStream.writeUTF(msg);
           dataOutputStream.flush();
        }catch(Exception E){
          
        }
          
        }
        public static void main(String[] args) {
            new client();
        }
        public void run() {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    textArea.append("Server: " + message + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       
    
}