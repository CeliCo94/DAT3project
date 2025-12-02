document.getElementById("addRuleForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const body = {
        type: document.getElementById("selectRuleType").value,
        name: document.getElementById("ruleName").value,
        description: document.getElementById("ruleDescription").value,
        duration: Number(document.getElementById("ruleDuration").value),
        thresholdTempIn: Number(document.getElementById("thresholdTempIn")?.value) || null,
        thresholdTempOut: Number(document.getElementById("thresholdTempOut")?.value) || null,
        thresholdHeatWaterFlow: Number(document.getElementById("thresholdHeatWaterFlow")?.value) || null,
        thresholdWaterFlow: Number(document.getElementById("thresholdWaterFlow")?.value) || null,
        thresholdHumidity: Number(document.getElementById("thresholdHumidity")?.value) || null
    };

    console.log("fetch request body:", body);

    const response = await fetch("/api/addRule", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });

    const data = await response.json();
    console.log("Server replied:", data);
});
