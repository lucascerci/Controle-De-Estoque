package br.com.poo.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(
        name = "adicionaProduto",
        urlPatterns = {"/adicionaProduto"}
)
public class AdicionaProduto extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private File arquivo;
 	Produto produto = new Produto();
 	String erro = null;
	 
 	protected void service(HttpServletRequest request,	
             HttpServletResponse response)
             throws IOException, ServletException {

         PrintWriter out = response.getWriter();
         
         if(!request.getParameter("nome").isEmpty()) {
             String nome = request.getParameter("nome");
             produto.setNome(nome);
         } else {
        	 erro = "O nome não pode ser nulo";
         }
         
         if(!request.getParameter("preco").isEmpty()) {
             String preco = request.getParameter("preco");
             produto.setPreco(Double.parseDouble(preco));
         } else {
        	 if(erro != null) {
        		 erro = erro + " || O preço não pode ser nulo";
        	 } else {
        		 erro = "O preço não pode ser nulo";
        	 }
         }
         
         if(!request.getParameter("unidade").isEmpty()) {
             String unidade = request.getParameter("unidade");
             produto.setUnidade(unidade);
         } else {
        	 if(erro != null) {
            	 erro = erro + " || A unidade não pode ser nula";
        	 } else {
        		 erro = "A unidade não pode ser nula";
        	 }
        	 
         }
         
         if(!request.getParameter("quantidade").isEmpty()) {
             String quantidade = request.getParameter("quantidade");
             produto.setQuantidade(Integer.parseInt(quantidade));
         } else {
        	 if(erro != null) {
        		 erro = erro + " || A quantidade não pode ser nula";
        	 }else {
        		 erro = "A quantidade não pode ser nula";
        	 }
         }
       
        if(erro == null) {
        	if(this.Escrever(produto)){
           	 out.println("<html>");
                out.println("<body style='text-align: center; margin-top: 15%;'>");
                out.println("Produto " + produto.getNome() +
                        " adicionado com sucesso");    
                out.println("<div><a href='/TrabPoo/adicionaProduto.html'>Adicionar Outro</a></div>");
                out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
                out.println("</body>");
                out.println("</html>");
           }else{
           	 out.println("<html>");
                out.println("<body>");
                out.println("Produto " + produto.getNome() +
                        " nao foi adicionado, devido a um erro nao tratado.");   
                out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
                out.println("</body>");
                out.println("</html>");
           }
        }else {
        	out.println("<html>");
            out.println("<body>");
            out.println("Erro ao cadastrar um produto: " + erro);    
            out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
            out.println("</body>");
            out.println("</html>");
        }
     }
	 
	 public Boolean Escrever(Produto produto) {
		 	File arquivo = new File("banco.txt");
			
			FileWriter inserindo;
			
			try {
				inserindo = new FileWriter(arquivo, true);
				inserindo.write("Nome:" + produto.getNome() + ";");
				inserindo.write("Preco:" + produto.getPreco() + ";");
				inserindo.write("Unidade:" + produto.getUnidade() + ";");
				inserindo.write("Quantidade:" + produto.getQuantidade());
				inserindo.write("-\r\n");
				inserindo.close(); 
				return true;
				
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
}




