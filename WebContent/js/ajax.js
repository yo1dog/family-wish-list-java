var AJAX = (function() {
  var requestsInProgress = {};
  
  function makeRequest(post, url, params, param1, param2) {
    var waitForName, cb;
    
    if (typeof param1 === "function") {
      waitForName = null;
      cb = param1;
    }
    else {
      waitForName = param1;
      cb = param2;
    }
    
    
    // check if we should wait
    if (waitForName && requestsInProgress[waitForName]) {
      if (cb) {
        return setTimeout(function() {
          return cb();
        }, 0);
      }
    }
    
    
    // create the HTTP object
    var http = null;
    
    try {
      if (window.XMLHttpRequest) {
        // IE7+, Firefox, Chrome, Opera, Safari
        http = new XMLHttpRequest();
      }
      else {
        // IE5, IE6
        http = new ActiveXObject("Microsoft.XMLHTTP");
      }
    }
    catch (e) {
      if (cb) {
        return setTimeout(function() {
          return cb(e);
        }, 0);
      }
    }
    
    
    http.onreadystatechange = function() {
      if (http.readyState !== 4) {
        return;
      }
      
      if (waitForName) {
        delete requestsInProgress[waitForName];
      }
      
      return cb(undefined, http.status, http.responseText, http);
    }
    
    if (post)
    {
      http.open("POST", url, true);
      http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      http.send(params);
    }
    else
    {
      http.open("GET", url + "?" + params, true);
      http.send();
    }
  }
  
  return {
    /**
     * Create and send a GET request.
     * 
     * @param url - URL to send request to.
     * @param params - Form URL encoded parameters.
     * @param [waitForName] - Identifying string for this request. Ensures that
     *                        two request with the same name can not be
     *                        executed in parallel.
     * @param [cb] - Callback. Should accept
     *               (error, statusCode, responseStr, http). If the request was
     *               not executed because a request with the same name was
     *               already in progress, statusCode will be undefined.
     * @returns
     */
    get: function(url, params, param1, param2) {
      makeRequest(false, url, params, param1, param2);
    },
    
    
    /**
     * Create and send a POST request.
     * 
     * @param url - URL to send request to.
     * @param params - Form URL encoded parameters.
     * @param [waitForName] - Identifying string for this request. Ensures that
     *                        two request with the same name can not be
     *                        executed in parallel.
     * @param [cb] - Callback. Should accept
     *               (error, statusCode, responseStr, http). If the request was
     *               not executed because a request with the same name was
     *               already in progress, statusCode will be undefined.
     * @returns
     */
    post: function(url, params, param1, param2) {
      makeRequest(true, url, params, param1, param2);
    }
  };
})();
