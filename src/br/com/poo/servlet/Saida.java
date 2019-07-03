package br.com.poo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "saida",
        urlPatterns = {"/saida"}
)
public class Saida extends HttpServlet {
	List linhas = new ArrayList();
	String txt = null;
	String linha;
	List lista;
	BuscaProduto buscaProduto = new BuscaProduto();
	Double quantidadeTotalProdutos = 0.0;
	String nome;
	
	protected void doGet(HttpServletRequest request,	
            HttpServletResponse response)
            throws IOException, ServletException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		 
		if(!request.getParameter("nome").isEmpty()) {
            nome = request.getParameter("nome");
        }else {
       	 out.println("<html>");
            out.println("<body>");
            out.println("<p>VocÃª precisa colocar o nome do produto a ser modificado.</p>");
            out.println("<div><a href='/TrabPoo/editaProduto.html'>Voltar</a></div>");
            out.println("</body>");
            out.println("</html>");
        }
		
       
        txt = buscaProduto.buscaTxt();
        
	    if(txt != null){
	    	lista = Arrays.asList(txt.split("-"));
	    }

	    if(lista != null){
	    	out.println("<html>");
	        out.println("<body style='text-align: center;'>");
	        out.println("<h2>Finanças</h2>");
	        		
	        for(int i = 0; i < lista.size(); i++){
	        	if(!lista.get(i).equals("\n") && !lista.get(i).equals("\n\n")){
        	 		if(lista.get(i) != null){
        	 			String produto = (String) lista.get(i);
        	 			if(produto != null){
        	 				String [] produtoAtributos = produto.split(";");
        	 				if(produtoAtributos != null && produtoAtributos.length != 0){
        	 					if(produtoAtributos[0].indexOf(":") != -1){
        	 						String[] nomeProduto = produtoAtributos[0].toString().split(":");
        	 						String[] quantidadeProduto = produtoAtributos[3].toString().split(":");
        	 						
        	 						if(nomeProduto[1] != null && nomeProduto[1].equals(nome)){
        	 							out.println("<div>");
        	 							out.println("Produto " + nomeProduto[1]);
        	 							out.println("</div>");
        	 							out.println("<div>");
            	 				        out.println("Quantidade atual: " + quantidadeProduto[1]);
            	 				        out.println("</div>");
            	 				        out.println("<div>");
            	 				        out.println("Quantidade entrada: " + quantidadeProduto[1]);
            	 				        out.println("</div>");
            	 				        out.println("<div>");
            	 				        out.println("Quantidade final: " + quantidadeProduto[1]);
            	 				        out.println("</div>");
            	 				        out.println("<div style='margin-bottom: 10px'>");
            	 				        out.println("</div>");
        	 						}
        	 					}
        	 				}
        	 			}
        	 		}
        	 	}
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
}