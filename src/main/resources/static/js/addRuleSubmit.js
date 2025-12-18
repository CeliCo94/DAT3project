document.getElementById("addRuleForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const msgEl = document.getElementById("formMessage");
    const submitBtn = document.querySelector('#addRuleForm input[type="submit"]');

    const setMessage = (text, kind) => {
        if (!msgEl) return;
        msgEl.textContent = text;
        msgEl.classList.remove("success", "error");
        if (kind) msgEl.classList.add(kind);
        msgEl.style.display = "block";
    };

    const body = {
        type: document.getElementById("selectRuleType").value,
        name: document.getElementById("ruleName").value,
        description: document.getElementById("ruleDescription").value,
        criticality: document.getElementById("selectRuleCriticality").value,
        duration: Number(document.getElementById("ruleDuration").value),
        active: true,
        thresholdTempIn: Number(document.getElementById("thresholdTempIn")?.value) || null,
        thresholdTempOut: Number(document.getElementById("thresholdTempOut")?.value) || null,
        thresholdHeatWaterFlow: Number(document.getElementById("thresholdHeatWaterFlow")?.value) || null,
        thresholdWaterFlow: Number(document.getElementById("thresholdWaterFlow")?.value) || null,
        thresholdHumidity: Number(document.getElementById("thresholdHumidity")?.value) || null
    };

    try {
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.value = "Gemmer...";
        }

        const response = await fetch("/api/addRule", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (!response.ok) {
            const errText = await response.text();
            console.error("Server error:", errText);
            throw new Error("Kunne ikke gemme regel");
        }

        await response.json();

        setMessage("Reglen er gemt og startet ✅", "success");

        document.getElementById("addRuleForm").reset();

        setTimeout(() => {
            document.getElementById("formMessage").style.display = "none";
        }, 2000);

        } catch (err) {
        console.error(err);
        setMessage("Der skete en fejl. Prøv igen.", "error");
        } finally {
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.value = "Start regel";
            }
        }
});