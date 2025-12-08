document.addEventListener("DOMContentLoaded", loadRules);

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
            const ruleTypeLabels = {
                ruleHeat : "Varme",
                ruleHumidity : "Luftfugtighed",
                ruleWater : "Vand"
            };
            const ruleType = 
            ruleTypeLabels[rule.type] || ruleTypeLabels[rule.ruleType] || rule.type || rule.ruleType || "Ukendt type";

            card.innerHTML = `
                <div class="rule-main">
                    <div class="rule-name">${escapeHtml(rule.name || "")}</div>
                    <div class="rule-description">${escapeHtml(rule.description || "")}</div>
                    <div class="rule-meta">Type: ${escapeHtml(ruleType)}</div>
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
        document.getElementById("rule-type-display").textContent =
            rule.type || rule.ruleType || "Ukendt type";

        document.getElementById("rule-name-input").value = rule.name || "";
        document.getElementById("rule-desc-input").value = rule.description || "";
        document.getElementById("rule-duration-input").value =
            rule.duration || rule.hours || 24;

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
        duration: parseInt(document.getElementById("rule-duration-input").value, 10)
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