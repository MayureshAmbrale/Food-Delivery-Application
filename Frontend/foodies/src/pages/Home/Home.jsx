import React, { useState } from 'react'
import Header from '../../components/Header/Header'
import ExploreMenu from './../../components/ExploreMenu/ExploreMenu';
import FoodDisplay from './../../components/FoodDisplay/FoodDisplay';

const Home = () => {

  const [categories,setCategories] = useState('All');
  return (
    <div>
      <Header />
      <ExploreMenu categories={categories} setCategories={setCategories} />
      <FoodDisplay categories={categories} searchText={''}/>
    </div>
  )
}

export default Home
