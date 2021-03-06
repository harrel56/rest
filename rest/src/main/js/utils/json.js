export function flattenJsonArray(arr) {
  let result = [];
  for (let i = 0; i < arr.length; ++i) {
    result.push(flattenJson(arr[i]));
  }
  return result;
}

export function flattenJson(data) {
  var result = {};
  function recurse(cur, prop) {
    if (Object(cur) !== cur) {
      result[prop] = cur;
    } else if (Array.isArray(cur)) {
      for (var i = 0, l = cur.length; i < l; i++)
        recurse(cur[i], prop + "[" + i + "]");
      if (l == 0) result[prop] = [];
    } else {
      var isEmpty = true;
      for (var p in cur) {
        isEmpty = false;
        recurse(cur[p], prop ? prop + "." + p : p);
      }
      if (isEmpty && prop) result[prop] = {};
    }
  }
  recurse(data, "");
  return result;
}

export function formDataToJsonString(data) {
  var object = {};
  data.forEach((value, key) => {
    object[key] = value;
  });
  return JSON.stringify(object);
}

export function formDataToJson(data) {
  var object = {};
  data.forEach((value, key) => {
    object[key] = value;
  });
  return object;
}
