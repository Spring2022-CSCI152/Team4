import React from "react";

export default () => {
  return (
    <section>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Amount Due</th>
            <th>Date</th>
            <th>Insurance Co</th>
            <th>Is Copay?</th>
            <th>Status</th>
            <th>Is Minor?</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Adam Rackis</td>
            <td>$100</td>
            <td>3/22/2021</td>
            <td>Blue Cross Blue Shield</td>
            <td>YES</td>
            <td>APPROVED</td>
            <td>NO</td>
          </tr>
          <tr>
            <td>John Doe</td>
            <td>$300</td>
            <td>6/30/2021</td>
            <td>Aetna</td>
            <td>NO</td>
            <td>MORE INFO NEEDED</td>
            <td>YES</td>
          </tr>
          <tr>
            <td>Jane Doe</td>
            <td>$200</td>
            <td>5/20/2021</td>
            <td>Blue Cross Blue Shielf</td>
            <td>YES</td>
            <td>REJECTED</td>
            <td>NO</td>
          </tr>
        </tbody>
      </table>
    </section>
  );
};
