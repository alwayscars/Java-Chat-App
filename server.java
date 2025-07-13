import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class server extends Frame implements Runnable, ActionListener {
    TextField textField;
    TextArea textArea;
    Button send;
    Thread chat;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;  
    server(){
           textArea=new TextArea();
           textField=new TextField();
           send=new Button("Send");

           send.addActionListener(this);
            try{
           serverSocket=new ServerSocket(12000);
           socket = serverSocket.accept();
           dataInputStream= new DataInputStream(socket.getInputStream());
           dataOutputStream= new DataOutputStream(socket.getOutputStream());
           }catch(Exception e){
               
                textArea.append("Could not connect to client.\n");
            e.printStackTrace();
           }

           add(textField);
           add(textArea);
           add(send);
           
           chat= new Thread(this);
            chat.setDaemon(true); 
           chat.start();
           setSize(500,500);
           setTitle("Server Chat");
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
            new server();
        }
        public void run() {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    textArea.append("Client: " + message + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       
    
}