import {Link} from 'react-router-dom'

const NavBar = () => {
    return (
      <nav-bar className="nav">
        <ul>
          <li><Link to="App">Reports</Link></li>
          <li><Link to="Notifications">Notifications</Link></li>
          <li><Link to="Profiles">Profiles</Link></li>
          <li><Link to="Admin">Admin</Link></li>
          <li id="btn-signout"><Link to="Login">Signout</Link></li>   
        </ul>
      </nav-bar>
    );
  };

  export default NavBar;