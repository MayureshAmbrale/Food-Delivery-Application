import React, { useContext } from 'react';
import StoreContext from '../../context/StoreContext/StoreContext';
import FoodItem from '../FoodItem/FoodItem';

const FoodDisplay = ({categories,searchText}) => {
    const { foodList } = useContext(StoreContext);

    const filteredFood = foodList.filter(food =>
        ( (categories==='All' || food.category === categories) &&
         food.name.toLowerCase().includes(searchText.toLowerCase()))
        
        );

    return (
        <div className="container">
            <div className="row">
                {filteredFood.length > 0 ? (
                   filteredFood.map((food,index)=>
                (
                    <FoodItem key={index} 
                    name={food.name} 
                    description={food.description}
                    imageUrl={food.imageUrl}
                    price={food.price}
                    id={food.id}/>
                   ))
                ) : (
                    <div className="text-center mt-4">
                        
                        <h4>No Food Found</h4>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FoodDisplay;
