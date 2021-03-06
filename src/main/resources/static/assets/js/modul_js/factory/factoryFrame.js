import { FactoryTag } from "./factoryTag.js";
import { FactoryBox } from "./factoryBox.js";
import { FactoryMenu } from "./factoryMenu.js";
import { FactoryButton } from "./factoryButton.js";
import { GeneralPurposeFunctions } from "../function/general_purpose_functions.js";
import {VALIDATOR} from "../enum/enum_validator.js";
import {FactoryImg} from "../factory/factoryImg.js";
import {LOADER} from "../enum/enum_loader.js";


export function FactoryFrame() {
  const API = {};
  const factoryTag = new FactoryTag();
  const factoryMenu = new FactoryMenu();
  const factoryButton = new FactoryButton();
  const factoryBox = new FactoryBox();
  const generalPurposeFunctions = new GeneralPurposeFunctions();
  const factoryImg = new  FactoryImg();

const input = function(params){
  const myId = params.id;
  const divBoxInput = factoryTag.div(params);
  divBoxInput.id = "divbox_input_" + params.id;
  params.class?divBoxInput.className = params.class : divBoxInput.className = "box-input"; 
  //divBoxInput.setAttribute("data-divcontrol", "true");  
   params.class = "box-input-children";
  if(params.labelOn){
    params.for =  params.id;
     params.id = "label_" + params.id;     
     params.labelText ?  params.text = generalPurposeFunctions.capital(params.labelText) : params.text = generalPurposeFunctions.capital(myId) + ": ";
    divBoxInput.appendChild(factoryTag.label(params));  
      params.text  ="";
       params.id = myId;
  }
  const divInput =  factoryTag.div(params);
  divInput.id = "div_input_" + params.id;

  if(params.required){
    params.text = "*";
   params.id = "span_" + params.id;
   params.class = "asterisco";
   params.title = params.title;
   divInput.appendChild(factoryTag.span(params));
    params.id = myId;
     params.class = "";
  }
  params.validate = params.validate || VALIDATOR.ACCEPTED;
  
  const myInput = factoryTag.input(params);
   myInput.setAttribute("data-field", params.field)
  if (params.phoneType){
       myInput.setAttribute("data-phoneType", params.phoneType);  
     }     
  divInput.appendChild(myInput);     
  divBoxInput.appendChild(divInput);
  if(params.errorBox){
    const errorBox =  factoryBox.error();
    errorBox.id = "boxerror_" + params.id;
    //errorBox.classList.add("box-input-children");
    divBoxInput.appendChild(errorBox);
  }
  if(params.infoBox){
     const errorInfo =  factoryBox.info();
     errorInfo.id = "boxinfo_" + params.id;
     divBoxInput.appendChild(errorInfo);
    }    
    return divBoxInput;
}
const phone = function(params){  
      const phone  = factoryTag.div(params);
      phone.id = "div_" + params.id;    
      phone.className = "box-input";
      params.class = "box-input-children";   
      const littleImgBox = factoryBox.littleImgBox();
         littleImgBox.id = "litleImg_"  + params.id;
         phone.appendChild(littleImgBox);
         const myId = params.id;
         params.id = "select_" + myId;
        const select = factoryTag.select(params);
        select.setAttribute("data-phoneType", params.phoneType);  
          phone .appendChild(select);
        params.id = myId; 
        phone.appendChild(input(params));
        return phone;    
};
API.menuButton = function () {
    let params = {};
    params.class = "header-content container";
    const section = factoryTag.section(params);

    section.appendChild(factoryMenu.index());
    section.appendChild(factoryButton.darkLight());
    return section;
  };
  API.menuClientButtonDarkLight = function () {
    let params = {};
    params.class =   "container mx-auto mt-5 text-center";   //"header-content container";
    const section = factoryTag.section(params);

    section.appendChild(factoryMenu.client());
    section.appendChild(factoryButton.darkLight());
    return section;
  };
API.weatherLocation = function () {
    
let params = {};
    params.class = "subhome";
    const divExt = factoryTag.div(params);
    params = {};
    params.id = "description";
    const divFirst = factoryTag.div(params);
    params = {};
    params.id = "temp";
    const h1 = factoryTag.h1(params);
    params = {};
    params.id = "location";
    const divSecond = factoryTag.div(params);
    divExt.appendChild(divFirst);
    divExt.appendChild(h1);
    divExt.appendChild(divSecond);
    return divExt;
  };
  API.viewTitle = function(id,title){
   let params = {};
    params.id = id;
    params.class = "section-formJM harni-form";     
    const section = factoryTag.section(params);
    params = {};
    params.text = title;
    const titleForm = factoryTag.h1(params);
    section.appendChild(titleForm);
    return section;
  };
  API.text = function(id,title){
   let params = {};
    params.id = id;
    params.class = "harni-form";     
    const div = factoryTag.div(params);
    params = {};
    params.text = title;
    const titleForm = factoryTag.h1(params);
    div.appendChild(titleForm);
    return div;
  };
  API.viewHref = function(id,title,enlace){
   let params = {};
    params.id = id;
    params.class = "harni-form";     
    const div = factoryTag.div(params);
    params = {};
    params.id = id;
    params.text = title;
    params.href = enlace;
    const a=factoryTag.a(params);
    div.appendChild(a);
    return div;
  };
  API.createControl = function(params, control){
  const divDataControl = factoryTag.div(params);
 divDataControl.id = "div_dataControl_" + params.id;
 divDataControl.setAttribute("data-divcontrol", "true");  
 divDataControl.appendChild(eval( control + "(params)"));
    return divDataControl;
  };
API.divSubmit = function(value){
  const params = {};
  params.id = "div_submit";
  const divSubmit = factoryTag.div(params);
  divSubmit.style.display = "none";
  divSubmit.appendChild(factoryButton.submit(value));
  const loader = factoryImg.loader(LOADER.BALL_TRIANGLE);
  loader.style.display = "none";
  divSubmit.appendChild(loader);
  const error = factoryBox.error();
   error.style.display = "none";
  divSubmit.appendChild(error);
  return divSubmit;
};
API.divCancel = function(params){
  params = {};
  params.id = "div_cancel";
  const divSubmit = factoryTag.div(params);
  params = {};
  params.id = "linkCancel";
  params.href = "index";
  const a=factoryTag.a(params);
  a.appendChild(factoryButton.generic("Cancel"));
  divSubmit.appendChild(a);
  return divSubmit;
};
API.avatar = function(){
  const node = this.viewTitle("div_view_avatar", "Avatar"); 
   node.className = "bg-pink-800 container mx-auto px-4 h-48 w-48 border-dotted border-2 border-yellow-800";
   node.appendChild(factoryImg.avatar());
   return node;
}
API.formUpImage = function(){
  const nodeView = this.viewTitle("div_view_crud", "Update Avatar"); 
  let params = {};
  nodeView.appendChild(API.avatar()); 
  params.id  = "formUpImage";
  params.enctype="multipart/form-data";
  params.action = "/uploadFile";
  params.method="POST";
  const form = factoryTag.form(params); 
  params.id = "divFormUpImage";
  params.class ="mb-4";
  let divControl = factoryTag.div(params);
  params.id = "formControlLabel";
  params.class ="block text-grey-darker text-sm font-bold mb-2";
  params.for = "avatar";
  params.text = "Elige Avatar";
  let labelControl = factoryTag.label(params);
   params.id = "formControlInput";
  params.class ="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker";
  params.name = "avatar";
  params.type = "file";
  params.placeholder  ="New image";
  params.accept="image/png, image/jpeg" ;
  let inputControl = factoryTag.input(params);    
  let mySubmit = factoryButton.submit();
  mySubmit.id = "submitAvatar";
  mySubmit.value = "Upload Image";
//alert(mySubmit.id);
 divControl.appendChild(labelControl);
 divControl.appendChild(inputControl);   
 form.appendChild(divControl);
 form.appendChild(mySubmit);
 nodeView.appendChild(form);
 return nodeView;
}

  return API;
}
