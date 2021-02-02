package com.example.demo.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.DAO.procedure.CallerClient;
import com.example.demo.entity.Client;
import com.example.demo.entity.Login;
import com.example.demo.entity.Recover;
import com.example.demo.error.ErrorValidate;
import com.example.demo.error.ErrorVerify;
import com.example.demo.util.AddErrorArrayError;
import com.example.demo.util.GetDataControlFromValue;
import com.example.demo.util.SendEmail;
import com.example.demo.validate.ValidatorLengthComposite;
import com.example.demo.validate.ValidatorValueComposite;
import com.example.demo.verify.VerifyClient;
import com.example.demo.verify.VerifyLoginClient;
import com.example.demo.verify.VerifyRecover;
import com.example.demo.verify.VerifyUpdateClient;
import com.example.demo.verify.VerifyUpdateLoginClient;

import org.json.JSONArray;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import Thread.CustomThread;
import Thread.StateBlock;
import Thread.StateEmail;
import Thread.StateRecover;
import Thread.StateRegister;
import net.minidev.json.JSONObject;

public class CRUDclient {

  JSONArray arrayJson;
  HashMap<String, ArrayList<ErrorValidate>> errorsValidation;
  HashMap<String, ErrorVerify> errorsVerification;
  SendEmail sendEmail = new SendEmail();
  CallerClient callerClient;
  private int myIdClient;
  private StateEmail state;
  private CustomThread emailThread;

  public CRUDclient() {
    arrayJson = new JSONArray();
    errorsValidation = new HashMap<String, ArrayList<ErrorValidate>>();
    errorsVerification = new HashMap<String, ErrorVerify>();
  }

  public JSONArray addClient(Client client) {
    errorsValidation = getErrorsLength(client);
    Boolean error = false;
    if (errorsValidation.isEmpty()) {
      errorsValidation = getErrorsValue(client);
      if (errorsValidation.isEmpty()) {
        try {
          errorsVerification = getErrorsVerify(client);
        } catch (SQLException e) {
          e.printStackTrace();
        }
        if (errorsVerification.isEmpty()) {
          try {
            callerClient = new CallerClient();
            if (callerClient.addClient(client)) {
              myIdClient = callerClient.getIdClient(client.getNif());
              successfulAction("addClient","ok");
              successfulClient();
              this.state=new StateRegister(client.getEmail());
              this.emailThread = new CustomThread(this.state);
            } else {
              failedAction("addClient");
            }
          } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
          }

        } else {
          for (java.util.Map.Entry<String, ErrorVerify> entry : errorsVerification.entrySet()) {
            JSONObject oneJson = new JSONObject();
            oneJson.put("messageErrorControl", entry.getValue().getMsgEs());
            oneJson.put("messageValueControl", entry.getKey());
            oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(client, entry.getKey()));
            arrayJson.put(oneJson);
          }
          return arrayJson;
        }

      } else {
        error = true;
      }
    } else {
      error = true;
    }
    if (error) {
      for (java.util.Map.Entry<String, ArrayList<ErrorValidate>> entry : errorsValidation.entrySet()) {
        ArrayList<ErrorValidate> myerror = entry.getValue();
        myerror.forEach((n) -> {
          JSONObject oneJson = new JSONObject();
          oneJson.put("messageErrorControl", n.getMsgEs());
          oneJson.put("messageValueControl", entry.getKey());
          oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(client, entry.getKey()));
          arrayJson.put(oneJson);
        });
      }
    }
    return arrayJson;
  }

  public JSONArray checkLoginClient(Login login) {
    errorsValidation = getErrorsLength(login);
    Boolean error = false;
    if (errorsValidation.isEmpty()) {
      errorsValidation = getErrorsValue(login);
      if (errorsValidation.isEmpty()) {
        try {
          callerClient = new CallerClient();
          myIdClient = callerClient.getIdUser(login.getUser());
          if (!callerClient.isBlocked(myIdClient)) {
          errorsVerification = getErrorsVerify(login);
          if (errorsVerification.isEmpty()) {
              System.out.println(login.getUser());
              System.out.println(myIdClient);
              successfulAction("loginClient","ok");
              successfulClient();
          } else {
            setContadorErrores(getContadorErrores()+1);
            System.out.println(getContadorErrores());
            if (getContadorErrores()==getLimiteErrores()) {
              setContadorErrores(0);
              try {
                callerClient = new CallerClient();
                String nombreUsuario=login.getUser();
                if (callerClient.isUser(nombreUsuario)) {
                  callerClient.blockUser(nombreUsuario);
                  this.state = new StateBlock(callerClient.getEmail(nombreUsuario));
                  this.emailThread = new CustomThread(this.state);
                  JSONObject oneJson = new JSONObject();
                  oneJson.put("error", 30);
                  oneJson.put("emailblocked", "ok");
                  arrayJson.put(oneJson);
                }else{
                  JSONObject oneJson = new JSONObject();
                  oneJson.put("idButton","submit");
                  oneJson.put("idErrorBox","boxerror_");
                  oneJson.put("messageErrorBlock","bloqueado");
                  oneJson.put("error",40);
                  oneJson.put("tiempoBloqueo",10);
                  arrayJson.put(oneJson);
                  return arrayJson;
                }
              } catch (Exception e) {
              }
            }else{
              failedAction("loginClient");
            }
            for (java.util.Map.Entry<String, ErrorVerify> entry : errorsVerification.entrySet()) {
              JSONObject oneJson = new JSONObject();
              oneJson.put("messageErrorControl", entry.getValue().getMsgEs());
              oneJson.put("messageValueControl", entry.getKey());
              oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(login, entry.getKey()));
              arrayJson.put(oneJson);
            }
            return arrayJson;
          }
          }else{
            JSONObject oneJson = new JSONObject();
            oneJson.put("error", 20);
            oneJson.put("blocked", "ok");
            arrayJson.put(oneJson);  
          }
        } catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
        }
      } else {
        error = true;
      }
    } else {
      error = true;
    }
    if (error) {
      for (java.util.Map.Entry<String, ArrayList<ErrorValidate>> entry : errorsValidation.entrySet()) {
        ArrayList<ErrorValidate> myerror = entry.getValue();
        myerror.forEach((n) -> {
          JSONObject oneJson = new JSONObject();
          oneJson.put("messageErrorControl", n.getMsgEs());
          oneJson.put("messageValueControl", entry.getKey());
          oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(login, entry.getKey()));
          arrayJson.put(oneJson);
        });
      }
    }

    return arrayJson;

  }

  public JSONArray forgetPassword(Recover recover) {
    errorsValidation = getErrorsLength(recover);
    Boolean error = false;
    if (errorsValidation.isEmpty()) {
      errorsValidation = getErrorsValue(recover);
      if (errorsValidation.isEmpty()) {
        try {
          errorsVerification = getErrorsVerify(recover);
        } catch (SQLException e) {
          e.printStackTrace();
        }
        if (errorsVerification.isEmpty()) {
          try {
            this.callerClient=new CallerClient();
            if (callerClient.changePassword(recover.getEmail())) {
              // successfulAction("forgetPassword", "ok");
              this.state=new StateRecover(recover.email);
              this.emailThread = new CustomThread(this.state);
              JSONObject oneJson = new JSONObject();
              oneJson.put("error", 0);
              oneJson.put("validation", "ok");
              oneJson.put("verification", "ok");
              oneJson.put("forgetPassword", "ok");
              oneJson.put("reload", "ok");
              arrayJson.put(oneJson);
            } else {
              System.out.println("Fallo en updateCredential()");
            }
          } catch (Exception e) {
          }
        } else {
          for (java.util.Map.Entry<String, ErrorVerify> entry : errorsVerification.entrySet()) {
            JSONObject oneJson = new JSONObject();
            oneJson.put("messageErrorControl", entry.getValue().getMsgEs());
            oneJson.put("messageValueControl", entry.getKey());
            oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(recover, entry.getKey()));
            arrayJson.put(oneJson);
          }
          return arrayJson;
        }

      } else {
        error = true;
      }
    } else {
      error = true;
    }
    if (error) {
      for (java.util.Map.Entry<String, ArrayList<ErrorValidate>> entry : errorsValidation.entrySet()) {
        ArrayList<ErrorValidate> myerror = entry.getValue();
        myerror.forEach((n) -> {
          JSONObject oneJson = new JSONObject();
          oneJson.put("messageErrorControl", n.getMsgEs());
          oneJson.put("messageValueControl", entry.getKey());
          oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(recover, entry.getKey()));
          arrayJson.put(oneJson);
        });
      }
    }
    return arrayJson;
  }

  public JSONArray updateDataClient(Client client) {
    errorsValidation = getErrorsLength(client);
    Boolean error = false;
    if (errorsValidation.isEmpty()) {
      errorsValidation = getErrorsValue(client);
      if (errorsValidation.isEmpty()) {
        try {
          callerClient = new CallerClient();
          errorsVerification = getErrorsVerifyUpdate(client);
        } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
        if (errorsVerification.isEmpty()) {
          try {
            if (callerClient.updateClient(client)) {
              myIdClient = callerClient.getIdClient(client.getNif());
              successfulAction("UpdateClient","ok");
              successfulClient();
            } else {
              JSONObject oneJson = new JSONObject();
              oneJson.put("error", 50);
              arrayJson.put(oneJson);
            }
            return arrayJson;
          } catch (SQLException e) {

            e.printStackTrace();
          }

        } else {
          for (java.util.Map.Entry<String, ErrorVerify> entry : errorsVerification.entrySet()) {
            JSONObject oneJson = new JSONObject();
            oneJson.put("messageErrorControl", entry.getValue().getMsgEs());
            oneJson.put("messageValueControl", entry.getKey());
            oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(client, entry.getKey()));
            arrayJson.put(oneJson);
          }
          return arrayJson;
        }

      } else {
        error = true;
      }
    } else {
      error = true;
    }
    if (error) {
      for (java.util.Map.Entry<String, ArrayList<ErrorValidate>> entry : errorsValidation.entrySet()) {
        ArrayList<ErrorValidate> myerror = entry.getValue();
        myerror.forEach((n) -> {
          JSONObject oneJson = new JSONObject();
          oneJson.put("messageErrorControl", n.getMsgEs());
          oneJson.put("messageValueControl", entry.getKey());
          oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(client, entry.getKey()));
          arrayJson.put(oneJson);
        });
      }
    }
    return arrayJson;
  }

  public JSONArray updateLoginClient(Login login) {
    errorsValidation = getErrorsLength(login);
    Boolean error = false;
    if (errorsValidation.isEmpty()) {
      errorsValidation = getErrorsValue(login);
      if (errorsValidation.isEmpty()) {
        try {
          callerClient = new CallerClient();
          errorsVerification = getErrorsVerifyUpdate(login);
          if (errorsVerification.isEmpty()) {
            if (callerClient.updateLogin(login)) {
              myIdClient = callerClient.getIdUser(login.getUser());
              System.out.println(myIdClient);
              System.out.println(login.getUser());
              successfulAction("updateLogin","ok");
              successfulClient();
              return arrayJson;
            }else{
              JSONObject oneJson = new JSONObject();
              oneJson.put("error", 50);
              arrayJson.put(oneJson);
              return arrayJson;
            }
            } else {
            for (java.util.Map.Entry<String, ErrorVerify> entry : errorsVerification.entrySet()) {
              JSONObject oneJson = new JSONObject();
              oneJson.put("messageErrorControl", entry.getValue().getMsgEs());
              oneJson.put("messageValueControl", entry.getKey());
              oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(login, entry.getKey()));
              arrayJson.put(oneJson);
            }
            return arrayJson;
          }
        } catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
        }
      } else {
        error = true;
      }
    } else {
      error = true;
    }
    if (error) {
      for (java.util.Map.Entry<String, ArrayList<ErrorValidate>> entry : errorsValidation.entrySet()) {
        ArrayList<ErrorValidate> myerror = entry.getValue();
        myerror.forEach((n) -> {
          JSONObject oneJson = new JSONObject();
          oneJson.put("messageErrorControl", n.getMsgEs());
          oneJson.put("messageValueControl", entry.getKey());
          oneJson.put("messageNameControl", GetDataControlFromValue.getDataControlClient(login, entry.getKey()));
          arrayJson.put(oneJson);
        });
      }
    }

    return arrayJson;

  }

  private HashMap<String, ArrayList<ErrorValidate>> getErrorsValue(Client client) {
    HashMap<String, ArrayList<ErrorValidate>> errorsAll = new HashMap<>();
    errorsAll = new AddErrorArrayError(new ValidatorValueComposite().validate(client), errorsAll).getErrorsAll();
    return errorsAll;
  }

  private HashMap<String, ArrayList<ErrorValidate>> getErrorsValue(Login login) {
    HashMap<String, ArrayList<ErrorValidate>> errorsAll = new HashMap<>();
    errorsAll = new AddErrorArrayError(new ValidatorValueComposite().validate(login), errorsAll).getErrorsAll();
    return errorsAll;
  }

  private HashMap<String, ArrayList<ErrorValidate>> getErrorsValue(Recover recover) {
    HashMap<String, ArrayList<ErrorValidate>> errorsAll = new HashMap<>();
    errorsAll = new AddErrorArrayError(new ValidatorValueComposite().validate(recover), errorsAll).getErrorsAll();
    return errorsAll;
  }

  private HashMap<String, ArrayList<ErrorValidate>> getErrorsLength(Client client) {
    HashMap<String, ArrayList<ErrorValidate>> errorsAll = new HashMap<>();
    errorsAll = new AddErrorArrayError(new ValidatorLengthComposite().validate(client), errorsAll).getErrorsAll();
    return errorsAll;
  }

  private HashMap<String, ArrayList<ErrorValidate>> getErrorsLength(Login login) {
    HashMap<String, ArrayList<ErrorValidate>> errorsAll = new HashMap<>();
    errorsAll = new AddErrorArrayError(new ValidatorLengthComposite().validate(login), errorsAll).getErrorsAll();
    return errorsAll;
  }

  private HashMap<String, ArrayList<ErrorValidate>> getErrorsLength(Recover recover) {
    HashMap<String, ArrayList<ErrorValidate>> errorsAll = new HashMap<>();
    errorsAll = new AddErrorArrayError(new ValidatorLengthComposite().validate(recover), errorsAll).getErrorsAll();
    return errorsAll;
  }

  private HashMap<String, ErrorVerify> getErrorsVerify(Client client) throws SQLException {
    VerifyClient verifyClient = null;
    try {
      verifyClient = new VerifyClient(client);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return verifyClient.verify();
  }

  private HashMap<String, ErrorVerify> getErrorsVerify(Login login) throws SQLException {
    VerifyLoginClient verifyClient = null;
    try {
      verifyClient = new VerifyLoginClient(login);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return verifyClient.verify();
  }
  
  private HashMap<String, ErrorVerify> getErrorsVerifyUpdate(Client client) throws SQLException {
      VerifyUpdateClient verifyClient = null;
      try {
        verifyClient = new VerifyUpdateClient(client);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return verifyClient.verify();
    }

  private HashMap<String, ErrorVerify> getErrorsVerifyUpdate(Login login) throws SQLException {
    VerifyUpdateLoginClient verifyClient = null;
    try {
      verifyClient = new VerifyUpdateLoginClient(login);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return verifyClient.verify();
  }

  private HashMap<String, ErrorVerify> getErrorsVerify(Recover recover) throws SQLException {
    VerifyRecover verifyRecover = null;
    try {
      verifyRecover = new VerifyRecover(recover);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return verifyRecover.verify();
  }

  private void successfulClient(){
    RequestContextHolder.currentRequestAttributes().setAttribute("activePage", "client",
    RequestAttributes.SCOPE_SESSION);
    RequestContextHolder.currentRequestAttributes().setAttribute("idClient", myIdClient,
    RequestAttributes.SCOPE_SESSION);
  }

  private void successfulAction(String operation, String reload) {
    JSONObject oneJson = new JSONObject();
    oneJson.put("error", 0);
    oneJson.put("validation", "ok");
    oneJson.put("verification", "ok");
    oneJson.put(operation, "ok");
    oneJson.put("reload", reload);
    oneJson.put("idClient", myIdClient);
    arrayJson.put(oneJson);
  }

  private void failedAction(String operation) {
    JSONObject oneJson = new JSONObject();
    oneJson.put("error", 10);
    oneJson.put("validation", "ok");
    oneJson.put("verification", "ok");
    oneJson.put(operation, "error");
    arrayJson.put(oneJson);
  }

  private int getLimiteErrores() {
    return (int) RequestContextHolder.currentRequestAttributes().getAttribute("limiteErrores",
    RequestAttributes.SCOPE_SESSION);
  }

  private int getContadorErrores() {
    return (int) RequestContextHolder.currentRequestAttributes().getAttribute("contadorErrores",
    RequestAttributes.SCOPE_SESSION);
  }

  private void setContadorErrores(int number) {
    RequestContextHolder.currentRequestAttributes().setAttribute("contadorErrores", number,
          RequestAttributes.SCOPE_SESSION);
  }

  /*
   * private void errorVerification(SuperClient superclient) {
   * GetDataControlFromValue getDataControlFromValue = new
   * GetDataControlFromValue(); for (java.util.Map.Entry<String, ErrorVerify>
   * entry : errorsVerification.entrySet()) { JSONObject oneJson = new
   * JSONObject(); oneJson.put("messageErrorControl",
   * entry.getValue().getMsgEs()); oneJson.put("messageValueControl",
   * entry.getKey()); oneJson.put("messageNameControl",
   * getDataControlFromValue.getDataControlClient(superclient, entry.getKey()));
   * arrayJson.put(oneJson); }
   * 
   * }
   */
}
