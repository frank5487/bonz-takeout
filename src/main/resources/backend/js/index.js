/* self-define trim */
function trim (str) {  // delete space from left and right side
  return str == undefined ? "" : str.replace(/(^\s*)|(\s*$)/g, "")
}

// get parameter from url
function requestUrlParam(argname){
  var url = location.href //获取完整的请求url路径
  var arrStr = url.substring(url.indexOf("?")+1).split("&")
  for(var i =0;i<arrStr.length;i++)
  {
      var loc = arrStr[i].indexOf(argname+"=")
      if(loc!=-1){
          return arrStr[i].replace(argname+"=","").replace("?","")
      }
  }
  return ""
}
