package Triangle_TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket server;
    public enum TriangleType {
	KHONG_PHAI_TAM_GIAC,
	TAM_GIAC,
	TAM_GIAC_VUONG,
	TAM_GIAC_CAN,
	TAM_GIAC_DEU
    }
    
    private  static Socket client;
    public Server() {
	try {
            server = new ServerSocket(1234); 
            System.out.println("Server is running............!");
			
            while(true)	{ 
		client = server.accept(); 
		handleClientConnection(client);
            }		
	} catch (IOException e) {
		}
    }	
	
    int[] parseInput(String input)  {
        int[]   array = new int[input.length()]; 
        Scanner read = new Scanner(input);
        for(int i = 0; i < 6; i++) { 
            array[i] = read.nextInt(); 
        }
        
    return array;
    }
   
    public void handleClientConnection(Socket client)   {
        try {
        	
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream())); 
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); 
            
            String input = reader.readLine(); 
            int[] array = parseInput(input);
            
            TriangleType check = checkTriangle(array[0], array[1], array[2], array[3], array[4], array[5]);
           
            switch (check) 
            {
                case TAM_GIAC:
                    writer.write("Tam giac thuong");
                    writer.write("\n\n"); 
                    break;
                case TAM_GIAC_VUONG:
                    writer.write("Tam giac vuong"); 
                    writer.write("\n\n");
                    break;
                case TAM_GIAC_CAN:
                    writer.write("Tam giac can");
                    writer.write("\n\n");
                    break;
                case TAM_GIAC_DEU:
                    writer.write("Tam giac deu");
                    writer.write("\n\n");
                    break;
                default:
                    writer.write("Khong phai tam giac");
                    writer.write("\n\n");
                    writer.flush();
                    
                    writer.write("finished"); 
                    writer.write("\n");
                    writer.flush();
                    break;
            }
            
            writer.write("finished");
            writer.write("\n");
            writer.flush();
            
            reader.close();
            writer.close();
            client.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private TriangleType checkTriangle(int x1, int y1, int x2, int y2, int x3, int y3)	{
	TriangleType type = TriangleType.KHONG_PHAI_TAM_GIAC;
		
	double ab = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); 
	double bc = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3)); 
	double ca = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3)); 
	
	if(ab + bc > ca || ab + ca > bc || bc + ca > ab)
        {type = TriangleType.TAM_GIAC;
		
	if( (ab * ab + bc * bc == ca * ca) || (ab * ab + ca * ca == bc * bc) || (bc * bc + ca * ca == ab * ab))
		type = TriangleType.TAM_GIAC_VUONG;
		
	if( (ab == bc && ab == ca) || (bc == ab && bc == ca) || (ca == ab && ca == bc))
		type = TriangleType.TAM_GIAC_CAN;
			
	if(ab == bc && bc == ca) 
		type = TriangleType.TAM_GIAC_DEU;
        }
        return type;
    }
        
    private boolean isPointInsideTriangle(int px, int py, int x1, int y1, int x2, int y2, int x3, int y3) {
	double AB = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double BC = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
        double CA = Math.sqrt((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1));
        
        double PA = Math.sqrt((px - x1) * (px - x1) + (py - y1) * (py - y1));
        double PB = Math.sqrt((px - x2) * (px - x2) + (py - y2) * (py - y2));
        double PC = Math.sqrt((px - x3) * (px - x3) + (py - y3) * (py - y3));
        
        double sABC = dienTichTamGiac(AB + BC + CA, AB, BC, CA);
        double sPAB = dienTichTamGiac(PA + PB + AB, PA, PB, AB);
        double sPBC = dienTichTamGiac(PB + PC + BC, PB, PC, BC);
        double sPCA = dienTichTamGiac(PC + PA + CA, PC, PA, CA);
        
        if(sPAB + sPBC + sPCA == sABC) return true;
        return false;
    }

    private double triangleArea(double p, double a, double b, double c) {
        p = p/2; 
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

	
    public static void main(String[] args) {
	new Server();
    }
	
}
