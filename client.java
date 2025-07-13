import java.awt.*;
public class client{
    TextField textField;
    TextArea textArea;
    Button send;
    client(){
           textArea=new TextArea();
           textField=new TextField();
           send=new Button("Send");

           add(textField);
           add(textArea);
           add(send);

    }
}