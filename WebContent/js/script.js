var ItemUpdater = {
  setCovered: function(itemID, fulfilled, cb) {
    var unset = fulfilled == null || typeof fulfilled === "undefined";
    
    setItemStatusHTML(itemID, "...");
    
    
    var params = "itemID=" + itemID;
    if (!unset)
      params += "&fulfilled=" + (fulfilled? "true" : "false");
    
    AJAX.post("/api/setWishListItemCovered", params, "setWishListItemCoveredState" + itemID, function(err, statusCode) {
      if (err) {
        return cb(err);
      }
      if (statusCode !== 200) {
        return cb(new Error("Non 200 status code recieved: \"" + statusCode + "\"."));
      }
      
      return cb();
    });
  }
}