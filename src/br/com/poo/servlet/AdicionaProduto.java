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
	
	 protected void service(HttpServletRequest request,	
             HttpServletResponse response)
             throws IOException, ServletException {

         PrintWriter out = response.getWriter();

         // pegando os parâmetros do request
         String nome = request.getParameter("nome");
         String preco = request.getParameter("preco");
         String unidade = request.getParameter("unidade");
         String quantidade = request.getParameter("quantidade");

         // fazendo a conversão da data

         // monta um objeto contato
     	Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(Double.parseDouble(preco));
        produto.setUnidade(unidade);
        produto.setQuantidade(Integer.parseInt(quantidade));
       
        if(this.Escrever(produto)){
        	 out.println("<html>");
             out.println("<body>");
             out.println("Contato " + produto.getNome() +
                     " adicionado com sucesso");    
             out.println("<div><a href='/TrabPoo/produto.html'>Voltar</a></div>");
             out.println("</body>");
             out.println("</html>");
        }else{
        	 out.println("<html>");
             out.println("<body>");
             out.println("Contato " + produto.getNome() +
                     " não foi adicionado, devido a um erro nao tratado.");   
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




