var chooseFileInput = document.getElementById('chooseFileInput');

function getFileName() {
    var value = "";
    if ('files' in chooseFileInput) {
        if (chooseFileInput.files.length === 0) {
            value = "<strong class='chosenFileName'>Please select a file.</strong>";
        } else {
            var file = chooseFileInput.files[0];
            value += "<span class='chosenFileName'>File: ";
            value += "<strong>" + file.name + "</strong>";
            value += "</span>";
        }
    }

    document.getElementById('chosenFile').innerHTML = value;
}
