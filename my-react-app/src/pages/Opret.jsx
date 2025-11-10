import { useMemo, useState } from "react";
import Menu from "../components/Menu.jsx";
import Header from "../components/Header.jsx";

export default function Opret() {
  // step: 'editor' | 'test' | 'active'
  const [step, setStep] = useState("editor");

  // very light “results” for the test step
  const [testResult, setTestResult] = useState({
    total: 0,
    byDept: { name: "Afdeling", count: 0 },
    byDay: [
      { label: "5/10", pct: 10 },
      { label: "6/10", pct: 10 },
    ],
  });

  function onTestClick() {
    // fake some numbers so you can see UI changing
    const rand = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;
    const total = rand(3, 18);
    const count = rand(1, total);
    const byDay = [
      { label: "5/10", pct: rand(5, 90) },
      { label: "6/10", pct: rand(5, 90) },
    ];
    setTestResult({ total, byDept: { name: "Afdeling A", count }, byDay });
    setStep("test");
  }

  function onBackToEdit() {
    setStep("editor");
  }
  function onActivate() {
    setStep("active");
  }
  function onEditAgain() {
    setStep("editor");
  }
  function onSuspend() {
    // up to you: go back to edit state
    setStep("editor");
  }

  // Small helpers for class toggling
  const cls = (...parts) => parts.filter(Boolean).join(" ");
  const hiddenIf = (cond) => (cond ? "hidden" : "");

  return (
    <>
      {/* Top header bar */}
      <Header />

      <div className="layout">
        {/* Left menu */}
        <aside>
          <Menu />
        </aside>

        {/* Main content */}
        <main className="content">
          {/* STEP 1: editor */}
          <section id="editor" className={cls("card", hiddenIf(step !== "editor"))}>
            <div className="row" style={{ justifyContent: "space-between" }}>
              <h2>Ny Regel</h2>
              <span className="badge blue" id="statusBadge">
                Udkast
              </span>
            </div>

            <h3 className="muted" style={{ marginTop: 12 }}>Regel Konfiguration</h3>

            <div className="grid-2" style={{ marginTop: 12 }}>
              <div className="field">
                <label>
                  <strong>VarmelnInputIn (Fremløbstemperatur)</strong> — Min (°C)
                </label>
                <input id="inMin" type="number" placeholder="Min" />
              </div>
              <div className="field">
                <label>Max (°C)</label>
                <input id="inMax" type="number" placeholder="Max" />
              </div>

              <div className="field">
                <label>
                  <strong>VarmelnInputOut (Returtemperatur)</strong> — Min (°C)
                </label>
                <input id="outMin" type="number" placeholder="Min" />
              </div>
              <div className="field">
                <label>Max (°C)</label>
                <input id="outMax" type="number" placeholder="Max" />
              </div>

              <div className="field">
                <label>
                  <strong>FlowInput (Volumen)</strong> — Min (m³)
                </label>
                <input id="flowMin" type="number" placeholder="Min" />
              </div>
              <div className="field">
                <label>Max (m³)</label>
                <input id="flowMax" type="number" placeholder="Max" />
              </div>
            </div>

            <div className="grid-2" style={{ marginTop: 12 }}>
              <div className="field">
                <label>
                  <strong>Periode</strong>
                </label>
                <div className="row">
                  <label className="row" style={{ gap: 6 }}>
                    <input id="season" type="checkbox" /> Sæson
                  </label>
                  <input id="dateFrom" type="date" />
                  <span>–</span>
                  <input id="dateTo" type="date" />
                </div>
              </div>
              <div />
            </div>

            <div className="grid-2" style={{ marginTop: 12 }}>
              <div className="field">
                <label>
                  <strong>Afdeling</strong>
                </label>
                <select id="dept" defaultValue="">
                  <option value="">Vælg afdeling</option>
                  <option>Afdeling A</option>
                  <option>Afdeling B</option>
                </select>
              </div>
              <div className="field">
                <label>
                  <strong>Lejlighed (valgfrit)</strong>
                </label>
                <select id="apt" defaultValue="">
                  <option value="">Ingen (alle lejligheder)</option>
                  <option>1. tv</option>
                  <option>2. th</option>
                </select>
              </div>
            </div>

            <div className="field" style={{ marginTop: 12 }}>
              <label>
                <strong>Beskrivelse</strong>
              </label>
              <textarea id="desc" placeholder="Indtast beskrivelse..." rows={4}></textarea>
            </div>

            <div className="actions">
              <div />
              <button className="btn btn-primary" id="btnTest" onClick={onTestClick}>
                Test Regel →
              </button>
            </div>
          </section>

          {/* STEP 2: test results */}
          <section id="test" className={cls("card", hiddenIf(step !== "test"))}>
            <div className="row" style={{ justifyContent: "space-between" }}>
              <h2>Ny Regel</h2>
              <span className="badge blue">Testet</span>
            </div>

            <div
              className="row"
              style={{ justifyContent: "space-between", alignItems: "center", marginTop: 12 }}
            >
              <p>
                Baseret på de konfigurerede grænseværdier ville denne regel have udløst{" "}
                <strong id="notifTotal">{testResult.total}</strong> notifikationer.
              </p>
              <span className="badge" id="notifBadge">
                {testResult.total} notifikationer
              </span>
            </div>

            <div className="grid-2" style={{ marginTop: 12 }}>
              <div className="barcard">
                <p className="muted">📈 Notifikationer per Dag</p>
                <div className="bars">
                  <div className="bar">
                    <div id="barDay1" style={{ height: `${testResult.byDay[0].pct}%` }} />
                    <div id="barDay2" style={{ height: `${testResult.byDay[1].pct}%` }} />
                  </div>
                  <div className="bar-labels">
                    <span id="labelDay1">{testResult.byDay[0].label}</span>
                    <span id="labelDay2">{testResult.byDay[1].label}</span>
                  </div>
                </div>
              </div>

              <div className="barcard">
                <p className="muted">📈 Notifikationer per Afdeling</p>
                <div className="bars">
                  <div className="bar">
                    <div id="barDept" style={{ width: "100%", height: "10%" }} />
                  </div>
                  <div className="bar-labels">
                    <span id="labelDept">{testResult.byDept.name}</span>
                    <span id="labelDeptCount">{testResult.byDept.count}</span>
                  </div>
                </div>
              </div>
            </div>

            <div className="list" style={{ marginTop: 16 }}>
              <div className="item">
                <div className="row" style={{ justifyContent: "space-between" }}>
                  <strong>Målernummer: 82721892</strong>
                  <span className="badge">Afdeling A</span>
                </div>
                <div className="muted">6.10.2025, 23.01.00</div>
                <div className="grid-2" style={{ marginTop: 8 }}>
                  <div>
                    <strong>Fremløbstemperatur</strong>
                    <div className="muted">Værdi: 69.5 | Grænse: 50.0 | Over grænse</div>
                  </div>
                  <div>
                    <strong>Returtemperatur</strong>
                    <div className="muted">Værdi: 41.3 | Grænse: 30.0 | Over grænse</div>
                  </div>
                </div>
              </div>
            </div>

            <div className="actions">
              <button className="btn" id="btnBack" onClick={onBackToEdit}>
                ← Tilbage til Redigering
              </button>
              <button
                className="btn"
                style={{ background: "#16a34a", color: "white", borderColor: "#16a34a" }}
                id="btnActivate"
                onClick={onActivate}
              >
                Aktiver Regel →
              </button>
            </div>
          </section>

          {/* STEP 3: active view */}
          <section id="active" className={cls("card", hiddenIf(step !== "active"))}>
            <div className="alert-success">
              <div>
                <strong>Regel Aktiv</strong>
                <br />
                Denne regel er aktiv og sender notifikationer når grænseværdierne overskrides.
              </div>
              <span className="badge">Aktiv</span>
            </div>

            <h3 className="muted" style={{ marginTop: 12 }}>
              Regel Konfiguration
            </h3>

            <div className="grid-3" style={{ marginTop: 12 }}>
              <div className="field">
                <label>VarmelnInputIn</label>
                <input id="sumIn" disabled />
              </div>
              <div className="field">
                <label>VarmelnInputOut</label>
                <input id="sumOut" disabled />
              </div>
              <div className="field">
                <label>FlowInput</label>
                <input id="sumFlow" disabled />
              </div>
            </div>

            <div className="field" style={{ marginTop: 12 }}>
              <label>Lokation</label>
              <input id="sumLoc" disabled />
            </div>

            <div className="field" style={{ marginTop: 12 }}>
              <label>Beskrivelse</label>
              <input id="sumDesc" disabled />
            </div>

            <div className="field" style={{ marginTop: 12 }}>
              <label>Test Statistik</label>
              <div className="muted">
                Ville have udløst <strong id="sumNotif">{testResult.total}</strong> notifikationer baseret på test data
              </div>
            </div>

            <div className="actions">
              <button className="btn" id="btnEdit" onClick={onEditAgain}>
                Rediger Regel
              </button>
              <button className="btn btn-danger" id="btnSuspend" onClick={onSuspend}>
                Suspender Regel
              </button>
            </div>
          </section>
        </main>
      </div>
    </>
  );
}
