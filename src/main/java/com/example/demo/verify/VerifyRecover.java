package com.example.demo.verify;

import java.sql.SQLException;
import java.util.HashMap;

import com.example.demo.DAO.procedure.CallerClient;
import com.example.demo.entity.Recover;
import com.example.demo.error.ErrorVerify;

public class VerifyRecover {
    CallerClient callerClient = null;
    Recover recover;
  
    public VerifyRecover(Recover recover) throws ClassNotFoundException, SQLException {
      callerClient = new CallerClient();
      this.recover = recover;
    }
  
    public HashMap<String, ErrorVerify> verify() throws SQLException {
      HashMap<String, ErrorVerify> errors = new HashMap<String, ErrorVerify>();
      if (!callerClient.checkingRecover(recover)) {
        errors.put(recover.getNif(), ErrorVerify.BADRECOVER);
        errors.put(recover.getEmail(), ErrorVerify.BADRECOVER);
      }
      return errors;
    }
      
}
