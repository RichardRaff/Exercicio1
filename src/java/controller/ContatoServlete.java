/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.corba.se.impl.activation.ServerMain;
import controller.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Contato;

/**
 *
 * @author Richard Raff
 */
public class ContatoServlete extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ContatoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ContatoServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Adição
        if (request.getParameter("cadastrar") != null) {

            // Instanciando variaveis para receber os dados do formulário
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");
            String telefone = request.getParameter("telefone");

            
            Contato c = new Contato();
            c.setNome(nome);
            c.setSobrenome(sobrenome);
            c.setEmail(email);
            c.setTelefone(telefone);
            c.setVisibilidade(1);

            ContatoJpaController CJPA = new ContatoJpaController(Persistence.createEntityManagerFactory("Exercicio1PU"));
            CJPA.create(c);

            // Redirecionamento de site
            response.sendRedirect("index.jsp");
        }

        // Remoção
        if (request.getParameter("excluir") != null) {
            int id = Integer.parseInt(request.getParameter("excluir"));
            ContatoJpaController CJPA = new ContatoJpaController(Persistence.createEntityManagerFactory("Exercicio1PU"));
            try {
                CJPA.destroy(id);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ContatoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("index.jsp");
        }

        // Atualização
        if (request.getParameter("atualizar") != null) {
            // Pegando as informações do modal
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");
            String telefone = request.getParameter("telefone");

            // Instanciando o Contato e adicionando as informações do modal nele
            Contato c = new Contato();
            c.setIdcontato(id);
            c.setNome(nome);
            c.setSobrenome(sobrenome);
            c.setEmail(email);
            c.setTelefone(telefone);
            c.setVisibilidade(1);

            ContatoJpaController CJPA = new ContatoJpaController(Persistence.createEntityManagerFactory("Exercicio1PU"));
            try {
                CJPA.edit(c);
            } catch (Exception e) {
                Logger.getLogger(ContatoServlet.class.getName()).log(Level.SEVERE, null, e);
            }

            response.sendRedirect("index.jsp");
        }
        
        // Pesquisa
        if(request.getParameter("pesquisar") !=null){
            //enviar dado aqui referente ao campo de pesquisa
            String pesquisa = request.getParameter("pesqNome");
            //pegar esse dado e enviar para a página em que você pretende usar a informação
            request.setAttribute("retornoPesq", pesquisa);
            RequestDispatcher rd = request.getRequestDispatcher("Pesquisa.jsp");
            rd.forward(request, response);
        }

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
