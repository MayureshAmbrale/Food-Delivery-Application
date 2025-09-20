import "./Menubar.css";
import { assets, category } from "../../assets/assets";
import { Link, useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import StoreContext from "../../context/StoreContext/StoreContext";

const Menubar = () => {
  const { quantities,token, setToken , setQuantities} = useContext(StoreContext);
  const uniqueItems = Object.values(quantities).filter((qty) => qty > 0).length;
  const navigate = useNavigate();
  const [active, setActive] = useState("home");

    const logout =()=>
    {
        localStorage.removeItem("token");
        setToken("");
        navigate("/");
        setQuantities({})
    }


  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <Link to="/">
          {" "}
          <img
            src={assets.logo}
            alt=""
            className="mx-4"
            height={40}
            width={40}
          />
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link
                className={
                  active === "home" ? "nav-link fw-bold active" : "nav-link"
                }
                to="/"
                onClick={() => setActive("home")}
              >
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link
                className={
                  active === "explore" ? "nav-link fw-bold active" : "nav-link"
                }
                to="/explore"
                onClick={() => setActive("explore")}
              >
                Explore
              </Link>
            </li>

            <li className="nav-item">
              <Link
                className={
                  active === "contact-us"
                    ? "nav-link fw-bold active"
                    : "nav-link"
                }
                to="/contact"
                onClick={() => setActive("contact-us")}
              >
                Contact Us
              </Link>
            </li>
          </ul>
          <div className="d-flex align-items-center gap-4">
            <Link to={"/cart"}>
              <div className="position-relative mx-2">
                <img src={assets.cart} alt="" height={40} width={40} />
                <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning">
                  {uniqueItems}
                </span>
              </div>
            </Link>
          </div>
          {
            !token ? <>
                <button className="btn btn-outline-primary mx-1">
            <Link to={"/login"} style={{ textDecoration: "none" }}>
              Login
            </Link>
          </button>
          <button className="btn btn-outline-success">
            <Link to={"/register"} style={{ textDecoration: "none" }}>
              Register
            </Link>
          </button>
            </>:
            (<div className="dropdown text-end">
                <a href="" className="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                <img src={assets.profile} alt="" width={40} height={40} className="rounded-circle" />
                    
                </a>
                <ul className="dropdown-menu text-small">
                    <li className="dropdown-item" onClick={()=>navigate("/myorder")}>
                        Orders
                    </li>
                    <li className="dropdown-item" onClick={logout}>Logout</li>
                </ul>
            </div>)
          }
        </div>
      </div>
    </nav>
  );
};
export default Menubar;
