let editingDepartmentId = null;

document.addEventListener("DOMContentLoaded", () => {
  fetchDepartments();
  setupFormHandler();
});

// READ: Get all departments
async function fetchDepartments() {
  try {
    const res = await fetch("/api/departments", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);

    const departments = await res.json();
    if (!departments || departments.length === 0) {
      displayNoDepartments();
      return;
    }

    displayDepartments(departments);
  } catch (err) {
    console.error("Fetching departments failed:", err);
    displayError();
  }
}

// READ: Get single department by ID
async function getDepartmentById(id) {
  try {
    const res = await fetch(`/api/departments/${id}`, { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    return await res.json();
  } catch (err) {
    console.error("Fetching department failed:", err);
    throw err;
  }
}

function displayDepartments(departments) {
  const tbody = document.querySelector("#afdelinger-table tbody");
  if (!tbody) return;

  tbody.innerHTML = "";

  departments.forEach(dept => {
    const row = document.createElement("tr");
    
    const idCell = document.createElement("td");
    idCell.textContent = dept.id || "-";
    
    const emailCell = document.createElement("td");
    emailCell.textContent = dept.email || "-";
    
    const statusCell = document.createElement("td");
    statusCell.textContent = dept.isActive ? "Aktiv" : "Inaktiv";
    
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
    editBtn.onclick = () => editDepartment(dept.id);
    
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Slet";
    deleteBtn.style.padding = "4px 12px";
    deleteBtn.style.background = "#d32f2f";
    deleteBtn.style.color = "white";
    deleteBtn.style.border = "none";
    deleteBtn.style.borderRadius = "4px";
    deleteBtn.style.cursor = "pointer";
    deleteBtn.style.fontSize = "13px";
    deleteBtn.onclick = () => deleteDepartment(dept.id);
    
    actionsCell.appendChild(editBtn);
    actionsCell.appendChild(deleteBtn);
    
    row.appendChild(idCell);
    row.appendChild(emailCell);
    row.appendChild(statusCell);
    row.appendChild(actionsCell);
    
    tbody.appendChild(row);
  });
}

function displayNoDepartments() {
  const tbody = document.querySelector("#afdelinger-table tbody");
  if (!tbody) return;
  
  const row = document.createElement("tr");
  const cell = document.createElement("td");
  cell.colSpan = 4;
  cell.textContent = "Ingen afdelinger fundet";
  cell.style.textAlign = "center";
  cell.style.padding = "20px";
  row.appendChild(cell);
  tbody.appendChild(row);
}

function displayError() {
  const tbody = document.querySelector("#afdelinger-table tbody");
  if (!tbody) return;
  
  const row = document.createElement("tr");
  const cell = document.createElement("td");
  cell.colSpan = 4;
  cell.textContent = "Fejl ved indlæsning af afdelinger";
  cell.style.textAlign = "center";
  cell.style.padding = "20px";
  cell.style.color = "#d32f2f";
  row.appendChild(cell);
  tbody.appendChild(row);
}

function setupFormHandler() {
  const form = document.getElementById("department-form");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    
    if (editingDepartmentId) {
      await updateDepartment();
    } else {
      await createDepartment();
    }
  });
}

// Modal functions
function openDepartmentModal() {
  resetForm();
  document.getElementById("department-modal").classList.add("active");
}

function closeDepartmentModal() {
  document.getElementById("department-modal").classList.remove("active");
  resetForm();
}

function closeModalOnOverlay(event) {
  if (event.target.id === "department-modal") {
    closeDepartmentModal();
  }
}

// CREATE: Create new department
async function createDepartment() {
  const id = document.getElementById("dept-id").value.trim();
  const email = document.getElementById("dept-email").value.trim();
  const active = document.getElementById("dept-active").checked;

  const payload = {
    id: id,
    email: email,
    active: active
  };

  try {
    const res = await fetch("/api/departments", {
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
    alert(`Afdeling "${created.id}" er oprettet!`);
    closeDepartmentModal();
    fetchDepartments();
  } catch (err) {
    console.error("Creating department failed:", err);
    alert(`Fejl ved oprettelse: ${err.message}`);
  }
}

// UPDATE: Load department for editing
async function editDepartment(id) {
  try {
    const dept = await getDepartmentById(id);
    
    document.getElementById("department-id-hidden").value = dept.id;
    document.getElementById("dept-id").value = dept.id;
    document.getElementById("dept-id").disabled = true;
    document.getElementById("dept-email").value = dept.email;
    document.getElementById("dept-active").checked = dept.isActive;
    
    document.getElementById("modal-title").textContent = "Rediger afdeling";
    document.getElementById("submit-btn").textContent = "Opdater";
    
    editingDepartmentId = id;
    
    // Open modal
    document.getElementById("department-modal").classList.add("active");
  } catch (err) {
    console.error("Loading department for edit failed:", err);
    alert(`Fejl ved indlæsning: ${err.message}`);
  }
}

// UPDATE: Update department
async function updateDepartment() {
  const id = editingDepartmentId;
  const email = document.getElementById("dept-email").value.trim();
  const active = document.getElementById("dept-active").checked;

  const payload = {
    id: id,
    email: email,
    active: active
  };

  try {
    const res = await fetch(`/api/departments/${id}`, {
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
    alert(`Afdeling "${updated.id}" er opdateret!`);
    closeDepartmentModal();
    fetchDepartments();
  } catch (err) {
    console.error("Updating department failed:", err);
    alert(`Fejl ved opdatering: ${err.message}`);
  }
}

// DELETE: Delete department
async function deleteDepartment(id) {
  if (!confirm(`Er du sikker på, at du vil slette afdeling "${id}"?`)) {
    return;
  }

  try {
    const res = await fetch(`/api/departments/${id}`, {
      method: "DELETE",
      credentials: "same-origin"
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || `HTTP ${res.status}`);
    }

    alert(`Afdeling "${id}" er slettet!`);
    fetchDepartments();
  } catch (err) {
    console.error("Deleting department failed:", err);
    alert(`Fejl ved sletning: ${err.message}`);
  }
}

function resetForm() {
  document.getElementById("department-form").reset();
  document.getElementById("department-id-hidden").value = "";
  document.getElementById("dept-id").disabled = false;
  document.getElementById("modal-title").textContent = "Opret ny afdeling";
  document.getElementById("submit-btn").textContent = "Opret";
  editingDepartmentId = null;
}