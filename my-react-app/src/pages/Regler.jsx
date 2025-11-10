import Menu from "../components/Menu.jsx";
import Header from "../components/Header.jsx";

export default function Regler() {
  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: read values with e.target.elements or convert to controlled inputs
    // const { name, thresholdTempIn, thresholdTempOut, thresholdWaterFlow, duration } = e.target.elements;
  };

  return (
    <>
      <div className="layout">
        <aside><Menu /></aside>

        <main className="content">
          <section className="card">
            <h2>Regler</h2>

            <form id="ruleForm" onSubmit={handleSubmit}>
              <label htmlFor="name"><strong>Navngiv reglen</strong>:</label><br />
              <input type="text" id="name" name="name" /><br /><br />

              <label htmlFor="thresholdTempIn">Grænseværdi for fremløbstemperatur:</label><br />
              <input type="number" id="thresholdTempIn" name="thresholdTempIn" /><br /><br />

              <label htmlFor="thresholdTempOut">Grænseværdi for returløbstemperatur:</label><br />
              <input type="number" id="thresholdTempOut" name="thresholdTempOut" /><br /><br />

              <label htmlFor="thresholdWaterFlow">Grænseværdi for flow:</label><br />
              <input type="number" id="thresholdWaterFlow" name="thresholdWaterFlow" /><br /><br />

              <label htmlFor="duration">Grænseværdi for varighed (timer):</label><br />
              <input type="number" id="duration" name="duration" /><br /><br />

              <button type="submit" className="btn btn-primary">Send</button>
            </form>
          </section>
        </main>
      </div>
    </>
  );
}