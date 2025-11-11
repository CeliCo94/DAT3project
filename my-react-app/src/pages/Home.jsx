export default function Home() {
  return (
      <section className="card">
        <h2>Aktive notifikationer</h2>

        <h3 className="section-heading">Service Center 1</h3>
        <div className="table-wrap">
          <table id="sc1-table" className="data-table">
            <thead>
              <tr>
                <th>Notifikation</th>
                <th>Regel</th>
                <th>Vigtighed</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr><td></td><td></td><td></td><td></td></tr>
              <tr><td></td><td></td><td></td><td></td></tr>
              <tr><td></td><td></td><td></td><td></td></tr>
            </tbody>
          </table>
        </div>

        <h3 className="section-heading">Service Center 2</h3>
        <div className="table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>Notifikation</th>
                <th>Regel</th>
                <th>Vigtighed</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr><td></td><td></td><td></td><td></td></tr>
              <tr><td></td><td></td><td></td><td></td></tr>
              <tr><td></td><td></td><td></td><td></td></tr>
            </tbody>
          </table>
        </div>
      </section>
  )
}