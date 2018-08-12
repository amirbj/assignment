(function(){
var loaded = parseInt(localStorage.getItem('login'), 10),
loaded_numb = loaded?loaded+1:1;
localStorage.setItem('login', loaded_numb);
alert('Login page was loaded by you '+loaded_numb+' times !')})();