package com.example.demo.verify;

import java.sql.SQLException;
import java.util.HashMap;

import com.example.demo.DAO.procedure.CallerClient;
import com.example.demo.entity.Login;
import com.example.demo.error.ErrorVerify;

public class VerifyUpdateLoginClient {
  CallerClient callerClient = null;
  Login login;

  public VerifyUpdateLoginClient(Login login) throws ClassNotFoundException, SQLException {
    callerClient = new CallerClient();
    this.login = login;
  }

  public HashMap<String, ErrorVerify> verify() throws SQLException {
    HashMap<String, ErrorVerify> errors = new HashMap<String, ErrorVerify>();
    if (!callerClient.isFreeData(login.getUser(),"User")) {
      errors.put(login.getUser(), ErrorVerify.EXISTLOGIN);
    }
    return errors;
  }
}
