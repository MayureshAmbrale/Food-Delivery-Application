import React from 'react'
import Explore from '../../pages/Explore/Explore';
import "./Header.css"
import { Link } from 'react-router-dom';
const Header = () => {
  return (
    <div className="p-5 bg-light rounded-3  mx-5 header">
        <div className="container-fluid py-5">
            <h1 className="display-5 fw-bold">Order your favourate food here </h1>
            <p className='col-md-8 fs-4'>Discover the best food in India</p>
            <Link to="/explore" className='btn btn-primary '>Explore</Link>
        </div>
    </div>
  )
}

export default Header
