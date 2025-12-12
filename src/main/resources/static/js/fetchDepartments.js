let editingDepartmentId = null;
let searchedDepartment = null;

document.addEventListener("DOMContentLoaded", () => {
  setupFormHandler();
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