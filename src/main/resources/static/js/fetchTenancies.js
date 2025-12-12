let editingTenancyId = null;
let departments = [];
let allTenancies = []; // Store all tenancies for filtering

document.addEventListener("DOMContentLoaded", () => {
  fetchDepartments();
  fetchTenancies();
  setupFormHandler();
});

// Fetch departments for dropdown
async function fetchDepartments() {
  try {
    const res = await fetch("/api/departments", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    
    departments = await res.json();
    populateDepartmentDropdown();
    populateFilterDropdown(); // Also populate the filter dropdown
  } catch (err) {
    console.error("Fetching departments failed:", err);
  }
}

function populateDepartmentDropdown() {
  const select = document.getElementById("tenancy-department-id");
  if (!select) return;
  
  // Clear existing options except the first one
  select.innerHTML = '<option value="">Vælg afdeling...</option>';
  
  departments.forEach(dept => {
    const option = document.createElement("option");
    option.value = dept.id;
    option.textContent = dept.name; // Changed from dept.id to dept.name
    select.appendChild(option);
  });
}

// Populate the filter dropdown
function populateFilterDropdown() {
  const select = document.getElementById("department-filter");
  if (!select) return;
  
  // Clear existing options except "All"
  select.innerHTML = '<option value="">Alle afdelinger</option>';
  
  departments.forEach(dept => {
    const option = document.createElement("option");
    option.value = dept.id;
    option.textContent = dept.name; // Changed from dept.id to dept.name
    select.appendChild(option);
  });
}

// READ: Get all tenancies
async function fetchTenancies() {
  try {
    const res = await fetch("/api/tenancies", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);

    const tenancies = await res.json();
    allTenancies = tenancies; // Store all tenancies
    
    if (!tenancies || tenancies.length === 0) {
      displayNoTenancies();
      return;
    }

    // Apply current filter if any
    applyFilter();
  } catch (err) {
    console.error("Fetching tenancies failed:", err);
    displayError();
  }
}

// Filter function
function filterByDepartment() {
  applyFilter();
}

function displayTenancies(tenancies) {
  const tbody = document.querySelector("#lejemaal-table tbody");
  if (!tbody) return;

  tbody.innerHTML = "";

  tenancies.forEach(tenancy => {
    const row = document.createElement("tr");
    
    const idCell = document.createElement("td");
    idCell.textContent = tenancy.id || "-";
    
    const departmentCell = document.createElement("td");
    departmentCell.textContent = tenancy.departmentName || "-";
    
    const addressCell = document.createElement("td");
    addressCell.textContent = tenancy.address || "-";
    
    const cityCell = document.createElement("td");
    cityCell.textContent = tenancy.city || "-";
    
    const postalCodeCell = document.createElement("td");
    postalCodeCell.textContent = tenancy.postalCode || "-";
    
    const actionsCell = document.createElement("td");
    actionsCell.style.display = "flex";
    actionsCell.style.gap = "8px";
    
    const editBtn = document.createElement("button");
    editBtn.textContent = "Rediger";
    editBtn.style.padding = "4px 12px";
    editBtn.style.background = "#085a6b";
    editBtn.style.color = "white";
    editBtn.style.border = "none";
    editBtn.style.borderRadius = "4px";
    editBtn.style.cursor = "pointer";
    editBtn.style.fontSize = "13px";
    editBtn.onclick = () => editTenancy(tenancy.id);
    
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Slet";
    deleteBtn.style.padding = "4px 12px";
    deleteBtn.style.background = "#d32f2f";
    deleteBtn.style.color = "white";
    deleteBtn.style.border = "none";
    deleteBtn.style.borderRadius = "4px";
    deleteBtn.style.cursor = "pointer";
    deleteBtn.style.fontSize = "13px";
    deleteBtn.onclick = () => deleteTenancy(tenancy.id);
    
    actionsCell.appendChild(editBtn);
    actionsCell.appendChild(deleteBtn);
    
    row.appendChild(idCell);
    row.appendChild(departmentCell);
    row.appendChild(addressCell);
    row.appendChild(cityCell);
    row.appendChild(postalCodeCell);
    row.appendChild(actionsCell);
    
    tbody.appendChild(row);
  });
}

function clearFilter() {
  const filterSelect = document.getElementById("department-filter");
  if (filterSelect) {
    filterSelect.value = "";
    applyFilter();
  }
}

// Form handler setup
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

function closeModalOverlay(event) {
  if (event.target.id === "tenancy-modal") {
    closeTenancyModal();
  }
}

// CREATE: Create new tenancy
async function createTenancy() {
  // Remove the ID field - it will be auto-generated by backend
  const meterNumber = document.getElementById("tenancy-meter-number").value.trim();
  const departmentName = document.getElementById("tenancy-department-id").value.trim();
  const tenancyNumber = document.getElementById("tenancy-number").value.trim();
  const address = document.getElementById("tenancy-address").value.trim();
  const city = document.getElementById("tenancy-city").value.trim();
  const postalCode = document.getElementById("tenancy-postal-code").value.trim();
  const active = document.getElementById("tenancy-active").checked;

  const payload = {
    // id: id,  // Remove this - backend will auto-generate
    meterNumber: meterNumber,
    departmentName: departmentName,
    tenancyNumber: tenancyNumber,
    address: address,
    city: city,
    postalCode: postalCode,
    active: active
  };

  try {
    const res = await fetch("/api/tenancies", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "same-origin",
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || `HTTP ${res.status}`);
    }

    const created = await res.json();
    alert(`Lejemål "${created.id}" er oprettet!`);
    closeTenancyModal();
    await fetchTenancies(); // Re-fetch to update allTenancies
    applyFilter(); // Re-apply current filter
  } catch (err) {
    console.error("Creating tenancy failed:", err);
    alert(`Fejl ved oprettelse: ${err.message}`);
  }
}

// UPDATE: Update tenancy
async function updateTenancy() {
  const id = editingTenancyId;
  const meterNumber = document.getElementById("tenancy-meter-number").value.trim();
  const departmentName = document.getElementById("tenancy-department-id").value.trim();
  const tenancyNumber = document.getElementById("tenancy-number").value.trim();
  const address = document.getElementById("tenancy-address").value.trim();
  const city = document.getElementById("tenancy-city").value.trim();
  const postalCode = document.getElementById("tenancy-postal-code").value.trim();
  const active = document.getElementById("tenancy-active").checked;

  const payload = {
    id: id,
    meterNumber: meterNumber,
    departmentName: departmentName,
    tenancyNumber: tenancyNumber,
    address: address,
    city: city,
    postalCode: postalCode,
    active: active
  };

  try {
    const res = await fetch(`/api/tenancies/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "same-origin",
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || `HTTP ${res.status}`);
    }

    const updated = await res.json();
    alert(`Lejemål "${updated.id}" er opdateret!`);
    closeTenancyModal();
    await fetchTenancies(); // Re-fetch to update allTenancies
    applyFilter(); // Re-apply current filter
  } catch (err) {
    console.error("Updating tenancy failed:", err);
    alert(`Fejl ved opdatering: ${err.message}`);
  }
}

// DELETE: Delete tenancy
async function deleteTenancy(id) {
  if (!confirm(`Er du sikker på, at du vil slette dette lejemål?`)) {
    return;
  }

  try {
    const res = await fetch(`/api/tenancies/${id}`, {
      method: "DELETE",
      credentials: "same-origin"
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || `HTTP ${res.status}`);
    }

    alert(`Lejemål "${id}" er slettet!`);
    await fetchTenancies(); // Re-fetch to update allTenancies
    applyFilter(); // Re-apply current filter
  } catch (err) {
    console.error("Deleting tenancy failed:", err);
    alert(`Fejl ved sletning: ${err.message}`);
  }
}

// Helper function to get department name by ID
function getDepartmentName(departmentId) {
  if (!departmentId) return "-";
  const dept = departments.find(d => d.id === departmentId);
  return dept ? dept.name : departmentId; // Return name if found, otherwise fallback to ID
}

// Display functions
function displayTenancies(tenancies) {
  const tbody = document.getElementById("lejemaal-table")?.querySelector("tbody");
  if (!tbody) return;

  tbody.innerHTML = "";

  tenancies.forEach(tenancy => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${tenancy.tenancyNumber || "-"}</td>
      <td>${tenancy.meterNumber || "-"}</td>
      <td>${getDepartmentName(tenancy.departmentId)}</td>
      <td>${tenancy.address || "-"}</td>
      <td>${tenancy.city || "-"}</td>
      <td>${tenancy.postalCode || "-"}</td>
      <td>
        <button onclick="editTenancy('${tenancy.id}')">Rediger</button>
        <button onclick="deleteTenancy('${tenancy.id}')">Slet</button>
      </td>
    `;
    tbody.appendChild(row);
  });
}

function displayNoTenancies() {
  const tbody = document.getElementById("lejemaal-table")?.querySelector("tbody");
  if (!tbody) return;
  
  tbody.innerHTML = `
    <tr>
      <td colspan="8" style="text-align: center; padding: 20px;">
        Ingen lejemål fundet
      </td>
    </tr>
  `;
}

function displayError() {
  const tbody = document.getElementById("lejemaal-table")?.querySelector("tbody");
  if (!tbody) return;
  
  tbody.innerHTML = `
    <tr>
      <td colspan="8" style="text-align: center; padding: 20px; color: red;">
        Fejl ved indlæsning af lejemål
      </td>
    </tr>
  `;
}

// Reset form
function resetForm() {
  const form = document.getElementById("tenancy-form");
  if (!form) return;
  
  form.reset();
  document.getElementById("tenancy-id-hidden").value = "";
  document.getElementById("tenancy-modal-title").textContent = "Opret nyt lejemål";
  document.getElementById("tenancy-submit-btn").textContent = "Opret";
  editingTenancyId = null;
}

// Edit tenancy
async function editTenancy(id) {
  try {
    const res = await fetch(`/api/tenancies/${id}`, { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    
    const tenancy = await res.json();
    
    document.getElementById("tenancy-id-hidden").value = tenancy.id;
    document.getElementById("tenancy-id").value = tenancy.id || "";
    document.getElementById("tenancy-meter-number").value = tenancy.meterNumber || "";
    document.getElementById("tenancy-department-id").value = tenancy.departmentId || "";
    document.getElementById("tenancy-number").value = tenancy.tenancyNumber || "";
    document.getElementById("tenancy-address").value = tenancy.address || "";
    document.getElementById("tenancy-city").value = tenancy.city || "";
    document.getElementById("tenancy-postal-code").value = tenancy.postalCode || "";
    document.getElementById("tenancy-active").checked = tenancy.active !== false;
    
    document.getElementById("tenancy-modal-title").textContent = "Rediger lejemål";
    document.getElementById("tenancy-submit-btn").textContent = "Opdater";
    
    editingTenancyId = id;
    
    // Disable ID field when editing
    document.getElementById("tenancy-id").disabled = true;
    
    // Open modal
    document.getElementById("tenancy-modal").classList.add("active");
  } catch (err) {
    console.error("Loading tenancy for edit failed:", err);
    alert(`Fejl ved indlæsning: ${err.message}`);
  }
}