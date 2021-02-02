package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.DAO.procedure.CallerClient;

@WebServlet("/unlockUser")
public class UnlockUser extends HttpServlet{
    
    private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    String email=request.getParameter("email");
    String uid=request.getParameter("uuid");
    System.out.println(email);
    System.out.println(uid);
    try {
      CallerClient callerClient = new CallerClient();
      if(callerClient.unlockUser(email, uid)){
        System.out.println("Usuario desbloqueado");
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
}
