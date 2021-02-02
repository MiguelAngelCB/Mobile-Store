import {STRATEGY} from "../enum/enum_stratey.js";
import {w,d,$,lS,sS,Q,Qa} from "./global.js";
import { GeneralPurposeFunctions } from "./general_purpose_functions.js";
import { ViewClient } from "../view/viewClient.js";
import { ManagerFunctions } from "./manager_functions.js";

const viewClient = new ViewClient();
const managerFunctions = new ManagerFunctions();

export function UpdateFunctions(){
    const API={};
    API.generatePage = function(){
        if (sS.getItem("operationClient") == "linkUpData") {
           const globalFunction = new GeneralPurposeFunctions();
           globalFunction.resetAutoIncrementPhoneCP();      
           myBody.innerHTML = "";
           myBody.appendChild(viewClient.updateData());    
           managerFunctions.validations();     
           managerFunctions.phone();   
           managerFunctions.saveDataControls();
           managerFunctions.showIniStrategy(STRATEGY.ALL);
           fetch("/getRegisterData")
           .then((res) => (res.json()))
           .then((response) => {
            console.log("response",response[0].data);
               API.filDatas(response[0].data);
            });
            let operationClient=sS.getItem("operationClient");
            sS.setItem("url",operationClient);
        }
        if (sS.getItem("operationClient") == "linkUpLogin") {
            myBody.innerHTML = "";
            myBody.appendChild(viewClient.updateLogin());    
            managerFunctions.validations();     
            managerFunctions.saveDataControls();
            managerFunctions.showIniStrategy(STRATEGY.ALL);
            const dataControl =  managerFunctions.getDataControls();
            fetch("/getLoginData")
           .then((res) => (res.json()))
           .then((response) => {
            console.log("response",response[0].data);
               API.filDatas(response[0].data);
            });
            let operationClient=sS.getItem("operationClient");
            sS.setItem("url",operationClient);
        }
        if (sS.getItem("operationClient") == "linkUpAvatar") {
            myBody.innerHTML = "";
            myBody.appendChild(viewClient.updateAvatar());   
        }
    };
    API.filDatas =function(dataClient) {
        const datas = managerFunctions.getDataControls();
        let i = 0;
        for (const data in datas) {
            let input=Q("input[data-field='" + data + "']");
            if (data == "mobile") {
                let names=dataClient[i].split("-");
                managerFunctions.phone().changePrefixAuto(names[0],input);
                input.value = names[1];
            } else {
                input.value=dataClient[i];
            }
            i++;
            managerFunctions.dataControl().validData(input);
        }
        managerFunctions.submit().on();
    };
    return API;
}