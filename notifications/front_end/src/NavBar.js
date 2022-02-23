import {Link} from 'react-router-dom'

const NavBar = () => {
    return (
      <nav-bar className="nav">
        <ul>
          <li>
            <Link to="App">Reports</Link>
          </li>
          <li>
            <Link to="Notifications">Notifications</Link>
          </li>
          <li>
            <Link to="Profiles">Profiles</Link>
          </li>
          <li>
          <Link to="Admin">Admin</Link>
          </li>
     
        <button id="btn-signout">Signout</button>   </ul>
      </nav-bar>
    );
  };

  export default NavBar;