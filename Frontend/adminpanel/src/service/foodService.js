
import axios from "axios";

const URL = "http://localhost:8080/api/foods";
export const addFood = async (foodData, image) => {
    const formData = new FormData();
    formData.append('food', JSON.stringify(foodData));
    formData.append('file', image);

    try {
        await axios.post(URL, formData, { headers: { "Content-Type": "multipart/form-data" } });

    } catch (error) {
        console.error('error', error);
        throw error;
    }
}

export const getFood = async () => {
    try {
        const response = await axios.get(URL);

       return response.data;
    } catch (error) {
        throw error;
    }
}

export const deleteFood = async(foodId)=>
{
    try {
        const response = await axios.delete(URL+"/"+foodId);
        return response.status
    } catch (error) {
        throw error;
    }
}