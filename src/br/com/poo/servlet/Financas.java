package br.com.poo.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "financas",
        urlPatterns = {"/financas"}
)
public class Financas extends HttpServlet {
	List linhas = new ArrayList();
	String txt = null;
	String linha;
	List lista;
	BuscaProduto buscaProduto = new BuscaProduto();
	Double quantidadeTotalProdutos = 0.0;
	
	protected void doGet(HttpServletRequest request,	
            HttpServletResponse response)
            throws IOException, ServletException {

		response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        txt = buscaProduto.buscaTxt();
        
	    if(txt != null){
	    	lista = Arrays.asList(txt.split("-"));
	    }

	    if(lista != null){
	    	out.println("<html>");
	        out.println("<body style='text-align: center;'>");
	        out.println("<h2>Finanças</h2>");
	        		
	        for(int i = 0; i < lista.size(); i++){
	        	if(!lista.get(i).equals("\n")){
        	 		if(lista.get(i) != null){
        	 			String produto = (String) lista.get(i);
        	 			if(produto != null){
        	 				String [] produtoAtributos = produto.split(";");
        	 				if(produtoAtributos != null && produtoAtributos.length != 0){
        	 					if(produtoAtributos[0].indexOf(":") != -1){
        	 						String[] nomeProduto = produtoAtributos[0].toString().split(":");
        	 						String[] precoProduto = produtoAtributos[1].toString().split(":");
        	 						String[] unidadeProduto = produtoAtributos[2].toString().split(":");
        	 						String[] quantidadeProduto = produtoAtributos[3].toString().split(":");
        	 						Double preco = Double.parseDouble(precoProduto[1]);
        	 						Double quantidade = Double.parseDouble(quantidadeProduto[1]);
        	 						quantidadeTotalProdutos = quantidadeTotalProdutos + quantidade;
        	 						Double valorTotal = preco + quantidade;
        	 						
        	 				        out.println("<div>");
        	 				        out.println("Produto: " + nomeProduto[1]);
        	 				        out.println("</div>");
        	 				        out.println("<div>");
        	 				        out.println("Preço: " + precoProduto[1]);
        	 				        out.println("</div>");
        	 				        out.println("<div>");
        	 				        out.println("Unidade: " + unidadeProduto[1]);
        	 				        out.println("</div>");
        	 				        out.println("<div>");
        	 				        out.println("Quantidade: " + quantidadeProduto[1]);
        	 				        out.println("</div>");
        	 				        out.println("<div style='margin-bottom: 10px'>");
        	 				        out.println("Valor Total: " + valorTotal.toString());
        	 				        out.println("</div>");
        	 				  
        	 					}
        	 				}
        	 			}
        	 		}
        	 	}
	        }
	        out.println("<div>");
		    out.println("Quantidade de produtos em estoque: " + quantidadeTotalProdutos);
		    out.println("</div>");
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