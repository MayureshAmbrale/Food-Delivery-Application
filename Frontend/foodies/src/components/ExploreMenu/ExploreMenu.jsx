import React, { useRef } from 'react'
import { category } from '../../assets/assets'
import './ExploreMenu.css'

const ExploreMenu = ({categories,setCategories}) => {

    const menuRef = useRef(null);

    const scrollLeft =()=>
    {
        if(menuRef.current)
        {
            menuRef.current.scrollBy({left:-200,behavior:'smooth'});
        }
    };

    const scrollRight =()=>
    {
        if(menuRef.current)
        {
            menuRef.current.scrollBy({left:200,behavior:'smooth'});
        }
    };
  return (
    <div className="explore-menu position-relative">
        <h1 className="d-flex align-items-center justify-content-between">Explore Our Menu
            <div>
                <i className='bi bi-arrow-left-circle scroll-icon' onClick={scrollLeft}></i>
                <i className='bi bi-arrow-right-circle scroll-icon' onClick={scrollRight}></i>
            </div>
        </h1>
        <p>Explore list of dishes from top categories</p>
        <div className="d-flex justify-content-between gap-4 overflow-auto explore-menu-list" ref={menuRef}>
            {category.map((item,index)=>
            {
                return(
                    <div key={index} className='text-center explore-menu-list-item' onClick={()=>setCategories(prev => prev===item.category ? "All":item.category)}>
                        <img src={item.icon} alt="" className={item.category ===categories ? "rounded-circle active":"rounded-circle"} height={140} width={140} />      
                        <p className='mt-2 fw-bold'>{item.category}</p>                  
                    </div>
                )
            })}
        </div>
        <hr />
    </div>
  )
}

export default ExploreMenu
