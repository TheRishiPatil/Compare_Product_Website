const searchWrapper = document.querySelector(".search");
const inputBox = searchWrapper.querySelector("input");
const suggBox = searchWrapper.querySelector(".autocom-box");

inputBox.addEventListener("keyup", (e) => {
	let userData = e.target.value;
	let emptyArray = [];

	if (userData) {
		emptyArray = suggestions.filter((data) => {
			return data.toLowerCase().startsWith(userData.toLowerCase());
		});
		emptyArray = emptyArray.map((data) =>{
			return data = '<li>' + data + '</li>';
		});
		searchWrapper.classList.add("active");
		showSuggestions(emptyArray);
		let allList = suggBox.querySelectorAll("li")
		for(let i = 0; i < allList.length; i++){
			allList[i].setAttribute("onclick", "select(this)")
		}
		
	} else {
		searchWrapper.classList.remove("active");
	} 
});

function select(element){
	let selectUserData = element.textContent;
	inputBox.value = selectUserData;
	searchWrapper.classList.remove("active");
}

function showSuggestions(list){
	let listData;
	if(!list.length){
		userValue = inputBox.value;
		listData = '<li>' + userValue + '</li>'; 
		
	} else{
		listData = list.join('');
	}
	suggBox.innerHTML = listData;
}