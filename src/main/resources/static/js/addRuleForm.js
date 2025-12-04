document.getElementById("selectRuleType").addEventListener("change", function (e) {
    
    const selectedValue = e.target.value;

    const description = document.getElementById("description");
    const thresholds = document.getElementById("thresholds");
    const submit = document.getElementById("submit");

    if (selectedValue === "ruleHeat" || selectedValue === "ruleWater" || selectedValue === "ruleHumidity") {
        description.style.display = "block";
        thresholds.style.display = "block";
        submit.style.display = "block";

    } else {
        description.style.display = "none";
        thresholds.style.display = "none";
        submit.style.display = "none";
    }

    const select = document.getElementById("selectRuleType");
    const selectedText = select.options[select.selectedIndex].text;
    document.getElementById("ruleTypeDisplay").textContent = "Type af regel:    " + selectedText;

    const container = document.getElementById("thresholds");

    container.innerHTML = "";
    const header = document.createElement("h3");
    header.classList.add("container-title");
    header.textContent = "Tærskelværdier";
    container.appendChild(header)

    const br = () => document.createElement("br");

    const addField = (labelText, id, placeholder) => {
        const label = document.createElement("label");
        label.for = id;
        label.textContent = labelText;

        const input = document.createElement("input");
        input.type = "number";
        input.id = id;
        input.name = id;
        input.placeholder = placeholder;

        container.appendChild(label);
        container.appendChild(input);
    };

    if (selectedValue === "ruleHeat") {
        addField("Laveste temperatur for fremløb (°C)", "thresholdTempIn", 60);
        addField("Laveste temperatur for tilbageløb (°C)", "thresholdTempOut", 30);
        addField("Laveste gennemstrømning af vand (L/min)", "thresholdHeatWaterFlow", 20);
    }

    if (selectedValue === "ruleWater") {
        addField("Laveste gennemstrømning af vand (L/min)", "thresholdWaterFlow", 600);
    }

    if (selectedValue === "ruleHumidity") {
        addField("Laveste luftfugtighed (%)", "thresholdHumidity", 60);
    }
});
