import { useEffect, useState } from "react";

export default function Email() {
  const [notification, setNotification] = useState(null);
  const [status, setStatus] = useState("loading");
  const [error, setError] = useState("");
  const [sendStatus, setSendStatus] = useState("idle"); // for email sending

  useEffect(() => {
    async function fetchNotification() {
      try {
        setStatus("loading");
        setError("");

        const res = await fetch("/notifications/fetch");

        if (!res.ok) {
          throw new Error("Server-fejl");
        }

        const data = await res.json(); // can be null
        setNotification(data);
        setStatus("success");
      } catch (err) {
        console.error(err);
        setError("Kunne ikke hente notifikation.");
        setStatus("error");
      }
    }

    fetchNotification();
  }, []);

  const handleSendEmail = async () => {
    try {
      setSendStatus("loading");
      const res = await fetch("/notifications/send-email", {
        method: "POST",
      });
      if (!res.ok) {
        throw new Error("Kunne ikke sende e-mail");
      }
      setSendStatus("success");
      // update local state so button disables
      setNotification((prev) =>
        prev ? { ...prev, sent: true } : prev
      );
    } catch (err) {
      console.error(err);
      setSendStatus("error");
    }
  };

  return (
    <section className="card">
      <h2>E-mails / Notifikation</h2>

      {status === "loading" && <p>Henter notifikation...</p>}

      {status === "error" && (
        <p className="badge red" style={{ marginTop: 12 }}>
          {error}
        </p>
      )}

      {status === "success" && !notification && (
        <p>Der er ingen aktiv notifikation.</p>
      )}

      {status === "success" && notification && (
        <div style={{ marginTop: 16 }}>
          <p>
            <strong>Adresse:</strong> {notification.address}
          </p>
          <p>
            <strong>Årsag:</strong> {notification.cause}
          </p>
          <p>
            <strong>Regel:</strong> {notification.rule}
          </p>
          <p>
            <strong>Kritikalitet:</strong> {notification.criticality}
          </p>
          <p>
            <strong>Tidspunkt:</strong> {notification.timeStamp}
          </p>
          <p>
            <strong>Aktiv:</strong> {notification.active ? "Ja" : "Nej"}
          </p>
          <p>
            <strong>Sendt som e-mail:</strong>{" "}
            {notification.sent ? "Ja" : "Nej"}
          </p>

          <br />

          <button
            type="button"
            onClick={handleSendEmail}
            disabled={
              sendStatus === "loading" ||
              notification.sent ||
              !notification.active
            }
          >
            {notification.sent
              ? "E-mail er allerede sendt"
              : sendStatus === "loading"
              ? "Sender e-mail..."
              : "Send e-mail"}
          </button>

          {sendStatus === "error" && (
            <p className="badge red" style={{ marginTop: 8 }}>
              Kunne ikke sende e-mail.
            </p>
          )}
          {sendStatus === "success" && (
            <p className="badge blue" style={{ marginTop: 8 }}>
              E-mail er sendt!
            </p>
          )}
        </div>
      )}
    </section>
  );
}