document.addEventListener("DOMContentLoaded", loadRules);

const RULE_TYPE_LABELS = {
    ruleHeat: "Varme",
    ruleHumidity: "Luftfugtighed",
    ruleWater: "Vand"
};

function getRuleTypeLabel(rule) {
    const typeKey = rule.type || rule.ruleType;  
    if (!typeKey) return "Ukendt type";

    return RULE_TYPE_LABELS[typeKey] || typeKey || "Ukendt type";
}

async function loadRules() {
    try {
        const res = await fetch("/api/rules");
        if (!res.ok) {
            throw new Error("Kunne ikke hente regler");
        }

        const rules = await res.json();

        // optional: sort, e.g. by type then name
        rules.sort((a, b) => {
            const typeA = (a.type || "").localeCompare(b.type || "");
            if (typeA !== 0) return typeA;
            return (a.name || "").localeCompare(b.name || "");
        });

        const container = document.getElementById("rules-list");
        container.innerHTML = "";

        rules.forEach(rule => {
            const card = document.createElement("div");
            card.className = "rule-card";

            const active = !!rule.active;
            const ruleType = getRuleTypeLabel(rule);
            const thresholdsSummary = buildThresholdSummary(rule);

            card.innerHTML = `
                <div class="rule-main">
                    <div class="rule-name">${escapeHtml(rule.name || "")}</div>
                    <div class="rule-description">${escapeHtml(rule.description || "")}</div>
                    <div class="rule-meta">Type: ${escapeHtml(ruleType)}</div>
                    ${
                        thresholdsSummary
                        ? `<div class="rule-thresholds">${escapeHtml(thresholdsSummary)}</div>`
                        : ""
                    }
                </div>

                <div class="rule-actions">
                    <span class="rule-status ${active ? "active" : "inactive"}"
                          id="rule-status-${rule.id}">
                        ${active ? "Aktiv" : "Inaktiv"}
                    </span>

                    <label class="switch">
                        <input type="checkbox"
                               id="toggle-${rule.id}"
                               ${active ? "checked" : ""}
                               onchange="toggleRule('${rule.id}', this)">
                        <span class="slider"></span>
                    </label>

                    <button class="rule-edit-btn" type="button"
                            onclick="openEditRule('${rule.id}')">
                        Redigér
                    </button>
                </div>
            `;

            container.appendChild(card);
        });

    } catch (err) {
        console.error(err);
        alert("Der opstod en fejl ved hentning af regler.");
    }
}

async function toggleRule(id, checkboxElem) {
    // Optimistisk UI: disable mens vi sender request
    checkboxElem.disabled = true;

    try {
        const res = await fetch(`/api/rules/${id}/toggle`, {
            method: "PATCH"
        });

        if (!res.ok) {
            throw new Error("Fejl ved opdatering af regel");
        }

        const updated = await res.json();
        const isActive = !!updated.active;

        const statusElem = document.getElementById(`rule-status-${id}`);
        if (statusElem) {
            statusElem.textContent = isActive ? "Aktiv" : "Inaktiv";
            statusElem.classList.toggle("active", isActive);
            statusElem.classList.toggle("inactive", !isActive);
        }

    } catch (err) {
        console.error(err);
        alert("Kunne ikke opdatere regel. Prøv igen.");

        // Rul UI tilbage hvis fejl
        checkboxElem.checked = !checkboxElem.checked;
    } finally {
        checkboxElem.disabled = false;
    }
}

// Simple escaping so descriptions can't break the HTML
function escapeHtml(str) {
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

let currentRule = null;

async function openEditRule(id) {
    try {
        const res = await fetch(`/api/rules/${id}`);
        if (!res.ok) {
            throw new Error("Kunne ikke hente regel");
        }

        const rule = await res.json();
        currentRule = rule;

        document.getElementById("rule-id").value = rule.id;
        document.getElementById("rule-type-display").textContent = getRuleTypeLabel(rule);

        document.getElementById("rule-name-input").value = rule.name || "";
        document.getElementById("rule-desc-input").value = rule.description || "";
        document.getElementById("rule-duration-input").value =
            rule.duration || rule.hours || 24;
        
        fillThresholdInputs(rule);
        updateThresholdVisibility(rule);

        document.getElementById("rule-modal").classList.remove("hidden");
    } catch (err) {
        console.error(err);
        alert("Kunne ikke åbne redigering af regel.");
    }
}

function closeRuleModal() {
    document.getElementById("rule-modal").classList.add("hidden");
    currentRule = null;
}

function getIntValueOrNull(inputId) {
    const el = document.getElementById(inputId);
    if (!el) {
        return null;
    }
    const value = el.value;
    if (value === "") {
        return null; 
    }
    const num = parseInt(value, 10);
    if (isNaN(num)) {
        return null;
    }
    return num;
}

async function saveRule(event) {
    event.preventDefault();
    if (!currentRule) {
        return;
    }

    const id = document.getElementById("rule-id").value;

    const updatedRule = {
        ...currentRule,
        name: document.getElementById("rule-name-input").value,
        description: document.getElementById("rule-desc-input").value,
        duration: parseInt(document.getElementById("rule-duration-input").value, 10),
        thresholdTempIn:        getIntValueOrNull("rule-thresholdTempIn"),
        thresholdTempOut:       getIntValueOrNull("rule-thresholdTempOut"),
        thresholdHeatWaterFlow: getIntValueOrNull("rule-thresholdHeatWaterFlow"),
        thresholdHumidity:      getIntValueOrNull("rule-thresholdHumidity"),
        thresholdWaterFlow:     getIntValueOrNull("rule-thresholdWaterFlow")
    };

    try {
        const res = await fetch(`/api/rules/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedRule)
        });

        if (!res.ok) {
            throw new Error("Kunne ikke gemme regel");
        }

        await res.json(); 

        closeRuleModal();
        loadRules(); // refresh kortene

    } catch (err) {
        console.error(err);
        alert("Der opstod en fejl under gem af regel.");
    }
}

function toggleRow(rowId, show) {
    const row = document.getElementById(rowId);
    if (!row) return; 
    row.style.display = show ? "" : "none";
}

function updateThresholdVisibility(rule) {
    const type = rule.type || rule.ruleType;

    const isHeat = type === "ruleHeat";
    const isWater = type === "ruleWater";
    const isHumidity = type === "ruleHumidity";

    toggleRow("thresholdTempIn", isHeat);
    toggleRow("thresholdTempOut", isHeat);
    toggleRow("thresholdHeatWaterFlow", isHeat);
    toggleRow("thresholdWaterFlow", isWater);
    toggleRow("thresholdHumidity", isHumidity);
}

function fillThresholdInputs(rule) {
    const safe = (v) => (v === null || v === undefined ? "" : v);

    const tempInInput = document.getElementById("rule-thresholdTempIn");
    const tempOutInput = document.getElementById("rule-thresholdTempOut");
    const heatFlowInput = document.getElementById("rule-thresholdHeatWaterFlow");
    const humidityInput = document.getElementById("rule-thresholdHumidity");
    const waterFlowInput = document.getElementById("rule-thresholdWaterFlow");

    if (tempInInput) tempInInput.value = safe(rule.thresholdTempIn);
    if (tempOutInput) tempOutInput.value = safe(rule.thresholdTempOut);
    if (heatFlowInput) heatFlowInput.value = safe(rule.thresholdHeatWaterFlow);
    if (humidityInput) humidityInput.value = safe(rule.thresholdHumidity);
    if (waterFlowInput) waterFlowInput.value = safe(rule.thresholdWaterFlow);
}

function buildThresholdSummary(rule) {
    const parts = [];

    if (rule.thresholdTempIn != null) {
        parts.push(`Fremløb ≥ ${rule.thresholdTempIn}°C`);
    }
    if (rule.thresholdTempOut != null) {
        parts.push(`Tilbageløb ≥ ${rule.thresholdTempOut}°C`);
    }
    if (rule.thresholdHeatWaterFlow != null) {
        parts.push(`Varmevand ≥ ${rule.thresholdHeatWaterFlow} L/min`);
    }
    if (rule.thresholdWaterFlow != null) {
        parts.push(`Vand ≥ ${rule.thresholdWaterFlow} L/min`);
    }
    if (rule.thresholdHumidity != null) {
        parts.push(`Luftfugtighed ≥ ${rule.thresholdHumidity}%`);
    }

    return parts.join(" • ");
}