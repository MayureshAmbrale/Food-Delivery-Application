import axios from "axios";
import React, { createContext, useEffect, useState } from "react";
import { fetchFoodList } from "../../service/foodService";

export const StoreContext = createContext(null);

export const StoreContextProvider = (prop) => {
  const [foodList, setFoodList] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [token, setToken] = useState("");

  const increaseQnt = async (foodId) => {
    setQuantities((prev) => ({ ...prev, [foodId]: (prev[foodId] || 0) + 1 }));
    await axios.post(
      "http://localhost:8080/api/cart",
      { foodId },
      { headers: { Authorization: `Bearer ${token}` } }
    );
  };

  const loadCartData = async (token) => {
    const response = await axios.get("http://localhost:8080/api/cart", {
      headers: { Authorization: `Bearer ${token}` },
    });
    setQuantities(response.data.items);
  };

  const decreaseQnt = async (foodId) => {
    setQuantities((prev) => ({
      ...prev,
      [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0,
    }));
    await axios.post(
      "http://localhost:8080/api/cart/remove",
      { foodId },
      { headers: { Authorization: `Bearer ${token}` } }
    );
  };

  const removeFromCart = (foodId) => {
    setQuantities((prev) => ({ ...prev, [foodId]: 0 }));
  };

  useEffect(() => {
    async function loadData() {
      const data = await fetchFoodList();
      setFoodList(data);
      if (localStorage.getItem("token")) {
        setToken(localStorage.getItem("token"));
        await loadCartData(localStorage.getItem("token"))
      }
    }
    loadData();
  }, []);
  const contextValue = {
    foodList,
    increaseQnt,
    decreaseQnt,
    quantities,
    removeFromCart,
    token,
    setToken,
    setQuantities,
    loadCartData
  };
  return (
    <StoreContext.Provider value={contextValue}>
      {prop.children}
    </StoreContext.Provider>
  );
};

export default StoreContext;
