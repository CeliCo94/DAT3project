document.addEventListener("DOMContentLoaded", () => {
  const targets = document.querySelectorAll("[data-include]");
  targets.forEach(async el => {
    const url = el.getAttribute("data-include");
    try {
      const res = await fetch(url, { credentials: "same-origin" });
      if (!res.ok) throw new Error(res.status + " " + res.statusText);
      el.innerHTML = await res.text();
      el.removeAttribute("data-include");
    } catch (err) {
      console.error("Menu include failed:", err);
      el.innerHTML = "<!-- menu kunne ikke indlæses -->";
    }
  });
});
