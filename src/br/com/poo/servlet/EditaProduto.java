package br.com.poo.servlet;

import java.io.FileWriter;
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
import br.com.poo.servlet.BuscaProduto;
import br.com.poo.servlet.AdicionaProduto;

@WebServlet(
        name = "editaProduto",
        urlPatterns = {"/editaProduto"}
)
public class EditaProduto extends HttpServlet {
	
	
	BuscaProduto buscaProduto = new BuscaProduto();
	AdicionaProduto adicionaProduto = new AdicionaProduto();
	String txt;
	List<String> lista;
	Boolean isExiste = false;
	String produtoEditado;
	String produtoEditadoNome;
	
	 protected void service(HttpServletRequest request,	
             HttpServletResponse response)
             throws IOException, ServletException {

         PrintWriter out = response.getWriter();

         // pegando os parâmetros do request
         String nome = request.getParameter("nome");
         String preco = request.getParameter("preco");
         String unidade = request.getParameter("unidade");
         String quantidade = request.getParameter("quantidade");
         
         Produto produtoEditar = new Produto();
         produtoEditar.setNome(nome);
         produtoEditar.setPreco(Double.parseDouble(preco));
         produtoEditar.setUnidade(unidade);
         produtoEditar.setQuantidade(Integer.parseInt(quantidade));
         
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
                	        	if(nomeProduto[1] != null && nomeProduto[1].equals(produtoEditar.getNome())){
                	        		produtoEditadoNome = nomeProduto[1];
                	        		produtoEditado = produto;
                	        		isExiste = true;
                	        	}
        	 				}
        	 			}
        	 		}
        	 	}
	        }
         
         if(isExiste){
        	 FileWriter arquivo = new FileWriter("banco.txt", true);
        	 String[] txtEditado = txt.split("-" + produtoEditado + "-");
        	 arquivo.write(txtEditado[0] + txtEditado[1]);
        	 arquivo.close();
        	 adicionaProduto.Escrever(produtoEditar);
        	 
        	 out.println("<html>");
             out.println("<body>");
             out.println("<p>O produto " + produtoEditadoNome + "</p>");
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
