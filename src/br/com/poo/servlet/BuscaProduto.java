package br.com.poo.servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "buscaProduto",
        urlPatterns = {"/buscaProduto"}
)
public class BuscaProduto extends HttpServlet {
	List linhas = new ArrayList();
	String txt = null;
	String linha;
	List lista;
	
	protected void doGet(HttpServletRequest request,	
            HttpServletResponse response)
            throws IOException, ServletException {
		
		response.setContentType("text/html");
		
        PrintWriter out = response.getWriter();
        buscaTxt();
	    if(txt != null){
	    	lista = Arrays.asList(txt.split("-"));
	        System.out.println(lista);
	    }
	    
	    if(lista != null){
	    	out.println("<html>");
	        out.println("<body>");
	        for(int i = 0; i < lista.size(); i++){
	        	String produto = (String) lista.get(i);
	        	out.println("<p>" + lista.get(i) + "</p>");
	        }
	        out.println("</body>");
	        out.println("</html>");
	        out.close();
	    }else{
	    	out.println("<html>");
	        out.println("<body>");
	        out.println("Nenhum produto cadastrado ainda.");
	        out.println("</body>");
	        out.println("</html>");
	        out.close();
	    }
	    
	}
	
	public String buscaTxt() throws IOException{
			try {
				
				Reader fReader = new FileReader("banco.txt");
				BufferedReader bReader = new BufferedReader(fReader);

				// AQUI LEIO O CONTEUDO DO ARQUIVO E GUARDO NA VARIAVEL conteudo
				while(bReader.ready()){
				  txt += bReader.readLine() + "\n";
				}

				bReader.close();
				return txt;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}		    
	}
}
