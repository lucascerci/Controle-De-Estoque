package br.com.poo.servlet;

import java.io.File;
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
	Produto produtoEditar = new Produto();
	
	 protected void service(HttpServletRequest request,	
             HttpServletResponse response)
             throws IOException, ServletException {

         PrintWriter out = response.getWriter();

         if(!request.getParameter("nome").isEmpty()) {
             String nome = request.getParameter("nome");
             produtoEditar.setNome(nome);
         }else {
        	 out.println("<html>");
             out.println("<body>");
             out.println("<p>Você precisa colocar o nome do produto a ser modificado.</p>");
             out.println("<div><a href='/TrabPoo/editaProduto.html'>Voltar</a></div>");
             out.println("</body>");
             out.println("</html>");
         }
         
         if(!request.getParameter("preco").isEmpty()) {
             String preco = request.getParameter("preco");
             produtoEditar.setPreco(Double.parseDouble(preco));
         }
         
         if(!request.getParameter("unidade").isEmpty()) {
             String unidade = request.getParameter("unidade");
             produtoEditar.setUnidade(unidade);
         }
         
         if(!request.getParameter("quantidade").isEmpty()) {
             String quantidade = request.getParameter("quantidade");
             produtoEditar.setQuantidade(Integer.parseInt(quantidade));
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
        	 					if(produtoAtributos[0].indexOf(":") != -1){
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
	        }
         
         if(isExiste){
        	 File txtOld = new File("banco.txt");
        	 txtOld.delete();
        	 FileWriter arquivo = new FileWriter("banco.txt", true);
        	 String [] produtoEditadoAtt = produtoEditado.split(";");
        	 String novoPreco = null;
        	 String novaQuantidade = null;
        	 String novaUnidade =  null;
        	 String novoProdutoEditado = null;
        	 
        	 if(produtoEditar.getPreco() != null){
        		 String [] preco = produtoEditadoAtt[1].split(":");
        		 novoPreco = preco[0] + ":" + produtoEditar.getPreco() + ";";
        	 }
        	 if(produtoEditar.getUnidade() != null){
        		 String[] unidade = produtoEditadoAtt[2].split(":");
        		 novaUnidade = unidade[0] + ":" + produtoEditar.getUnidade() + ";";
        	 }
        	 if(produtoEditar.getQuantidade() != null){
        		 String [] quantidade = produtoEditadoAtt[3].split(":");
        		 novaQuantidade = quantidade[0] + ":" + produtoEditar.getQuantidade() + ";";
        	 }
        	 
        	 if(novoPreco != null){
            	 novoProdutoEditado = produtoEditadoAtt[0] + ";" + novoPreco;
        	 }else{
        		 novoProdutoEditado = produtoEditadoAtt[0] + ";" + produtoEditadoAtt[1];
        	 }
        	 
        	 if(novaQuantidade != null){
        		 novoProdutoEditado = novoProdutoEditado + novaQuantidade;
        	 }else{
        		 novoProdutoEditado = novoProdutoEditado + produtoEditadoAtt[2];
        	 }	
        	
        	 if(novaUnidade != null){
        		 novoProdutoEditado = novoProdutoEditado + novaUnidade;
        	 }else{
        		 novoProdutoEditado = novoProdutoEditado + produtoEditadoAtt[3];
        	 }
        	
        	 String[] txtEditado = txt.replaceAll(produtoEditado + "-", novoProdutoEditado + "-").split("-");
        	 
        	 if(txtEditado != null){
        		 for(int i = 0; i < txtEditado.length; i++){
        			 if(txtEditado[i] != produtoEditado){
        				 arquivo.write(txtEditado[i] + "\r\n");
        			 }
            	 }
        	 }
        	 
        	 arquivo.close();
        	 
        	 out.println("<html>");
             out.println("<body>");
             out.println("<p>O produto " + produtoEditadoNome + " foi editado com sucesso</p>");
             out.println("<div><a href='/TrabPoo/editaProduto.html'>Editar Outro</a></div>");
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
