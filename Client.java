package TamGiac_TCP;

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
            
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); 
            
            out.write(input);
            out.write('\n');
            out.flush();
            
            String response = null; 
            while((response = in.readLine()) != null)    {
                if(response.equalsIgnoreCase("finished")) 
                    break;
                
                System.out.println(response); 
            }
             
            in.close();
            out.close();
            client.close();
            
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
            ex.printStackTrace(); 
            // sout ex.getMessage();
        }
    }
    
    public static void main(String[] args) {
        String ch = ""; 
        do {
            try {
                String input;
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); 
                System.out.print("Chon phuong thuc nhap du lieu (1 - Nhap tu ban phim || 2 - Nhap tu file): ");
                ch = keyboard.readLine();
                System.out.println("");

                if(ch.equalsIgnoreCase("1")) { 
                    input = inputFromKeyboard();
                    System.out.println("");
                    System.out.println("----------------------------------------");
                    System.out.println("Input: " + input);
                    calculator(input); 
                    System.out.println("----------------------------------------");
                }
                else if(ch.equalsIgnoreCase("2")) {
                    
                    BufferedReader file = new BufferedReader(new FileReader("input.txt"));
                    
                    while((input = file.readLine()) != null) { 
                   
                        if(input.equalsIgnoreCase("stop")) { 
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
