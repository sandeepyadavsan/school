function ConvertKgToLbsViceversa(val,to){
	var res=0;
	if(to=='KG'){
		res=(val*0.45359237).toFixed(2);
	}
	else if(to=='LBS'){
		res=(val/0.45359237).toFixed(2);
	}
	return res;
 }

function ConvertFeetInctToCmViceversa(val,to){
	var res="";
	if(to=='CM'){
		res=(val*2.54).toString();
	}
	else if(to=='FEET'){
		inches2 = ((val) * .39370078740157477);
	    feet= parseInt(inches2 / 12);
	    inch =Math.round(inches2 % 12);
		res=feet+'.'+inch;
	}
	return res;
}
function toDistance(param)
{
	try{
	if(distanceunit=="MILES")
	   return parseFloat(kmToMiles(param));//.toFixed(2);
	else
	   return parseFloat(param);//.toFixed(2);
	}
	catch(e)
	{
		return 0;
	}
}
function toWeight(param)
{
	if(weightunit=="LBS")
	  return (kgToLbs(param));
	else
	  return (parseFloat(param).toFixed(1));
}
function toWeightExact(param)
{
	if(weightunit=="LBS")
	  return kgToLbs(parseFloat(param)).toFixed(1);
	else
	  return parseFloat(param).toFixed(1);
}
function kgToLbs(param)
{
	return param*(2.20462);
}
function kmToMiles(param)
{
	return param*(0.621371);
}
function milesToKm(param)
{
	return param*(1.60934);
}
function minuteToHours(param)
{
	var hour=parseInt(param/60);
	var minute=param%60;
	return hour+'h '+minute+'m';
}

function fixed(val){
	var rtval=0;
	var flval=parseFloat(val).toFixed(1);
	var ar=flval.toString().split('.');
	var dcpart=parseInt(ar[0]);
	var decval=parseInt(ar[1]);
	if(decval>0){
		rtval= flval;
	}
	else{
		rtval=dcpart;
	}
	return rtval;
}
function distanceUnit(){
	return distanceunit;
}
function weightUnit(){
	return weightunit;
}
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

function isNumber(el,evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    var tl=	$(el).tooltip({
		title: "Please enter numeric values only",
	        trigger:'manual',
	        container: 'body'
	    });
         tl.tooltip('show');
        return false;
    }
    $(el).tooltip('destroy');
    return true;
}
function isNegativeNumber(el,evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
   var vl= el.value;
   console.log(vl.length);
   if(vl.length!=0&&charCode==45)
	{return false;}
    if (charCode > 31 && (charCode < 48 || charCode > 57)&&charCode!=45) {
    var tl=	$(el).tooltip({
		title: "Please enter numeric values only", //getJsonMessage("onlynumber", "messages"),
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
		title: "Please enter numeric values only",
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

String.prototype.trunc = String.prototype.trunc ||function(n){
    return this.length>n ? this.substr(0,n-1)+'&hellip;' : this;
};

function dropText(txt,no)
{
   if(txt==null || txt=='null' || txt=='' || txt==undefined){
	   return '';
   }else if(txt.length>no){
	  return txt.substring(0, no-1)+"...";
    }
   else
	return txt;  
	   
}


//=====================
$.fn.serializeObject = $.fn.serializeObject ||function()
{
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};
$.fn.serialize =$.fn.serialize || function (options) {
	return $.param(this.serializeArray(options));
};
$.fn.serializeArray = $.fn.serializeArray|| function (options) {
	var o = $.extend({
		checkboxesAsBools: true
	}, options || {});

	var rselectTextarea = /select|textarea/i;
	var rinput = /text|hidden|password|search/i;

	return this.map(function () {
		return this.elements ? $.makeArray(this.elements) : this;
	})
		.filter(function () {
			return this.name && !this.disabled &&
				(this.checked
				|| (o.checkboxesAsBools && this.type === 'checkbox')
				|| rselectTextarea.test(this.nodeName)
				|| rinput.test(this.type));
		})
		.map(function (i, elem) {
			var val = $(this).val();
			return val == null ?
				null :
				$.isArray(val) ?
					$.map(val, function (val, i) {
						return { name: elem.name, value: val };
					}) :
				{
					name: elem.name,
					value: (o.checkboxesAsBools && this.type === 'checkbox') ? //moar ternaries!
						(this.checked ? 'true' : 'false') :
						val
				};
		}).get();
};

function round(value, decimals) {
    return Number(Math.round(value + 'e' + decimals) + 'e-' + decimals);
}