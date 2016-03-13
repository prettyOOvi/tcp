package triangle_TCP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static String inputFromKeyboard() throws IOException  {
        String input = "";
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.println("(x1 y1  x2  y2  x3  y3 )");
        input = keyboard.readLine();
        
        return input;
    }
    private static Socket client;
    public static void calculator(String input)   {
        try {
            client = new Socket("localhost", 1234); 
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); 
            
            writer.write(input);
            writer.write('\n');
            writer.flush();
            
            String response = null; 
            while ((response = reader.readLine()) != null)    {
                if (response.equalsIgnoreCase("finished")) 
                    break;
                
                System.out.println(response); 
            }
             
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
            ex.printStackTrace(); 
            // sout (ex.getMessage());
        }   finally {
            reader.close();
            writer.close();
            client.close();
        }
    }
    
    public static void main(String[] args) {
        String ch = ""; 
        do {
            try {
                String input;
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); 
                System.out.print("Enter choice: (1 - Data input from keyboard || 2 - Data input from file): ");
                ch = keyboard.readLine();
                System.out.println("");

                if  (ch.equalsIgnoreCase("1")) { 
                    input = inputFromKeyboard();
                    System.out.println("");
                    System.out.println("----------------------------------------");
                    System.out.println("Input: " + input);
                    calculator(input); 
                    System.out.println("----------------------------------------");
                }   else if (ch.equalsIgnoreCase("2")) {
                    
                    BufferedReader file = new BufferedReader(new FileReader("input.txt"));
                    
                    while ((input = file.readLine()) != null) { 
                   
                        if (input.equalsIgnoreCase("stop")) { 
                            break;
                        }
                       
                        System.out.println("----------------------------------------");
                        System.out.println("Input: " + input);
                        calculator(input); 
                        System.out.println("----------------------------------------");
                    }
                }
                  
            } catch (IOException ex) {
                ex.printStackTrace(); 
            }
            
        } while (!ch.endsWith("quit")); 
    }
}
