import { useEffect, useState } from "react";

export default function Regler() {
  const [form, setForm] = useState({
    name: "",
    thresholdTempIn: "",
    thresholdTempOut: "",
    thresholdWaterFlow: "",
    duration: "",
  });

  const [rules, setRules] = useState([]);
  const [status, setStatus] = useState("idle"); // "idle" | "loading" | "success" | "error"
  const [message, setMessage] = useState("");

  // 1) Load existing rules from backend when page opens
  useEffect(() => {
    async function fetchRules() {
      try {
        const res = await fetch("/api/regler");
        if (!res.ok) {
          throw new Error("Kunne ikke hente regler");
        }
        const data = await res.json();
        setRules(data);
      } catch (err) {
        console.error(err);
      }
    }
    fetchRules();
  }, []);

  // 2) Update form state when user types
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // 3) When user clicks "Gem og kør regel"
  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      name: form.name,
      thresholdTempIn: Number(form.thresholdTempIn),
      thresholdTempOut: Number(form.thresholdTempOut),
      thresholdWaterFlow: Number(form.thresholdWaterFlow),
      duration: Number(form.duration),
    };

    try {
      setStatus("loading");
      setMessage("");

      const res = await fetch("/api/regler", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        throw new Error("Server-fejl");
      }

      const savedRule = await res.json();

      // Update list: remove rule with same name (edit) and add new one
      setRules((prev) => {
        const withoutSameName = prev.filter((r) => r.name !== savedRule.name);
        return [...withoutSameName, savedRule];
      });

      setStatus("success");
      setMessage(`Regel "${savedRule.name}" er gemt og kørt på test-data.`);
    } catch (err) {
      console.error(err);
      setStatus("error");
      setMessage("Noget gik galt da reglen skulle gemmes.");
    }
  };

  // 4) When user clicks "Rediger" on a rule in the list → fill form
  const handleEditClick = (rule) => {
    setForm({
      name: rule.name ?? "",
      thresholdTempIn: rule.thresholdTempIn?.toString() ?? "",
      thresholdTempOut: rule.thresholdTempOut?.toString() ?? "",
      thresholdWaterFlow: rule.thresholdWaterFlow?.toString() ?? "",
      duration: rule.duration?.toString() ?? "",
    });
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <section className="card">
      <h2>Regler</h2>

      {/* FORM */}
      <form id="ruleForm" onSubmit={handleSubmit}>
        <label htmlFor="name">
          <strong>Navngiv reglen</strong>:
        </label>
        <br />
        <input
          type="text"
          id="name"
          name="name"
          required
          value={form.name}
          onChange={handleChange}
        />
        <br />
        <br />

        <label htmlFor="thresholdTempIn">
          Grænseværdi for fremløbstemperatur:
        </label>
        <br />
        <input
          type="number"
          id="thresholdTempIn"
          name="thresholdTempIn"
          required
          value={form.thresholdTempIn}
          onChange={handleChange}
        />
        <br />
        <br />

        <label htmlFor="thresholdTempOut">
          Grænseværdi for returløbstemperatur:
        </label>
        <br />
        <input
          type="number"
          id="thresholdTempOut"
          name="thresholdTempOut"
          required
          value={form.thresholdTempOut}
          onChange={handleChange}
        />
        <br />
        <br />

        <label htmlFor="thresholdWaterFlow">
          Grænseværdi for flow:
        </label>
        <br />
        <input
          type="number"
          id="thresholdWaterFlow"
          name="thresholdWaterFlow"
          required
          value={form.thresholdWaterFlow}
          onChange={handleChange}
        />
        <br />
        <br />

        <label htmlFor="duration">
          Grænseværdi for varighed (timer):
        </label>
        <br />
        <input
          type="number"
          id="duration"
          name="duration"
          required
          value={form.duration}
          onChange={handleChange}
        />
        <br />
        <br />

        <button type="submit" className="btn btn-primary">
          Gem og kør regel
        </button>
      </form>

      {/* STATUS MESSAGES */}
      {status === "loading" && <p>Sender reglen til serveren...</p>}
      {status === "success" && (
        <p className="badge blue" style={{ marginTop: 12 }}>
          {message}
        </p>
      )}
      {status === "error" && (
        <p className="badge red" style={{ marginTop: 12 }}>
          {message}
        </p>
      )}

      <hr style={{ margin: "24px 0" }} />

      {/* LIST OF RULES */}
      <h3>Gemte regler (kun mens serveren kører)</h3>
      {rules.length === 0 && <p>Der er ingen regler endnu.</p>}

      {rules.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Navn</th>
              <th>Frem (°C)</th>
              <th>Retur (°C)</th>
              <th>Flow</th>
              <th>Varighed (timer)</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {rules.map((rule) => (
              <tr key={rule.name}>
                <td>{rule.name}</td>
                <td>{rule.thresholdTempIn}</td>
                <td>{rule.thresholdTempOut}</td>
                <td>{rule.thresholdWaterFlow}</td>
                <td>{rule.duration}</td>
                <td>
                  <button type="button" onClick={() => handleEditClick(rule)}>
                    Rediger
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
