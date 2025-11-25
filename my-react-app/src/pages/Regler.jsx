import { useEffect, useState } from "react";

export default function Regler() {
  const [form, setForm] = useState({
    name: "",
    description: "",
    criticality: "Low",
    seasonal: false,
    startDate: "",
    endDate: "",
    thresholdTempIn: "",
    thresholdTempOut: "",
    thresholdWaterFlow: "",
    duration: "",
  });

  const [rules, setRules] = useState([]);
  const [status, setStatus] = useState("idle");
  const [message, setMessage] = useState("");

  // Load existing rules
  useEffect(() => {
    async function fetchRules() {
      try {
        const res = await fetch("/api/regler");
        if (!res.ok) return;
        const data = await res.json();
        setRules(data);
      } catch (err) {
        console.error(err);
      }
    }
    fetchRules();
  }, []);

  // Handle changes in all inputs (text, number, checkbox, select)
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      name: form.name,
      description: form.description,
      criticality: form.criticality, // "High" | "Medium" | "Low"
      seasonal: form.seasonal,
      startDate: form.startDate || null,
      endDate: form.endDate || null,
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

      // If you keep using name as identifier:
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

  const handleEditClick = (rule) => {
    setForm({
      name: rule.name ?? "",
      description: rule.description ?? "",
      criticality: rule.criticality ?? "Low",
      seasonal: rule.seasonal ?? false,
      startDate: rule.startDate ?? "",
      endDate: rule.endDate ?? "",
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

        <label htmlFor="description">
          Beskrivelse / hvad tror du problemet er?
        </label>
        <br />
        <input
          type="text"
          id="description"
          name="description"
          placeholder="f.eks. Manglende isolering på rør"
          value={form.description}
          onChange={handleChange}
        />
        <br />
        <br />

        <label htmlFor="criticality">Kritikalitet</label>
        <br />
        <select
          id="criticality"
          name="criticality"
          value={form.criticality}
          onChange={handleChange}
        >
          <option value="High">Høj</option>
          <option value="Medium">Middel</option>
          <option value="Low">Lav</option>
        </select>
        <br />
        <br />

        <fieldset style={{ border: "none", padding: 0, margin: 0 }}>
          <legend>Periode</legend>
          <label>
            <input
              type="checkbox"
              name="seasonal"
              checked={form.seasonal}
              onChange={handleChange}
            />{" "}
            Sæson
          </label>
          <br />
          {/* season dates */}
          <input
            type="date"
            name="startDate"
            value={form.startDate}
            onChange={handleChange}
            disabled={!form.seasonal}
          />{" "}
          -{" "}
          <input
            type="date"
            name="endDate"
            value={form.endDate}
            onChange={handleChange}
            disabled={!form.seasonal}
          />
        </fieldset>

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

        <label htmlFor="thresholdWaterFlow">Grænseværdi for flow:</label>
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

        <label htmlFor="duration">Grænseværdi for varighed (timer):</label>
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

      <h3>Gemte regler (mens serveren kører)</h3>
      {rules.length === 0 && <p>Der er ingen regler endnu.</p>}

      {rules.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Navn</th>
              <th>Kritikalitet</th>
              <th>Sæson</th>
              <th>Periode</th>
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
                <td>{rule.criticality}</td>
                <td>{rule.seasonal ? "Ja" : "Nej"}</td>
                <td>
                  {rule.startDate && rule.endDate
                    ? `${rule.startDate} - ${rule.endDate}`
                    : "-"}
                </td>
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