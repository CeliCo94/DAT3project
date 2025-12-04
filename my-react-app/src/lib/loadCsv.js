import Papa from "papaparse";

export async function loadCsv(path) {
  const res = await fetch(path);
  const text = await res.text();
  const parsed = Papa.parse(text, { header: true, skipEmptyLines: true });
  return parsed.data;
}

// helpers for grouping + simple splits
export const by = (fn) => (a, b) => (fn(a) > fn(b) ? 1 : fn(a) < fn(b) ? -1 : 0);

export function groupBy(list, keyFn) {
  return list.reduce((acc, item) => {
    const k = keyFn(item);
    (acc[k] ||= []).push(item);
    return acc;
  }, {});
}

// decide “active” vs “history” based on status text
export function splitActiveAndHistory(rows) {
  const active = [];
  const history = [];
  for (const r of rows) {
    const s = (r.status || "").toLowerCase();
    if (s.startsWith("afsendt") || s.startsWith("åben") || s.startsWith("aktiv")) {
      active.push(r);
    } else {
      history.push(r);
    }
  }
  return { active, history };
}
