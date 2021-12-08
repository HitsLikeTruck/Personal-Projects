const inputBox = document.querySelector(".inputField input");
const addBtn = document.querySelector(".inputField button");
const todoList = document.querySelector(".todoList");
const completedNumb = document.querySelector(".completedTask");
const pendingNumb = document.querySelector(".pendingTask");
const clearAll = document.querySelector(".footer2 button");


showList();

//Entered values
inputBox.onkeyup = ()=> {
    let userData = inputBox.Value;
        if (userData != 0) {
            addBtn.classList.add("active");
        } else {
            addBtn.classList.remove("active"); }
}

//Button function
addBtn.onclick = ()=> { 
    let userEnteredValue = inputBox.value; 
    let getLocalStorage = localStorage.getItem("New item");

    if(getLocalStorage == null) { 
      listArray = []; 
    } else{
      listArray = JSON.parse(getLocalStorage); }

    listArray.push(userEnteredValue);
    localStorage.setItem("New item", JSON.stringify(listArray));
    addBtn.classList.remove("active");
    showList();
  }

//Display list
function showList(){
    let getLocalStorage = localStorage.getItem("New item"); 
    
    if (getLocalStorage == null) { 
      listArray = []; 
    } else {
      listArray = JSON.parse(getLocalStorage); }

      pendingNumb.textContent = listArray.length;
      if (listArray.length > 0) {
         clearAll.classList.add("active"); 
      } else {
        clearAll.classList.remove("active"); }        

    let newLiTag = '';
    listArray.forEach((element, delete_index, check_index) => {
        newLiTag += `<li>${element}<span class="icon" onclick="deleteTask(${delete_index})"><i class="fas fa-trash"></i></span>` +
                    `<span1 class="icon alert" onclick="finishTask(${delete_index})"><i class="far fa-check-square"></span1></i></li>`;
      });
      todoList.innerHTML = newLiTag;
      inputBox.value = "";
    }

function deleteTask(index){
    let getLocalStorage = localStorage.getItem("New item");
    listArray = JSON.parse(getLocalStorage);

    listArray.splice(index, 1);
    localStorage.setItem("New item", JSON.stringify(listArray));
    showList();
}

function finishTask(index){
    let getLocalStorage = localStorage.getItem("New item");
    listArray = JSON.parse(getLocalStorage);

    listArray.splice(index, 1);

    alert("Task completed. \n\n *INSERT TASK DETAILS*");
    localStorage.setItem("New item", JSON.stringify(listArray));
    showList();
}

clearAll.onclick = ()=>{
  let getLocalStorageData = localStorage.getItem("New item");
  if(getLocalStorageData == null){ 
    listArray = [];
  }else{
    listArray = JSON.parse(getLocalStorageData);
    listArray = [];
  }
  localStorage.setItem("New item", JSON.stringify(listArray));
  showList();
}