document.addEventListener("DOMContentLoaded", () => {
  fetchNotification();
});

async function fetchNotification() {
  try {
    const res = await fetch("/notifications/fetch", { credentials: "same-origin" });
    if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);

    const n = await res.json();
    if (!n) return;

    // Første række i første tabel (Service Center 1)
    const row = document.querySelector("#sc1-table tbody tr");
    if (!row) return;

    const [tdNotif, tdRule, tdCrit, tdStatus] = row.children;
    tdNotif.textContent = `${n.address} — ${n.cause}`;
    tdRule.textContent = n.rule;
    tdCrit.textContent = n.criticality;
    tdStatus.textContent = `${n.active ? "Aktiv" : "Inaktiv"} · ${n.sent ? "Sendt" : "Ikke sendt"}`;
  } catch (err) {
    console.error("Fetching notification failed:", err);
  }
}
