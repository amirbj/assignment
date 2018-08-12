

(function(){
var loaded = parseInt(localStorage.getItem('shop'), 10),
loaded_numb = loaded?loaded+1:1;
localStorage.setItem('shop', loaded_numb);
alert('shop page was loaded by you '+loaded_numb+' times !')})();





