// Collapsibles
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
    } else {
      content.style.display = "block";
    }
  });
}

// Onclick Notifications
//function myFunction(x) {
  //alert("Row index is: " + x.rowIndex);
//}

function addRowHandlers() {
    var table = document.getElementById("sc1-table");
    var rows = table.getElementsByTagName("tr");
    for (i = 0; i < rows.length; i++) {
        var currentRow = table.rows[i];
        var createClickHandler = 
            function(row) {
                return function() { 
                    var cell = row.getElementsByTagName("td")[0]; //Find ud af hvordan jeg fixer det ift at der er 4 td elementer og ikke kun 1
                    var id = cell.innerHTML;
                    alert(id);
                };
            };

        currentRow.onclick = createClickHandler(currentRow);
    }
}
window.onload = addRowHandlers();