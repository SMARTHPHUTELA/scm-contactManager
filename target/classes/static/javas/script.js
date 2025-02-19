console.log("js");
let currentTheme=getTheme();
document.addEventListener("DOMContentLoaded",()=>{
    changeTheme();
});
function changeTheme(){
    changepagetheme(currentTheme,currentTheme);
    const themebutton = document.getElementById('themebutton');
    themebutton.addEventListener("click",(event)=>{
        let oldtheme=currentTheme;
        if(currentTheme=="light"){
            currentTheme="dark";
        }
        else{
            currentTheme="light";
        }
        changepagetheme(currentTheme,oldtheme)
        

    });

}
function changepagetheme(theme,oldtheme){
    setTheme(theme);
    document.querySelector("html").classList.remove(oldtheme);
    document.querySelector("html").classList.add(theme);
    document.querySelector("#themebutton").querySelector("span").textContent=theme=="light"?"Dark":"Light";

}

function setTheme(theme){
    localStorage.setItem("theme",theme);
}
function getTheme(){
    const th = localStorage.getItem("theme");
    if(th==null){
        return "light";
    }
    return th;
}