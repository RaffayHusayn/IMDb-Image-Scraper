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

document.querySelector('.searchButton').addEventListener("click", function (event) {
    event.preventDefault();
    var input = document.getElementById("search").value;
    if (input.startsWith("https://unsplash.com") || input.startsWith("https://www.imdb.com/list/") || input.startsWith("https://www.imdb.com/title/")) {

        disableButton("submit");
        disableInput("search");
        showLoading("loading");
    }
    makeApiCall('/main?url=' + urlInput.value, 'POST', null, updateList);
});

document.querySelector("#suggestion1").addEventListener("click", function(event){
    document.getElementById("search").value = document.getElementById("suggestion1").textContent;
});

document.querySelector("#suggestion2").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestion2").textContent;
    document.getElementById("search").value = suggestionUrl;
});

document.querySelector("#suggestion3").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestion3").textContent;
    document.getElementById("search").value = suggestionUrl;
});
document.querySelector("#suggestion4").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestion4").textContent;
    document.getElementById("search").value = suggestionUrl;
});
document.querySelector("#suggestionMovie1").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionMovie1").textContent;
    document.getElementById("search").value = suggestionUrl;
});

document.querySelector("#suggestionMovie2").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionMovie2").textContent;
    document.getElementById("search").value = suggestionUrl;
});

document.querySelector("#suggestionMovie3").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionMovie3").textContent;
    document.getElementById("search").value = suggestionUrl;
});
document.querySelector("#suggestionMovie4").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionMovie4").textContent;
    document.getElementById("search").value = suggestionUrl;
});
document.querySelector("#suggestionUnsplash1").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionUnsplash1").textContent;
    document.getElementById("search").value = suggestionUrl;
});

document.querySelector("#suggestionUnsplash2").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionUnsplash2").textContent;
    document.getElementById("search").value = suggestionUrl;
});

document.querySelector("#suggestionUnsplash3").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionUnsplash3").textContent;
    document.getElementById("search").value = suggestionUrl;
});
document.querySelector("#suggestionUnsplash4").addEventListener("click", function(event){
    var suggestionUrl = document.getElementById("suggestionUnsplash4").textContent;
    document.getElementById("search").value = suggestionUrl;
});
disableButton = function (id) {
    document.getElementById(id).disabled = true;
}
enableButton = function (id) {
    document.getElementById(id).disabled = false;
}
disableInput = function (id) {
    document.getElementById(id).disabled = true;
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
