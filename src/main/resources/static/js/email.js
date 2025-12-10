document.addEventListener("DOMContentLoaded", () => {
  const loadingEl = document.getElementById("email-status-loading");
  const errorEl = document.getElementById("email-status-error");
  const emptyEl = document.getElementById("email-status-empty");
  const notifBox = document.getElementById("email-notification");

  const addressEl = document.getElementById("notif-address");
  const causeEl = document.getElementById("notif-cause");
  const ruleEl = document.getElementById("notif-rule");
  const critEl = document.getElementById("notif-criticality");
  const timeEl = document.getElementById("notif-timestamp");
  const activeEl = document.getElementById("notif-active");
  const sentEl = document.getElementById("notif-sent");

  const sendBtn = document.getElementById("email-send-button");
  const sendErrorEl = document.getElementById("email-send-error");
  const sendSuccessEl = document.getElementById("email-send-success");

  let currentNotification = null;
  let sendStatus = "idle";

  function showOnlyLoading() {
    loadingEl.style.display = "";
    errorEl.style.display = "none";
    emptyEl.style.display = "none";
    notifBox.style.display = "none";
  }

  function showError(msg) {
    loadingEl.style.display = "none";
    errorEl.textContent = msg;
    errorEl.style.display = "";
    emptyEl.style.display = "none";
    notifBox.style.display = "none";
  }

  function showEmpty() {
    loadingEl.style.display = "none";
    errorEl.style.display = "none";
    emptyEl.style.display = "";
    notifBox.style.display = "none";
  }

  function showNotification(n) {
    loadingEl.style.display = "none";
    errorEl.style.display = "none";
    emptyEl.style.display = "none";
    notifBox.style.display = "";

    addressEl.textContent = n.address || "";
    causeEl.textContent = n.cause || "";
    ruleEl.textContent = n.rule || "";
    critEl.textContent = n.criticality || "";
    timeEl.textContent = n.timeStamp || "";
    activeEl.textContent = n.active ? "Ja" : "Nej";
    sentEl.textContent = n.sent ? "Ja" : "Nej";

    updateSendButton(n);
  }

  function updateSendButton(n) {
    if (!sendBtn) return;

    const disabled =
      sendStatus === "loading" || n.sent || !n.active;

    sendBtn.disabled = disabled;

    if (n.sent) {
      sendBtn.textContent = "E-mail er allerede sendt";
    } else if (sendStatus === "loading") {
      sendBtn.textContent = "Sender e-mail...";
    } else {
      sendBtn.textContent = "Send e-mail";
    }
  }

  async function fetchNotification() {
    try {
      showOnlyLoading();
      sendErrorEl.style.display = "none";
      sendSuccessEl.style.display = "none";

      const res = await fetch("api/notifications/fetch");

      if (!res.ok) {
        throw new Error("Server-fejl");
      }

      let data = await res.json();

      // Hvis backend sender en liste: tag første element
      if (Array.isArray(data)) {
        data = data.length > 0 ? data[0] : null;
      }

      currentNotification = data;

      if (!data) {
        showEmpty();
      } else {
        showNotification(data);
      }
    } catch (err) {
      console.error(err);
      showError("Kunne ikke hente notifikation.");
    }
  }

  async function handleSendEmail() {
    if (!currentNotification) {
      return;
    }

    try {
      sendStatus = "loading";
      updateSendButton(currentNotification);
      sendErrorEl.style.display = "none";
      sendSuccessEl.style.display = "none";

      const res = await fetch("api/notifications/send-email", {
        method: "POST",
      });

      if (!res.ok) {
        throw new Error("Kunne ikke sende e-mail");
      }

      sendStatus = "success";
      currentNotification.sent = true;
      sentEl.textContent = "Ja";
      updateSendButton(currentNotification);
      sendSuccessEl.style.display = "";
    } catch (err) {
      console.error(err);
      sendStatus = "error";
      updateSendButton(currentNotification);
      sendErrorEl.style.display = "";
    }
  }

  if (sendBtn) {
    sendBtn.addEventListener("click", handleSendEmail);
  }

  fetchNotification();
});
