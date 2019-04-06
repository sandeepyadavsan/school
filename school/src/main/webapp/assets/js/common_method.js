String.prototype.format = function (placeholders) {
	var s = this;
	for (var propertyName in placeholders) {
		var re = new RegExp('{' + propertyName + '}', 'gm');
		s = s.replace(re, placeholders[propertyName]);
	}
	return s;
};

var numbersOnly = /^\d+$/;
var decimalOnly = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
var uppercaseOnly = /^[A-Z]+$/;
var lowercaseOnly = /^[a-z]+$/;
var stringOnly = /^[A-Za-z0-9]+$/;
function isNull(val){
	if(val==null&&val=='undefined'&&val.length==0)
		return false;
	else
		return true;
}

String.prototype.trunc = String.prototype.trunc ||function(n){
    return this.length>n ? this.substr(0,n-1)+'&hellip;' : this;
};
String.prototype.striphtml = String.prototype.striphtml || function () {
	return this.replaceWith(this.html().replace('/< /?[^>]+>/gi', ''));
};

String.prototype.contains = function(str, startIndex) {
    return ''.indexOf.call(this, str, startIndex) !== -1;
 };
  
 String.prototype.camelCase = String.prototype.camelCase || function () {
	 return this.toLowerCase().replace(/(?:^|\s)\w/g, function(match) {
	        return match.toUpperCase();
	  });
 };

function numberFormate(number){
	try{
		var num=parseFloat(number);
		if(num!="NaN"){
			 return (num.toString().replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ","));
		}else{
			return number;
		}
	}catch(error){
		return number;
	}  
}

function ConvertKgToLbsViceversa(val,to){
	var res=0;
	if(to=='KG'){
		res=(val*0.45359237).toFixed(1);
	}
	else if(to=='LBS'){
		res=(val/0.45359237).toFixed(1);
	}
	return res;
 }
function toDistance(param)
{
	try{
	if(distanceUnit=="MILES")
	   return kmToMiles(param).toFixed(1);
	else
	   return param.toFixed(1);
	}
	catch(e)
	{
		return 0;
	}
}
function kmToMiles(param)
{
	return param*(0.621371);
}
function milesToKm(param)
{
	return param*(1.60934);
}
function ConvertFeetInctToCmViceversa(val,to) {
	var res = "";
	if (to == 'CM') {
		res = (val * 2.54).toFixed(2).toString();
	}
	else if (to == 'FEET') {
		inches2 = ((val) * .393701);
		feet = parseInt(inches2 / 12);
		inch = Math.round(inches2 % 12);
		if (inch == 12) {
			feet = feet + 1;
			inch = 0;
		}
		res = feet + '.' + inch;

	}

	return res;

}
function isNumber(el,evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    var tl=	$(el).tooltip({
		title: getJsonMessage("onlynumber", "messages"),
	        trigger:'manual',
	        container: 'body'
	    });
         tl.tooltip('show');
        return false;
    }
    $(el).tooltip('destroy');
    return true;
}
function isFloatVal(el,evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && charCode != 46 && (charCode < 48 || charCode > 57)) {
    var tl=	$(el).tooltip({
		title: getJsonMessage("onlynumber", "messages"),
	        trigger:'manual',
	        container: 'body'
	    });
         tl.tooltip('show');
        return false;
    }
    $(el).tooltip('destroy');
    return true;
}
function destroyToolTip(el)
{
	 $(el).tooltip('destroy');
}

var numbersOnly = /^\d+$/;
var decimalOnly = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
var uppercaseOnly = /^[A-Z]+$/;
var lowercaseOnly = /^[a-z]+$/;
var stringOnly = /^[A-Za-z0-9]+$/;

function testInputData(myData, restrictionType,fieldName) {

 if(myData!==''){
  if(restrictionType.test(myData)){
   return true;
  }else{
   toastr.error("Invalid "+fieldName+" value.");
   return false;
  }
 }else{
	 toastr.error("Invalid "+fieldName+" value.");
	 return false;
 }
 return;
    
}
function toWeight(param)
{
	param = parseFloat(param);
	if (weightunit == "LBS")
		return ConvertKgToLbsViceversa(param, "LBS");
	else
		return param.toFixed(1);
}
function toWeightExact(param)
{
	if(weightunit=="LBS")
		return (ConvertKgToLbsViceversa(param, "LBS") * 1).toFixed(1);
	else
		return param.toFixed(1);
}