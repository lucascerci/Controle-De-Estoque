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
	String produtoPesquisado = null;
	String nome;
	Boolean produtoAchado = false;
	
	protected void doGet(HttpServletRequest request,	
            HttpServletResponse response)
            throws IOException, ServletException {
		
		response.setContentType("text/html");
		
		if(!request.getParameter("nome").isEmpty()) {
			nome = request.getParameter("nome");
		}
		
        PrintWriter out = response.getWriter();
        buscaTxt();

		if(txt != null){
 	    	lista = Arrays.asList(txt.split("-"));
 	     }
		
         for(int i = 0; i < lista.size(); i++){
        	 	if(!lista.get(i).equals("\n")){
        	 		if(lista.get(i) != null){
        	 			String produto = (String) lista.get(i);
        	 			if(produto != null){
        	 				String [] produtoAtributos = produto.split(";");
        	 				if(produtoAtributos != null && produtoAtributos.length != 0){
        	 					String[] nomeProduto = produtoAtributos[0].toString().split(":");
        	 					if(!produtoAchado){
        	 						if(nomeProduto[1] != null && nomeProduto[1].equals(nome)){
                    	        		produtoPesquisado = produto;
                    	        		produtoAchado = true;
                    	        	}else {
                    	        		produtoPesquisado = "Não foi encontrado esse produto no banco.";
                    	        	}
        	 					}
        	 				}
        	 			}
        	 		}
        	 	}
	        }
	    
	    if(produtoPesquisado != null){
	    	out.println("<html>");
	        out.println("<body>");
	        out.println("<p>" + produtoPesquisado + "</p>");
	        out.println("<div><a href='/TrabPoo/consultaProdutos.html'>Consultar Outro</a></div>");
            out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
	        out.println("</body>");
	        out.println("</html>");
	        out.close();
	    }else {
	    	out.println("<html>");
	        out.println("<body>");
	        out.println("<p>Erro não tratado ao buscar produto.</p>");
	        out.println("</body>");
	        out.println("</html>");
	        out.close();
	    }
	}
	
	public String buscaTxt() throws IOException{
			try {
				
				Reader fReader = new FileReader("banco.txt");
				BufferedReader bReader = new BufferedReader(fReader);

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
