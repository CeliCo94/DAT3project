import React, { useEffect, useState } from "react";
import { loadCsv, groupBy, splitActiveAndHistory, by } from "../lib/loadCsv";

const severityDot = (sev) => {
  const s = (sev || "").toLowerCase();
  const color =
    s.startsWith("høj") ? "#d62020" : s.startsWith("mellem") ? "#f1b300" : "#2e8b57";
  return (
    <span
      aria-label={sev}
      title={sev}
      style={{
        display: "inline-block",
        width: 14,
        height: 14,
        borderRadius: "50%",
        background: color,
        verticalAlign: "middle",
      }}
    />
  );
};

function Section({ title, children, defaultOpen = true }) {
  const [open, setOpen] = useState(defaultOpen);
  return (
    <section className="card" style={{ marginBottom: 16 }}>
      <button
        className="accordion-trigger"
        onClick={() => setOpen((o) => !o)}
        aria-expanded={open}
      >
        <span>{open ? "V" : ">"}</span>
        <h2 style={{ margin: 0 }}>{title}</h2>
      </button>
      {open && <div style={{ marginTop: 12 }}>{children}</div>}
    </section>
  );
}

function ActiveTable({ rows }) {
  return (
    <div className="table-wrap">
      <table className="data-table">
        <thead>
          <tr>
            <th>Service Center</th>
            <th>Notifikation</th>
            <th>Regel</th>
            <th>Vigtighed</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((r, i) => (
            <tr key={i}>
              <td>{r.service_center}</td>
              <td>{r.notification}</td>
              <td>{r.rule}</td>
              <td>
                {r.severity} &nbsp; {severityDot(r.severity)}
              </td>
              <td>{r.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default function Home() {
  const [rows, setRows] = useState([]);
  useEffect(() => {
    fetch('http://localhost:8080/notifications/fetch')
    .then((r) => r.json())
    .then(setRows)
    .catch(console.error);
  }, []);


  const { active, history } = splitActiveAndHistory(rows);

  const activeRows = [...active].sort(
    by((r) => `${r.service_center} ${r.notification}`)
  );

  // group history by date
  const historyByDate = groupBy(history, (r) => r.date);
  const dates = Object.keys(historyByDate).sort(by((d) => d));

  return (
      <section className="card">
      <Section title="Aktive notifikationer" defaultOpen>
        <ActiveTable rows={activeRows} />
      </Section>

      <Section title="Historik på notifikationer" defaultOpen={false}>
        {dates.map((d) => (
          <div key={d} style={{ marginBottom: 20 }}>
            <h3 className="section-heading">{d}</h3>
            <div className="table-wrap">
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Service Center</th>
                    <th>Notifikation</th>
                    <th>Regel</th>
                    <th>Vigtighed</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {historyByDate[d].map((r, i) => (
                    <tr key={i}>
                      <td>{r.service_center}</td>
                      <td>{r.notification}</td>
                      <td>{r.rule}</td>
                      <td>
                        {r.severity} &nbsp; {severityDot(r.severity)}
                      </td>
                      <td>{r.status}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        ))}
      </Section>
    </section>
);
}