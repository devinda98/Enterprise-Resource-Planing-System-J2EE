/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Beans.loginbeans;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ALPHA
 */
@WebServlet(name = "Login_Ceo", urlPatterns = {"/Login_Ceo"})
public class Login_Ceo extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
         try {
            
            loginbeans ub = new loginbeans();
            ub.setUsername(request.getParameter("username"));
            ub.setPassword(request.getParameter("pass"));
            
            String pass = ub.getPassword();
            String UserName= ub.getUsername();
            Cookie loginCookie = new Cookie("user",UserName);
            loginCookie.setMaxAge(30*60);
            response.addCookie(loginCookie);
            
            String sql = "SELECT * FROM `ceo` WHERE `UserName` ='"+UserName+"' AND `Password` = '"+pass+"'";
            ResultSet search = DB.search(sql);
            if (search.next()) {

                HttpSession session = request.getSession(true);
                session.setAttribute("user", UserName);
                RequestDispatcher r = request.getRequestDispatcher("Ceo_dashboard.jsp?message=Hello+" + UserName + "");
                r.forward(request, response);
                
            } else {
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Ceo_Login.jsp?message=<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
            }
        } catch (Exception ex) {
             PrintWriter out = response.getWriter();
             out.print(ex);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
