import axios from 'axios';

const API = "http://localhost:8080/api/foods";

export const fetchFoodList = async () => {
    try {
        const response = await axios.get(API);
        return response.data;
    } catch (error) {
        throw error;
    }
}
export const fetchFoodDetails = async (id)=>
{
    try {
    const response = await axios.get(API+'/'+id);
        return response.data;
    } catch (error) {
        throw error;
    }       
}