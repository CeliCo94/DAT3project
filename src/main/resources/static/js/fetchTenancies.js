let editingTenancyId = null;
let departments = [];
let tenancies = [];

document.addEventListener("DOMContentLoaded", () => {
  fetchDepartments();
  fetchTenancies();
  setupFormHandler();

  document.getElementById("tenancy-department-id")
  .addEventListener("change", (e) => {
    const selectedDepartment = e.target.value;
    applyDepartmentFilter(selectedDepartment);
  });
});

// Fetch departments for dropdown
async function fetchDepartments() {
  try {
    const res = await fetch("/api/departments", { credentials: "same-origin" });
    console.log("Response status:", res.status);
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    
    data = await res.json();
    console.log("Departments from API:", data);

    departments = data;
    populateDepartmentDropdown();
  } catch (err) {
    console.error("Fetching departments failed:", err);
  }
}

function populateDepartmentDropdown() {
  const select = document.getElementById("tenancy-department-id");
  console.log("Select found:", select);
  if (!select) return;
  
  // Clear existing options except the first one
  select.innerHTML = '<option value="">Vælg afdeling...</option>';
  
  departments.forEach(dept => {
    console.log("Dept object:", dept);
    const option = document.createElement("option");
    option.value = dept.name;
    option.textContent = dept.name;
    select.appendChild(option);
  });

  setTimeout(() => {
  console.log("Options after 1s:", select.options.length);
}, 1000);

  console.log("Options count:", select.options.length);
}

function applyDepartmentFilter(departmentName) {
  console.log(departmentName);
  if (!departmentName) {
    displayTenancies(tenancies);
    return;
  }

  console.log(tenancies)

  const filtered = tenancies.filter(t =>
    t.departmentName === departmentName
  );
  console.log(filtered)

  displayTenancies(filtered);
}

function clearFilter() {
  const select = document.getElementById("tenancy-department-id");

  if (select) {
    select.value = ""; // reset dropdown to "Alle afdelinger"
  }

  // show all tenancies again
  displayTenancies(tenancies);
}

// READ: Get all tenancies
async function fetchTenancies() {
  try {
    const res = await fetch("/api/tenancies", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);

    tenancies = await res.json();
    if (!tenancies || tenancies.length === 0) {
      displayNoTenancies();
      return;
    }

    displayTenancies(tenancies);
  } catch (err) {
    console.error("Fetching tenancies failed:", err);
    displayError();
  }
}

// READ: Get single tenancy by ID
async function getTenancyById(id) {
  try {
    const res = await fetch(`/api/tenancies/${id}`, { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    return await res.json();
  } catch (err) {
    console.error("Fetching tenancy failed:", err);
    throw err;
  }
}

function displayTenancies(tenancies) {
  const tbody = document.querySelector("#lejemaal-table tbody");
  if (!tbody) return;

  tbody.innerHTML = "";

  tenancies.sort((a, b) => {
    const depA = (a.departmentName || "").toLowerCase();
    const depB = (b.departmentName || "").toLowerCase();
    return depA.localeCompare(depB);
  });

  tenancies.forEach(tenancy => {
    const row = document.createElement("tr");
    
    const departmentCell = document.createElement("td");
    departmentCell.textContent = tenancy.departmentName || "-";
    
    const addressCell = document.createElement("td");
    addressCell.textContent = tenancy.address || "-";
    
    const cityCell = document.createElement("td");
    cityCell.textContent = tenancy.city || "-";
    
    const postalCodeCell = document.createElement("td");
    postalCodeCell.textContent = tenancy.postalCode || "-";
    
    row.appendChild(departmentCell);
    row.appendChild(addressCell);
    row.appendChild(cityCell);
    row.appendChild(postalCodeCell);
    
    tbody.appendChild(row);
  });
}

function displayNoTenancies() {
  const tbody = document.querySelector("#lejemaal-table tbody");
  if (!tbody) return;
  
  const row = document.createElement("tr");
  const cell = document.createElement("td");
  cell.colSpan = 9;
  cell.textContent = "Ingen lejemål fundet";
  cell.style.textAlign = "center";
  cell.style.padding = "20px";
  row.appendChild(cell);
  tbody.appendChild(row);
}

function displayError() {
  const tbody = document.querySelector("#lejemaal-table tbody");
  if (!tbody) return;
  
  const row = document.createElement("tr");
  const cell = document.createElement("td");
  cell.colSpan = 9;
  cell.textContent = "Fejl ved indlæsning af lejemål";
  cell.style.textAlign = "center";
  cell.style.padding = "20px";
  cell.style.color = "#d32f2f";
  row.appendChild(cell);
  tbody.appendChild(row);
}

function setupFormHandler() {
  const form = document.getElementById("tenancy-form");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    
    if (editingTenancyId) {
      await updateTenancy();
    } else {
      await createTenancy();
    }
  });
}

// Modal functions
function openTenancyModal() {
  resetForm();
  document.getElementById("tenancy-modal").classList.add("active");
}

function closeTenancyModal() {
  document.getElementById("tenancy-modal").classList.remove("active");
  resetForm();
}

function closeModalOnOverlay(event) {
  if (event.target.id === "tenancy-modal") {
    closeTenancyModal();
  }
}
