document.addEventListener("DOMContentLoaded", () => {
  loadNotifications();
});

function formatDateTime(isoString) {
  if (!isoString) return "-";
  const d = new Date(isoString);
  if (isNaN(d.getTime())) return isoString; 
  return d.toLocaleString("da-DK", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
}

async function loadNotifications() {
  try {
    const response = await fetch("/api/notifications");
    if (!response.ok) {
      throw new Error("Failed to fetch notifications");
    }

    const notifications = await response.json();
    renderTables(notifications);

  } catch (err) {
    console.error("Error loading notifications:", err);
  }
}

function renderTables(notifications) {

  const activeBody = document.getElementById("active-table-body");
  const historicalBody = document.getElementById("historical-table-body");

  // Clear old rows
  activeBody.innerHTML = "";
  historicalBody.innerHTML = "";

  notifications.forEach(n => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${n.address}</td>
      <td>${n.rule}</td>
      <td>${n.criticality}</td>
      <td>${n.active ? "Aktiv" : "Lukket"}</td>
      <td>${formatDateTime(n.timeStamp)}</td>
    `;

    if (n.active) {
      activeBody.appendChild(row);
    } else {
      historicalBody.appendChild(row);
    }
  });
}
