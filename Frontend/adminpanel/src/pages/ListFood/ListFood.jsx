import axios from "axios";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./ListFood.css";
import { deleteFood, getFood } from "../../service/foodService";

const ListFood = () => {
  const [list, setList] = useState([]);

  const fetchList = async () => {
    try {
      const data = await getFood();
      setList(data);

    } catch (error) {
      toast.error("Error while fetching foods.");
    }
  };

  useEffect(() => {
    fetchList();
  }, []);

  const removeFood = async (foodId) => {
    try {
        const status = await deleteFood(foodId);
        if(status === 204)
        {
            toast.success("Food removed successfully");
            await fetchList();
        }
        else{
      toast.error("Server error while removing food.");

        }

    } catch (error) {
      toast.error("Server error while removing food.");
    }
  };

  return (
    <div className="py-5 row justify-content-center">
      <div className="col-11">
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {list.length > 0 ? (
              list.map((item, index) => (
                <tr key={index}>
                  <td>
                    <img
                      src={item.imageUrl}
                      alt={item.name}
                      height={48}
                      width={48}
                    />
                  </td>
                  <td>{item.name}</td>
                  <td>{item.category}</td>
                  <td>{item.price}.00</td>
                  <td className="text-danger">
                    <i
                      className="bi bi-x-circle-fill"
                      style={{ cursor: "pointer" }}
                      onClick={() => removeFood(item.id )}
                    ></i>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5" className="text-center">
                  No foods found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListFood;
