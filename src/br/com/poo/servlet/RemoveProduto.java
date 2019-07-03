package br.com.poo.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "removeProduto",
        urlPatterns = {"/removeProduto"}
)
public class RemoveProduto extends HttpServlet {
	
	BuscaProduto buscaProduto = new BuscaProduto();
	String txt;
	List<String> lista;
	Boolean isExiste = false;
	String produtoRemovido;
	String produtoEditadoNome;
	String nome;
	
	 protected void service(HttpServletRequest request,	
             HttpServletResponse response)
             throws IOException, ServletException {

         PrintWriter out = response.getWriter();

         if(!request.getParameter("nome").isEmpty()) {
             nome = request.getParameter("nome");
         }else {
        	 out.println("<html>");
             out.println("<body>");
             out.println("<p>Você precisa colocar o nome do produto a ser removido.</p>");
             out.println("<div><a href='/TrabPoo/editaProduto.html'>Voltar</a></div>");
             out.println("</body>");
             out.println("</html>");
             return;
         }
        
        txt = buscaProduto.buscaTxt();
        
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
                	        	if(nomeProduto[1] != null && nomeProduto[1].equals(nome)){
                	        		produtoRemovido = produto;
                	        		isExiste = true;
                	        	}
        	 				}
        	 			}
        	 		}
        	 	}
	        }
         
         if(isExiste){
        	 File txtOld = new File("banco.txt");
        	 txtOld.delete();
        	 FileWriter arquivo = new FileWriter("banco.txt", true);
        	 String[] txtEditado = txt.split(produtoRemovido + "-");
        	 if(txtEditado != null){
        		 for(int i = 0; i < txtEditado.length; i++){
            		 arquivo.write(txtEditado[i] + "\r\n");
            	 }
        	 }
        	 arquivo.close();
        	 
        	 
        	 out.println("<html>");
             out.println("<body>");
             out.println("<p>O produto " + produtoRemovido + " foi removido </p>");
             out.println("<div><a href='/TrabPoo/removeProduto.html'>Remover Novamente</a></div>");
             out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
             out.println("</body>");
             out.println("</html>");
         }else{
        	 out.println("<html>");
             out.println("<body>");
             out.println("<p>ops nao foi encontrado nenhum produto com esse nome</p>");
             out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
             out.println("</body>");
             out.println("</html>");
         }
     }
}
