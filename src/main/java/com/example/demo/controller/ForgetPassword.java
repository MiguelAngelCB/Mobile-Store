package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.entity.Recover;
import com.example.demo.model.CRUDclient;
import com.google.gson.Gson;

import org.json.JSONArray;

@WebServlet("/forgetPasswordClient")
public class ForgetPassword extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    StringBuilder sb = new StringBuilder();
    BufferedReader br = request.getReader();
    String str = null;
    while ((str = br.readLine()) != null) {
      sb.append(str);
    }
    String json = sb.toString();
    System.out.println(json);

    Gson g = new Gson();
    Recover recover = g.fromJson(json, Recover.class);

    CRUDclient crudClient = new CRUDclient();
    JSONArray arrayJson = new JSONArray();
    arrayJson = crudClient.forgetPassword(recover);

    response.getWriter().write((arrayJson).toString());
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }
}
