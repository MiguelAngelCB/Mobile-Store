package com.example.demo.DAO.procedure;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import com.example.demo.DAO.GetConnectionMySql;
import com.example.demo.entity.Client;
import com.example.demo.entity.IpLocation;
import com.example.demo.entity.Login;
import com.example.demo.entity.Recover;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import net.minidev.json.JSONArray;

public class CallerClient extends GetConnectionMySql {

  public CallerClient() throws SQLException, ClassNotFoundException {
    super();
  }

  public Boolean existEmail(String email) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call 	EmailExist_client(?, ?)}");
    cstmt.setString(1, email);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public Boolean existNif(String nif) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call 	NifExist_client(?, ?)}");
    cstmt.setString(1, nif);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public Boolean existCP(String cp) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call 	CPExist_postalcode(?, ?)}");
    cstmt.setString(1, cp);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public Boolean existMobile(String cp) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call 	MobileExist_client(?, ?)}");
    cstmt.setString(1, cp);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public Boolean addClient(Client client) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call AddClient(?,?,?,?,?,?,?,?,?)}");
    cstmt.setString(1, client.getName());
    cstmt.setString(2, client.getSurname());
    cstmt.setString(3, client.getNif());
    cstmt.setString(4, client.getMobile());
    cstmt.setString(5, client.getEmail());
    cstmt.setString(6, client.getBirthdate());
    cstmt.setString(7, client.getPostalCode());
    cstmt.setString(8, client.getAddress());
    cstmt.registerOutParameter(9, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(9);
  }

  public Login getLoginInit(String email) throws SQLException {
    Login login = new Login();
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call 	GetLoginInit(?, ?, ?)}");
    cstmt.setString(1, email);
    cstmt.registerOutParameter(2, Types.VARCHAR);
    cstmt.registerOutParameter(3, Types.VARCHAR);
    cstmt.execute();
    login.setUser(cstmt.getString(2));
    login.setPassword(cstmt.getString(3));
    return login;
  }

  public Boolean checkingLoginClient(Login login) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call CheckingLoginClient(?, ?, ?)}");
    cstmt.setString(1, login.getUser());
    cstmt.setString(2, login.getPassword());
    cstmt.registerOutParameter(3, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(3);
  }
  
  public Boolean checkingRecover(Recover recover) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call checkRecover(?, ?, ?)}");
    cstmt.setString(1, recover.getNif());
    cstmt.setString(2, recover.getEmail());
    cstmt.registerOutParameter(3, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(3);
  }
  
  public Boolean changePassword(String myEmail) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call changePassword(?, ?)}");
    cstmt.setString(1, myEmail);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public Boolean isUser(String user) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  userExist_client(?, ?)}");
    cstmt.setString(1, user);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public String getEmail(String user) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  getEmail(?, ?)}");
    cstmt.setString(1, user);
    cstmt.registerOutParameter(2, Types.VARCHAR);
    cstmt.execute();
    return cstmt.getString(2);
  }

  public Boolean blockUser(String user) throws SQLException {
    String uid = UUID.randomUUID().toString();
    String clave=uid.substring(0,Math.min(uid.length(), 50));
    System.out.println(clave);
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  blockUser(?, ?, ?)}");
    cstmt.setString(1, user);
    cstmt.setString(2, clave);
    cstmt.registerOutParameter(3, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(3);
  }

  public String getUID(String email) throws SQLException{
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  getUID(?, ?)}");
    cstmt.setString(1, email);
    cstmt.registerOutParameter(2, Types.VARCHAR);
    cstmt.execute();
    return cstmt.getString(2); 
  }

  public Boolean unlockUser(String email,String uid) throws SQLException{
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  unlockUser(?, ?, ?)}");
    cstmt.setString(1, email);
    cstmt.setString(2, uid);
    cstmt.registerOutParameter(3, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(3);
  }

  public Boolean addIpLocation(IpLocation ipLocation) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  addIpLocation(?, ?, ?, ?, ?)}");
    cstmt.setString(1, ipLocation.getIdClient());
    cstmt.setString(2, ipLocation.getIp());
    cstmt.setString(3, ipLocation.getCity());
    cstmt.setString(4, ipLocation.getCountry());
    cstmt.registerOutParameter(5, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(5);
  }

  public int getIdUser(String user) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call  GetIdUser(?, ?)}");
    cstmt.setString(1, user);
    cstmt.registerOutParameter(2, Types.INTEGER);
    cstmt.execute();
    return cstmt.getInt(2);
  }

  public int getIdClient(String nif) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call GetIdNif(?, ?)}");
    cstmt.setString(1, nif);
    cstmt.registerOutParameter(2, Types.INTEGER);
    cstmt.execute();
    return cstmt.getInt(2);
  }

  public Boolean isBlocked(Integer idClient) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call isBlocked(?, ?)}");
    cstmt.setInt(1, idClient);
    cstmt.registerOutParameter(2, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(2);
  }

  public JSONArray getData(Integer idClient,String operiacion) throws SQLException {
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call get"+operiacion+"Data(?)}");
    cstmt.setInt(1, idClient);
    cstmt.execute();
    ResultSet rs=cstmt.getResultSet();
    ResultSetMetaData rsmt = rs.getMetaData();
    JSONArray oneJson=new JSONArray();
    while (rs.next()) {
      for (int i = 0; i < rsmt.getColumnCount(); i++) {
          oneJson.add(rs.getString(i+1));
      }
      }
    return oneJson;
  }

  public Boolean updateClient(Client client) throws SQLException {
    int id=(int) RequestContextHolder.currentRequestAttributes().getAttribute("idClient",
    RequestAttributes.SCOPE_SESSION);
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call updateClient(?,?,?,?,?,?,?,?,?,?)}");
    cstmt.setInt(1, id);
    cstmt.setString(2, client.getName());
    cstmt.setString(3, client.getSurname());
    cstmt.setString(4, client.getNif());
    cstmt.setString(5, client.getMobile());
    cstmt.setString(6, client.getEmail());
    cstmt.setString(7, client.getBirthdate());
    cstmt.setString(8, client.getPostalCode());
    cstmt.setString(9, client.getAddress());
    cstmt.registerOutParameter(10, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(10);
  }

  public Boolean updateLogin(Login login) throws SQLException {
    int id=(int) RequestContextHolder.currentRequestAttributes().getAttribute("idClient",
    RequestAttributes.SCOPE_SESSION);
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call updateLogin(?, ?, ?, ?)}");
    cstmt.setInt(1, id);
    cstmt.setString(2, login.getUser());
    cstmt.setString(3, login.getPassword());
    cstmt.registerOutParameter(4, Types.BOOLEAN);
    cstmt.execute();
    return cstmt.getBoolean(4);
  }

  public Boolean isFreeData(String data,String operacion) throws SQLException {
    int id=(int) RequestContextHolder.currentRequestAttributes().getAttribute("idClient",
    RequestAttributes.SCOPE_SESSION);
    CallableStatement cstmt = (CallableStatement) connection.prepareCall("{call "+operacion+"Free_client(?, ?, ?)}");
    cstmt.setInt(1, id);
    cstmt.setString(2, data);
    cstmt.registerOutParameter(3, Types.BOOLEAN);
    cstmt.execute();
    System.out.println(cstmt.getBoolean(3));
    return cstmt.getBoolean(3);
  }


}
