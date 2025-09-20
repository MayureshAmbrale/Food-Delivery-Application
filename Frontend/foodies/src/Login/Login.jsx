import React, { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
import axios from "axios";
import { toast } from "react-toastify";
import StoreContext from "../context/StoreContext/StoreContext";
import { loginUser } from "../service/authService";

const Login = () => {
  const {setToken,loadCartData} = useContext(StoreContext);
  const [data, setData] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const onChangehandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    setData((data) => ({ ...data, [name]: value }));
  };
  const onSubmitHandler = async(event) => {
    event.preventDefault();
    try {
      const response = await loginUser(data);
      if(response.status === 200)
      {
        toast.success("Successfull login");
        setToken(response.data.token);
        localStorage.setItem("token",response.data.token);
        navigate("/");
        loadCartData(response.data.token)
      }
      else{
        toast.error("Error in login, try again");
      }
    } catch (error) {
      toast.error("Error in login, try again");
    }
  };
  return (
    <div className="login-container">
      <div className="row">
        <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
          <div className="card border-0 shadow rounded-3 my-5">
            <div className="card-body p-4 p-sm-5">
              <h5 className="card-title text-center mb-5 fw-light fs-5">
                Sign In
              </h5>
              <form onSubmit={onSubmitHandler}>
                <div className="form-floating mb-3">
                  <input
                    type="email"
                    className="form-control"
                    id="floatingInput"
                    placeholder="name@example.com"
                    name="email"
                    value={data.email}
                    onChange={onChangehandler}
                  />
                  <label htmlFor="floatingInput">Email address</label>
                </div>
                <div className="form-floating mb-3">
                  <input
                    type="password"
                    className="form-control"
                    id="floatingPassword"
                    placeholder="Password"
                    name="password"
                    value={data.password}
                    onChange={onChangehandler}
                  />
                  <label htmlFor="floatingPassword">Password</label>
                </div>

                <div className="d-grid">
                  <button
                    className="btn btn-outline-primary btn-login text-uppercase"
                    type="submit"
                  >
                    Sign in
                  </button>
                </div>
                <div className="d-grid">
                  <button
                    className="btn btn-outline-danger btn-login text-uppercase mt-1"
                    type="reset"
                  >
                    Reset
                  </button>
                </div>
                <div className="mt-4">
                  Don't have an account? <Link to={"/register"}>Sign up</Link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
