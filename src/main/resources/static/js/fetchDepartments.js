let editingDepartmentId = null;
let searchedDepartment = null;
let allDepartments = [];
let filteredDepartments = [];

document.addEventListener("DOMContentLoaded", () => {
  setupFormHandler();
  setupListUI();
  fetchAllDepartments();
});

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

// Search for department by name (searches all departments and filters by name)
async function searchDepartment() {
  const searchInput = document.getElementById("search-department-name");
  const departmentName = searchInput.value.trim();
  const errorDiv = document.getElementById("search-error");
  const resultDiv = document.getElementById("search-results");
  
  // Hide previous results and errors
  resultDiv.classList.remove("active");
  errorDiv.classList.remove("active");
  errorDiv.textContent = "";
  
  if (!departmentName) {
    errorDiv.textContent = "Indtast venligst et afdeling navn";
    errorDiv.classList.add("active");
    return;
  }
  
  try {
    // Fetch all departments and search by name
    const res = await fetch("/api/departments", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
    
    const departments = await res.json();
    const found = departments.find(dept => 
      dept.name && dept.name.toLowerCase() === departmentName.toLowerCase()
    );
    
    if (!found) {
      errorDiv.textContent = `Afdeling med navn "${departmentName}" blev ikke fundet.`;
      errorDiv.classList.add("active");
      searchedDepartment = null;
      return;
    }
    
    searchedDepartment = found;
    
    // Display result
    document.getElementById("result-name").textContent = found.name || "-";
    document.getElementById("result-email").textContent = found.email || "-";
    
    resultDiv.classList.add("active");
  } catch (err) {
    errorDiv.textContent = `Fejl ved søgning: ${err.message}`;
    errorDiv.classList.add("active");
    searchedDepartment = null;
  }
}

function editSearchedDepartment() {
  if (!searchedDepartment) return;
  editDepartment(searchedDepartment.id);
}

async function deleteSearchedDepartment() {
  if (!searchedDepartment) return;
  await deleteDepartment(searchedDepartment.id);
  // Clear search result after deletion
  document.getElementById("search-results").classList.remove("active");
  document.getElementById("search-department-name").value = "";
  searchedDepartment = null;
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
  const name = document.getElementById("dept-name").value.trim();
  const email = document.getElementById("dept-email").value.trim();

  // Backend will auto-generate ID, so we only send name and email
  const payload = {
    name: name,
    email: email
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
    await fetchAllDepartments();
    alert(`Afdeling "${created.name || created.id}" er oprettet!`);
    closeDepartmentModal();
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
    document.getElementById("dept-name").value = dept.name || "";
    document.getElementById("dept-email").value = dept.email;
    
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
  const name = document.getElementById("dept-name").value.trim();
  const email = document.getElementById("dept-email").value.trim();

  const payload = {
    name: name,
    email: email
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
    await fetchAllDepartments();
    alert(`Afdeling "${updated.name || updated.id}" er opdateret!`);
    closeDepartmentModal();
    
    // Update search result if this department was searched
    if (searchedDepartment && searchedDepartment.id === id) {
      searchedDepartment = updated;
      document.getElementById("result-name").textContent = updated.name || "-";
      document.getElementById("result-email").textContent = updated.email;
    }
  } catch (err) {
    console.error("Updating department failed:", err);
    alert(`Fejl ved opdatering: ${err.message}`);
  }
}

// DELETE: Delete department
async function deleteDepartment(id) {
  if (!confirm(`Er du sikker på, at du vil slette denne afdeling?`)) {
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

    await fetchAllDepartments();
    alert(`Afdeling er slettet!`);
    
    // Clear search result after deletion
    if (searchedDepartment && searchedDepartment.id === id) {
      document.getElementById("search-results").classList.remove("active");
      document.getElementById("search-department-name").value = "";
      searchedDepartment = null;
    }
  } catch (err) {
    console.error("Deleting department failed:", err);
    alert(`Fejl ved sletning: ${err.message}`);
  }
}

function resetForm() {
  document.getElementById("department-form").reset();
  document.getElementById("department-id-hidden").value = "";
  document.getElementById("modal-title").textContent = "Opret ny afdeling";
  document.getElementById("submit-btn").textContent = "Opret";
  editingDepartmentId = null;
}




function setupListUI() {
  const searchInput = document.getElementById("dept-search-input");
  const clearBtn = document.getElementById("dept-clear-btn");

  if (searchInput) {
    searchInput.addEventListener("input", () => {
      const q = searchInput.value.trim().toLowerCase();
      filteredDepartments = filterDepartments(allDepartments, q);
      renderDepartmentList(filteredDepartments);
    });
  }

  if (clearBtn && searchInput) {
    clearBtn.addEventListener("click", () => {
      searchInput.value = "";
      filteredDepartments = [...allDepartments];
      renderDepartmentList(filteredDepartments);
      searchInput.focus();
    });
  }
}

async function fetchAllDepartments() {
  const errorDiv = document.getElementById("dept-error");
  if (errorDiv) {
    errorDiv.classList.remove("active");
    errorDiv.textContent = "";
  }

  try {
    const res = await fetch("/api/departments", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);

    allDepartments = await res.json();
    allDepartments.sort((a, b) => (a.name || "").localeCompare(b.name || "", "da"));

    filteredDepartments = [...allDepartments];
    updateDeptCount(filteredDepartments.length);
    renderDepartmentList(filteredDepartments);
  } catch (err) {
    console.error("Fetching departments failed:", err);
    if (errorDiv) {
      errorDiv.textContent = "Fejl ved indlæsning af afdelinger";
      errorDiv.classList.add("active");
    }
  }
}

function filterDepartments(list, q) {
  if (!q) return [...list];
  return list.filter(d => {
    const name = (d.name || "").toLowerCase();
    const email = (d.email || "").toLowerCase();
    return name.includes(q) || email.includes(q);
  });
}

function renderDepartmentList(list) {
  const container = document.getElementById("dept-list");
  const empty = document.getElementById("dept-empty");

  if (!container) return;

  container.innerHTML = "";

  if (!list || list.length === 0) {
    if (empty) empty.style.display = "block";
    updateDeptCount(0);
    return;
  }

  if (empty) empty.style.display = "none";
  updateDeptCount(list.length);

  list.forEach(dept => {
    const row = document.createElement("div");
    row.className = "dept-row";

    const initial = (dept.name || "?").trim().charAt(0).toUpperCase();

    row.innerHTML = `
      <div class="dept-badge">${escapeHtml(initial)}</div>
      <div class="dept-info">
        <div class="dept-name">${escapeHtml(dept.name || "-")}</div>
        <div class="dept-email">${dept.email ? escapeHtml(dept.email) : "-"}</div>
      </div>
      <div class="dept-actions">
        <button class="btn btn-secondary" type="button">Rediger</button>
        <button class="btn btn-danger" type="button">Slet</button>
      </div>
    `;

    const emailEl = row.querySelector(".dept-email");
    if (emailEl) {
      emailEl.addEventListener("click", (e) => e.stopPropagation());
    }

    const [editBtn, deleteBtn] = row.querySelectorAll("button");
    row.addEventListener("click", () => editDepartment(dept.id));
    
    editBtn.addEventListener("click", (e) => {
      e.stopPropagation();
      editDepartment(dept.id);
    });

    deleteBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      await deleteDepartment(dept.id);
      await fetchAllDepartments();
    });

    container.appendChild(row);
  });
}

function updateDeptCount(n) {
  const el = document.getElementById("dept-count");
  if (el) el.textContent = String(n ?? 0);
}

function escapeHtml(str) {
  return String(str)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}