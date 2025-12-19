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
    `;

    const emailEl = row.querySelector(".dept-email");
    if (emailEl) {
      emailEl.addEventListener("click", (e) => e.stopPropagation());
    }

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