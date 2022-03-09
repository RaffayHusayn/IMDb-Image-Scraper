var resultList = document.querySelector('ul.results');
var urlInput = document.querySelector('input[name=url]')

apiCallBack = function (xhr, callback) {
    if (xhr.readyState == XMLHttpRequest.DONE) {
        if (xhr.status != 200) {
            let message = xhr.status + ":" + xhr.statusText + ":"
                + xhr.responseText;
            alert(message);
            throw 'API call returned bad code: ' + xhr.status;
        }
        let response = xhr.responseText ? JSON.parse(xhr.responseText)
            : null;
        if (callback) {
            callback(response);
        }
    }
}

updateList = function (response) {
    resultList.innerHTML = '';
    for (var i = 0; i < response.length; i++) {
        var img = document.createElement("img");
        // img.width = 200;
        img.height = 200;
        img.src = response[i];
        resultList.appendChild(img);
        enableButton("submit");
        enableInput("search");
        hideLoading("loading");
    }
}

makeApiCall = function (url, method, obj, callback) {
    let xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.onreadystatechange = apiCallBack.bind(null, xhr, callback);
    xhr.send(obj ? obj instanceof FormData || obj.constructor == String ? obj : JSON.stringify(obj) : null);
}

document.querySelector('button').addEventListener("click", function (event) {
    event.preventDefault();
    var input = document.getElementById("search").value;
    if (input.length > 0) {

        disableButton("submit");
        disableInput("search");
        showLoading("loading");
    }
    makeApiCall('/main?url=' + urlInput.value, 'POST', null, updateList);
});

disableButton = function (id) {
    document.getElementById(id).disabled = true;
    // setTimeout(function(){document.getElementById(id).disabled = false;},5000);
}
enableButton = function (id) {
    document.getElementById(id).disabled = false;
}
disableInput = function (id) {
    document.getElementById(id).disabled = true;
    // setTimeout(function(){document.getElementById(id).disabled = false;},5000);
}
enableInput = function (id) {
    document.getElementById(id).disabled = false;
}
showLoading = function(id){
    document.getElementById(id).style.display = "flex";
}
hideLoading = function(id){
    document.getElementById(id).style.display = "none";
}
