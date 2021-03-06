
/*
Copyright (c) Jim Garvin (http://github.com/coderifous), 2008.
Dual licensed under the GPL (http://dev.jquery.com/browser/trunk/jquery/GPL-LICENSE.txt) and MIT (http://dev.jquery.com/browser/trunk/jquery/MIT-LICENSE.txt) licenses.
Written by Jim Garvin (@coderifous) for use on LMGTFY.com.
http://github.com/coderifous/jquery-localize
Based off of Keith Wood's Localisation jQuery plugin.
http://keith-wood.name/localisation.html
 */
(function($) {
  var normaliseLang;
  normaliseLang = function(lang) {
    lang = lang.replace(/_/, '-').toLowerCase();
    if (lang.length > 3) {
      lang = lang.substring(0, 3) + lang.substring(3).toUpperCase();
    }
    return lang;
  };
  $.defaultLanguage = normaliseLang(navigator.language || navigator.userLanguage);
    $.relocalize = function (data, tag) {
	  var defaultCallback,valueForKey,localizeElement,localizeInputElement,localizeForSpecialKeys,localizeOptgroupElement,localizeImageElement;
	  wrappedSet = this;
	  
	  defaultCallback = function(data) {
	      return wrappedSet.each(function() {
              var elem, key, value, elem1, key1, value1;
              elem = $(this);
              if (tag == undefined)
                  tag = 'localize';
              key = elem.data(tag);
              if (key != undefined) {
                  key || (key = elem.attr("rel").match(/localize\[(.*?)\]/)[1]);
                  value = valueForKey(key, data);
                  if (value != null) {
                      localizeElement(elem, key, value);
                  }
              }
              elem1 = $(this);
              key1 = elem1.data("tooltip");
              if (key1 != undefined) {
                  key1 || (key1 = elem1.attr("rel").match(/tooltip\[(.*?)\]/)[1]);
                  value1 = valueForKey(key1, data);
                  if (value1 != null) {
                      console.log("in val 1");
                      // if (elem1.is("[data-toggle='tooltip']"))
                          elem1.attr("data-original-title", value1);
                  }
              }

          });
	    };
	    localizeElement = function(elem, key, value) {
	      if (elem.is('input')) {
	        localizeInputElement(elem, key, value);
	      } else if (elem.is('textarea')) {
	        localizeInputElement(elem, key, value);
	      } else if (elem.is('img')) {
	        localizeImageElement(elem, key, value);
	      } else if (elem.is('optgroup')) {
	        localizeOptgroupElement(elem, key, value);
	      } else if (!$.isPlainObject(value)) {
	        elem.html(value);
	      }
	      if ($.isPlainObject(value)) {
	        return localizeForSpecialKeys(elem, value);
	      }
	    };
	    localizeInputElement = function(elem, key, value) {
	      var val;
	      val = $.isPlainObject(value) ? value.value : value;
	      if (elem.is("[placeholder]")) {
	        return elem.attr("placeholder", val);
	      } else {
	        return elem.val(val);
	      }
	    };
	    localizeForSpecialKeys = function(elem, value) {
	      setAttrFromValueForKey(elem, "title", value);
	      setAttrFromValueForKey(elem, "href", value);
	      return setTextFromValueForKey(elem, "text", value);
	    };
	    localizeOptgroupElement = function(elem, key, value) {
	      return elem.attr("label", value);
	    };
	    localizeImageElement = function(elem, key, value) {
	      setAttrFromValueForKey(elem, "alt", value);
	      return setAttrFromValueForKey(elem, "src", value);
	    };
	    valueForKey = function(key, data) {
	      var keys, value, _i, _len;
	      keys = key.split(/\./);
	      value = data;
	      for (_i = 0, _len = keys.length; _i < _len; _i++) {
	        key = keys[_i];
	        value = value != null ? value[key] : null;
	      }
	      return value;
	    };
	    setAttrFromValueForKey = function(elem, key, value) {
	      value = valueForKey(key, value);
	      if (value != null) {
	        return elem.attr(key, value);
	      }
	    };
	    setTextFromValueForKey = function(elem, key, value) {
	      value = valueForKey(key, value);
	      if (value != null) {
	        return elem.text(value);
	      }
	    };
	    defaultCallback(data);
  };
  $.localize = function(pkg, options) {
    var defaultCallback, fileExtension, intermediateLangData, jsonCall, lang, loadLanguage, localizeElement, localizeForSpecialKeys, localizeImageElement, localizeInputElement, localizeOptgroupElement, notifyDelegateLanguageLoaded, regexify, setAttrFromValueForKey, setTextFromValueForKey, valueForKey, wrappedSet;
    if (options == null) {
      options = {};
    }
    wrappedSet = this;
    intermediateLangData = {};
    fileExtension = options.fileExtension || "json";
    loadLanguage = function(pkg, lang, level) {
      var file;
      if (level == null) {
        level = 1;
      }
      switch (level) {
        case 1:
          intermediateLangData = {};
          if (options.loadBase) {
            file = pkg + ("." + fileExtension);
            return jsonCall(file, pkg, lang, level);
          } else {
            return loadLanguage(pkg, lang, 2);
          }
          break;
        case 2:
          if (lang.length >= 2) {
            file = "" + pkg + "-" + (lang.substring(0, 2)) + "." + fileExtension;
            return jsonCall(file, pkg, lang, level);
          }
          break;
        case 3:
          if (lang.length >= 5) {
            file = "" + pkg + "-" + (lang.substring(0, 5)) + "." + fileExtension;
            return jsonCall(file, pkg, lang, level);
          }
      }
    };
    jsonCall = function(file, pkg, lang, level) {
      var ajaxOptions, errorFunc, successFunc;
      if (options.pathPrefix != null) {
        file = "" + options.pathPrefix + "/" + file;
      }
      successFunc = function(d) {
        $.extend(intermediateLangData, d);
        notifyDelegateLanguageLoaded(intermediateLangData);
        return loadLanguage(pkg, lang, level + 1);
      };
      errorFunc = function() {
        if (options.fallback && options.fallback !== lang) {
          return loadLanguage(pkg, options.fallback);
        }
      };
      ajaxOptions = {
        url: file + '?v=3.1',
        dataType: "json",
        async: false,
          cache: true,
        timeout: options.timeout != null ? options.timeout : 500,
        success: successFunc,
        error: errorFunc
      };
      if (window.location.protocol === "file:") {
        ajaxOptions.error = function(xhr) {
          return successFunc($.parseJSON(xhr.responseText));
        };
      }
      return $.ajax(ajaxOptions);
    };
    notifyDelegateLanguageLoaded = function(data) {
      if (options.callback != null) {
        return options.callback(data, defaultCallback);
      } else {
        return defaultCallback(data);
      }
    };
    defaultCallback = function(data) {
      $.localize.data[pkg] = data;
      return wrappedSet.each(function() {
          var elem, key, value, elem1, key1, value1, pkey, pvalue;
        elem = $(this);
        key = elem.data("localize");
          pkey = elem.data("postpendkey");
          if (pkey != undefined && pkey != null)
              pvalue = valueForKey(pkey, data);
          if (key != undefined && key != null && key != '') {
              key || (key = elem.attr("rel").match(/localize\[(.*?)\]/)[1]);
              value = valueForKey(key, data);
              if (value != null) {
                  localizeElement(elem, key, value, pkey, pvalue);
              }
        }
          elem1 = $(this);
          key1 = elem1.data("tooltip");
          if (key1 != undefined && key1 != null && key1 != '') {
              key1 || (key1 = elem1.attr("rel").match(/tooltip\[(.*?)\]/)[1]);
              value1 = valueForKey(key1, data);
              if (value1 != null) {
                  if (elem1.is("[data-toggle='tooltip']"))
                      elem1.attr("data-original-title", value1);
              }
          }
      });
    };
      localizeElement = function (elem, key, value, pkey, pvalue) {
      if (elem.is('input')) {
        localizeInputElement(elem, key, value);
      } else if (elem.is('textarea')) {
        localizeInputElement(elem, key, value);
      } else if (elem.is('img')) {
        localizeImageElement(elem, key, value);
      } else if (elem.is('optgroup')) {
        localizeOptgroupElement(elem, key, value);
      } else if (!$.isPlainObject(value)) {
          if (pkey != undefined && pkey != null)
              elem.append(pvalue);
          if (elem.data("preappend"))
              return elem.prepend(value);
          else
              elem.html(value);
      }
      if ($.isPlainObject(value)) {
        return localizeForSpecialKeys(elem, value);
      }
    };
    localizeInputElement = function(elem, key, value) {
      var val;
      val = $.isPlainObject(value) ? value.value : value;
      if (elem.is("[placeholder]")) {
        return elem.attr("placeholder", val);
      } else {
        return elem.val(val);
      }
    };
    localizeForSpecialKeys = function(elem, value) {
      setAttrFromValueForKey(elem, "title", value);
      setAttrFromValueForKey(elem, "href", value);
      return setTextFromValueForKey(elem, "text", value);
    };
    localizeOptgroupElement = function(elem, key, value) {
      return elem.attr("label", value);
    };
    localizeImageElement = function(elem, key, value) {
      setAttrFromValueForKey(elem, "alt", value);
      return setAttrFromValueForKey(elem, "src", value);
    };
    valueForKey = function(key, data) {
      var keys, value, _i, _len;
      keys = key.split(/\./);
      value = data;
      for (_i = 0, _len = keys.length; _i < _len; _i++) {
        key = keys[_i];
        value = value != null ? value[key] : null;
      }
      return value;
    };
    setAttrFromValueForKey = function(elem, key, value) {
      value = valueForKey(key, value);
      if (value != null) {
        return elem.attr(key, value);
      }
    };
    setTextFromValueForKey = function(elem, key, value) {
      value = valueForKey(key, value);

        if (value != null) {
        return elem.text(value);
      }
    };
    regexify = function(string_or_regex_or_array) {
      var thing;
      if (typeof string_or_regex_or_array === "string") {
        return "^" + string_or_regex_or_array + "$";
      } else if (string_or_regex_or_array.length != null) {
        return ((function() {
          var _i, _len, _results;
          _results = [];
          for (_i = 0, _len = string_or_regex_or_array.length; _i < _len; _i++) {
            thing = string_or_regex_or_array[_i];
            _results.push(regexify(thing));
          }
          return _results;
        })()).join("|");
      } else {
        return string_or_regex_or_array;
      }
    };
    lang = normaliseLang(options.language ? options.language : $.defaultLanguage);
    if (!(options.skipLanguage && lang.match(regexify(options.skipLanguage)))) {
      loadLanguage(pkg, lang, 1);
    }
    return wrappedSet;
  };
  $.fn.localize = $.localize;
  $.fn.relocalize=$.relocalize;
  return $.localize.data = {};
})(jQuery);
