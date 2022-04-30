
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}

//validate username
function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("input username"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("the length of username should be 3-20"))
  } else {
    callback()
  }
}

//validate password
function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("input name"))
  } else if (value.length > 12) {
    callback(new Error("the length of name should be 1-12"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
  if (value == "") {
    callback(new Error("input phone #"))
  } else if (!isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
    callback(new Error("input correct format of phone #"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  // the length of id number should be 15 with all of numbers or should be 18 with 17 numbers of prefix and last validate word
  let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if(value == '') {
    callback(new Error('input id number'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('the format of ID is wrong'))
  }
}