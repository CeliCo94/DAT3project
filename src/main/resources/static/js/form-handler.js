let isEditing = false;
let currentRuleId = null;

async function loadRules() {
  try {
    const response = await fetch("/api/regler");
    if (!response.ok) {
      console.error("Fejl ved GET /api/regler:", response.status, response.statusText);
      return;
    }

    const rules = await response.json();
    console.log("Regler fra backend:", rules); // debug

    const tbody = document.getElementById("rulesTableBody");
    if (!tbody) {
      console.error("Kunne ikke finde <tbody id='rulesTableBody'>");
      return;
    }

    tbody.innerHTML = "";

    rules.forEach(rule => {
      const tr = document.createElement("tr");

      const nameTd = document.createElement("td");
      nameTd.textContent = rule.name ?? "";

      const descTd = document.createElement("td");
      descTd.textContent = rule.description ?? "";

      const actionTd = document.createElement("td");
      const editBtn = document.createElement("button");
      editBtn.type = "button";
      editBtn.textContent = "Redigér";
      editBtn.addEventListener("click", () => startEditRule(rule.id));

      actionTd.appendChild(editBtn);

      tr.appendChild(nameTd);
      tr.appendChild(descTd);
      tr.appendChild(actionTd);

      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Netværksfejl i loadRules:", err);
  }
}

async function startEditRule(id) {
  try {
    const response = await fetch(`/api/regler/${id}`);
    if (!response.ok) {
      console.error("Fejl ved GET /api/regler/" + id, response.status, response.statusText);
      return;
    }

    const rule = await response.json();

    isEditing = true;
    currentRuleId = rule.id;

    document.getElementById("ruleId").value = rule.id ?? "";
    document.getElementById("name").value = rule.name ?? "";
    document.getElementById("description").value = rule.description ?? "";
    document.getElementById("thresholdTempIn").value = rule.thresholdTempIn ?? "";
    document.getElementById("thresholdTempOut").value = rule.thresholdTempOut ?? "";
    document.getElementById("thresholdHeatWaterFlow").value = rule.thresholdHeatWaterFlow ?? "";
    document.getElementById("duration").value = rule.duration ?? "";

    document.getElementById("submitButton").textContent = "Gem ændringer";
  } catch (err) {
    console.error("Netværksfejl i startEditRule:", err);
  }
}

async function handleSubmit(event) {
  event.preventDefault();

  const nameEl = document.getElementById("name");
  const descEl = document.getElementById("description");
  const inEl = document.getElementById("thresholdTempIn");
  const outEl = document.getElementById("thresholdTempOut");
  const flowEl = document.getElementById("thresholdHeatWaterFlow");
  const durEl = document.getElementById("duration");

  const body = {
    id: currentRuleId,
    name: nameEl.value,
    description: descEl.value,
    thresholdTempIn: inEl.value ? Number(inEl.value) : null,
    thresholdTempOut: outEl.value ? Number(outEl.value) : null,
    thresholdHeatWaterFlow: flowEl.value ? Number(flowEl.value) : null,
    duration: durEl.value ? Number(durEl.value) : null,
    type: "ruleHeat"   
  };

  let url = "/api/regler";
  let method = "POST";

  if (isEditing && currentRuleId) {
    url = `/api/regler/${currentRuleId}`;
    method = "PUT";
  }

  try {
    const response = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    });

    if (!response.ok) {
      console.error("Fejl ved " + method + " " + url, response.status, response.statusText);
      alert("Der opstod en fejl – se konsollen.");
      return;
    }

    document.getElementById("ruleForm").reset();
    document.getElementById("ruleId").value = "";
    isEditing = false;
    currentRuleId = null;
    document.getElementById("submitButton").textContent = "Opret regel";

    await loadRules();
  } catch (err) {
    console.error("Netværksfejl i handleSubmit:", err);
    alert("Kunne ikke kommunikere med serveren.");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("ruleForm");
  if (form) {
    form.addEventListener("submit", handleSubmit);
  }
  loadRules();
});
