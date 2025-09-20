import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap-icons/font/bootstrap-icons.css";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import { BrowserRouter } from "react-router-dom";
import { StoreContextProvider } from "./context/StoreContext/StoreContext.jsx";
import { ToastContainer } from 'react-toastify';


createRoot(document.getElementById("root")).render(
  <BrowserRouter>

    <StoreContextProvider>
      <App />
      <ToastContainer/>
    </StoreContextProvider>
  </BrowserRouter>
);

