import axios from "axios";

const API = "http://localhost:8080/api/cart";

export const addToCart= async(foodId,token)=>
{
    try {
        await axios.post(
      API,
      { foodId },
      { headers: { Authorization: `Bearer ${token}` } }
    );
    } catch (error) {
        console.error("Error while adding to cart",error);
    }
}

export const removeQtyfromCart= async(foodId,token)=>
{
    try {
        await axios.post(
      API+"/remove",
      { foodId },
      { headers: { Authorization: `Bearer ${token}` } }
    );
    } catch (error) {
         console.error("Error while removing from cart",error);
    }
}

export const getCart= async(token)=>
{
    try {
        const response = await axios.get(API, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
    } catch (error) {
        console.error("Error while loading cart",error);
    }
}