
import  axios  from 'axios';
const API = 'http://localhost:8080/api/'
export const registerUser = async(data)=>
{
    try {
        const response = await axios.post(API+"register",data);
        return response;
    } catch (error) {
        throw error;
    }
}

export const loginUser = async(data)=>
{
    try {
        const response = await axios.post(API+"login",data);
        return response;
    } catch (error) {
        throw error;
    }
}