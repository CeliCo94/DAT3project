document.getElementById("ruleForm").addEventListener("submit", async function (e) {
  e.preventDefault(); // Stopper browserens default submit-adfærd
  
  const formData = {
    name: document.getElementById("name").value,
    thresholdTempIn: parseInt(document.getElementById("thresholdTempIn").value, 10),
    thresholdTempOut: parseInt(document.getElementById("thresholdTempOut").value, 10),
    thresholdWaterFlow: parseFloat(document.getElementById("thresholdWaterFlow").value),
    duration: parseInt(document.getElementById("duration").value, 10)
  };

  try {
    const response = await fetch("api/regler", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(formData)
    });

    if (response.ok) {
      const result = await response.json();
      alert(`Regel oprettet med navn: ${result.name}`);
    } else {
      console.error("Fejl ved POST:", response.statusText);
      alert("Der opstod en fejl. Tjek konsollen.");
    }
  } catch (error) {
    console.error("Netværksfejl:", error);
    alert("Kunne ikke kommunikere med serveren.");
  }
});