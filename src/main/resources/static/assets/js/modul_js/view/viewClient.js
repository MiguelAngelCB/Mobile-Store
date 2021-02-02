import {FactoryView} from "../factory/factoryView.js"

export function ViewClient() {

  
  const API = {};
   const factoryView = FactoryView();
  API.register = function () {          
           return factoryView.clientRegister("Register");    
  };
  API.login = function () {           
           return factoryView.clientLogin("Login");    
  };
  API.forgetPassword = function () {           
           return factoryView.clientForgetPassword();    
  };
  API.updateData = function () {           
    return factoryView.clientRegister("Update Data");
  };
  API.updateLogin = function () {           
    return factoryView.clientLogin("Update Login");
  };
  API.updateAvatar = function () { 
    return factoryView.clientUpdateAvatar();    
  };
  return API;
}
