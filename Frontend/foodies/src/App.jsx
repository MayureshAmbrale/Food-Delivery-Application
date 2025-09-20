import React from 'react'
import Menubar from './components/Menubar/Menubar'
import { Routes,Route } from 'react-router-dom';
import ContactUs from './pages/Contact/Contact';
import Home from './pages/Home/Home';
import Explore from './pages/Explore/Explore';
import FoodDetails from './pages/FoodDetails/FoodDetails';
import Cart from './components/Cart/Cart';
import PlaceOrder from './components/PlaceOrder/PlaceOrder';
import Login from './Login/Login';
import Register from './components/Register/Register';
import MyOrders from './pages/MyOrders/MyOrders';
import { useContext } from 'react';
import StoreContext from './context/StoreContext/StoreContext';


const App = () => {
  const {token} = useContext(StoreContext);
  return (
    <div>
      <Menubar/>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/contact" element={<ContactUs/>} />
        <Route path="/explore" element={<Explore/>} />
        <Route path="/food/:id" element={<FoodDetails/>} />
        <Route path="/cart" element={<Cart/>} />
        <Route path="/order" element={token ? <PlaceOrder/> : <Login/>} />
        <Route path="/login" element={token ? <Home/> : <Login/>} />
        <Route path="/register" element={token ? <Home/> : <Register/>} />
        <Route path="/myorder" element={token ? <MyOrders/> : <Login/>} />
      </Routes>
    </div>
  )
}

export default App
